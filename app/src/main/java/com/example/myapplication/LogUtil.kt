package com.example.myapplication

import android.os.Build
import android.util.Log

object LogUtil {

    private const val MAX_TAG_LENGTH = 23

    @JvmStatic
    fun d(message: String): Int{
        return getLogSourceInfo().let {(tag, source)->
            Log.d(tag, "$source : $message")
        }
    }

    @JvmStatic
    fun e(message: String, t: Throwable? = null): Int{
        return getLogSourceInfo().let {(tag, source)->
            Log.e(tag, "$source : $message :", t)
        }
    }

    @JvmStatic
    fun i(message: String, t: Throwable? = null): Int{
        return getLogSourceInfo().let {(tag, source)->
            Log.i(tag, "$source : $message :", t)
        }
    }

    @JvmStatic
    fun wtf(message: String, t: Throwable? = null): Int{
        return getLogSourceInfo().let {(tag, source)->
            Log.wtf(tag, "$source : $message :", t)
        }
    }


    private fun getLogSourceInfo(): Pair<String, String> {
        val stackTrace = Throwable().stackTrace
            .first { it.className != LogUtil::class.java.name }

        var tag = stackTrace.fileName.split(".")[0]
        // Tag length limit was removed in API 26.
        if (tag.length > MAX_TAG_LENGTH && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            tag = tag.substring(0, MAX_TAG_LENGTH)
        }

        return Pair(tag, "(${stackTrace.fileName}:${stackTrace.lineNumber})")
    }

}
