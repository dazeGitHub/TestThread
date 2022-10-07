package com.example.testthread.activity

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.testthread.R
import com.example.testthread.bean.DownloadImgTask
import com.example.testthread.bean.ImageBean
import com.example.testthread.data.DataCenter
import com.example.testthread.utils.TLog


class HandlerThreadActivity2 : AppCompatActivity() {

    private var imageView: ImageView? = null
    private var handlerThread: HandlerThread? = null

    /**
     * 处理UI
     */
    private var mainThreadHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val imageBean = msg.obj as ImageBean
            TLog.e("图片下载完成, 开始更新 UI, imgUrl = " + imageBean.url
                    + " CurThreadId = " + Thread.currentThread().id
                    + " CurThreadName = " + Thread.currentThread().name
            )
            imageView?.setImageBitmap(imageBean.bitmap)
        }
    }

    private var onDownloadImgSuccessListener = object : ((ImageBean) -> Unit){
        override fun invoke(imageBean: ImageBean) {
            TLog.e("图片下载成功回调 onDownloadImgSuccessListener invoke() , "
                    + " CurThreadId = " + Thread.currentThread().id
                    + " CurThreadName = " + Thread.currentThread().name
            )
            mainThreadHandler.sendMessage(Message().apply {
                this.what = 0
                this.obj = imageBean
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_thread)
        imageView = findViewById<View>(R.id.imageView) as ImageView
        findViewById<View>(R.id.btn_start_download_img).setOnClickListener{
            loadByHandlerThread()
        }
        createHandlerThread()
    }

    /**
     * 创建一个HandlerThread
     */
    private fun createHandlerThread() {
        //创建实例对象
        handlerThread = HandlerThread("downloadImage")
        handlerThread?.start()
    }

    /**
     * 通过HandlerThread的方式加载
     * @param view
     */
    private fun loadByHandlerThread() {
        val handlerThreadHandler = Handler(handlerThread!!.looper)
        val taskList: List<DownloadImgTask>  = DataCenter.imageUrls.map { imgUrl ->
            DownloadImgTask(imgUrl).apply {
                this.mDownloadImgSuccessListener = onDownloadImgSuccessListener
            }
        }
        //handlerThreadHandler.sendEmptyMessageDelayed(i, (1000 * i).toLong())
        for(task in taskList){
            handlerThreadHandler.post(task);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //释放资源
        handlerThread?.quit()
    }
}