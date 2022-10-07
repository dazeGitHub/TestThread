package com.example.testthread.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.testthread.R
import com.example.testthread.bean.ImageBean
import com.example.testthread.utils.TLog
import com.example.testthread.utils.Utils
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class HandlerThreadActivity : AppCompatActivity() {

    /**
     * 图片地址集合
     */
    private val url = arrayOf(
        "https://t7.baidu.com/it/u=4162611394,4275913936&fm=193&f=GIF",
        "https://t7.baidu.com/it/u=848096684,3883475370&fm=193&f=GIF",
        "https://t7.baidu.com/it/u=1415984692,3889465312&fm=193&f=GIF",
        "https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF",
        "https://t7.baidu.com/it/u=3691080281,11347921&fm=193&f=GIF",
    )
    private var imageView: ImageView? = null
    private var handlerThread: HandlerThread? = null
    private var loadImageThread: Thread? = null
    private var count = 0

    /**
     * 处理UI
     */
    var mainThreadHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            TLog.e("次数:" + msg.what)
            val imageBean = msg.obj as ImageBean
            imageView!!.setImageBitmap(imageBean.bitmap)
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
     * 通过HandlerThread的方式加载
     * @param view
     */
    private fun loadByHandlerThread() {
        val callBack: Handler.Callback = loadImageCallBack()
        val handlerThreadHandler = Handler(handlerThread!!.looper, callBack)
        for (i in url.indices) {
            //这里延迟发送消息, 防止图片加载过块
            handlerThreadHandler.sendEmptyMessageDelayed(i, (1000 * i).toLong())
        }
    }

    /**
     * 通过Thread的方式加载
     * @param view
     */
    fun loadByThread(view: View?) {
        if (loadImageThread != null && !loadImageThread!!.isInterrupted) {
            loadImageThread!!.interrupt()
        }
        count = 0
        loadImageThread = Thread {
            try {
                while (true) {
                    Thread.sleep(1000)
                    //在子线程中进行网络请求
                    val bitmap = Utils.downloadUrlBitmap(url[count])
                    val imageBean: ImageBean = ImageBean()
                    imageBean.bitmap = bitmap
                    imageBean.url = url[count]
                    val message = Message()
                    message.what = count
                    message.obj = imageBean
                    count++
                    mainThreadHandler.sendMessage(message)
                    //最后一张时停止加载
                    if (count >= url.size) {
                        loadImageThread!!.interrupt()
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                TLog.e("加载完毕, 停止线程")
            }
        }
        loadImageThread!!.start()
    }

    /**
     * 处理下载图片
     */
    internal inner class loadImageCallBack : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            //在子线程中进行网络请求
            val bitmap = Utils.downloadUrlBitmap(url[msg.what])
            val imageBean: ImageBean = ImageBean()
            imageBean.bitmap = bitmap
            imageBean.url = url[msg.what]
            val message = Message()
            message.what = msg.what
            message.obj = imageBean
            mainThreadHandler.sendMessage(message)
            return false
        }
    }

    /**
     * 创建一个HandlerThread
     */
    private fun createHandlerThread() {
        //创建实例对象
        handlerThread = HandlerThread("downloadImage")
        handlerThread!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        //释放资源
        handlerThread!!.quit()
    }
}