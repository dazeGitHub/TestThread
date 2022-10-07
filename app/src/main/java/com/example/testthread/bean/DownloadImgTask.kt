package com.example.testthread.bean

import com.example.testthread.utils.TLog
import com.example.testthread.utils.Utils

class DownloadImgTask(private val imgUrl: String) : Runnable { //Callable<Any?>

    var mDownloadImgSuccessListener : ((ImageBean) -> Unit)? = null
    
    override fun run() {
        //在子线程中进行网络请求
        TLog.e("DownloadImgTask 开始下载图片, "
                + " CurThreadId = " + Thread.currentThread().id
                + " CurThreadName = " + Thread.currentThread().name
        )
        val bitmap = Utils.downloadUrlBitmap(imgUrl)
        //这样也可以防止图片加载过快, 不过第一张图片会慢 1 秒加载
        //Thread.sleep(1000)
        mDownloadImgSuccessListener?.invoke(ImageBean().apply {
            this.bitmap = bitmap
            this.url = imgUrl
        })
    }
}