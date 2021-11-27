package com.jacky.videosdk.okhttp.response

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

/**
 * @author jacky
 * @date 2021/11/27
 *
 * 获取json的工具类
 */
object JsonDataUtil {

    fun getJson(context: Context, fileName: String): String {

        val stringBuilder = StringBuilder()
        try {

            val assetManger = context.assets
            val line:String
            val bufferedReader = BufferedReader(InputStreamReader(assetManger.open(fileName)))
            line = bufferedReader.readLine()
            while(line != null) {
                stringBuilder.append(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }
}