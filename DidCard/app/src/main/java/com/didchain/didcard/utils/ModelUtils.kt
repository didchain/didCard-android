package com.didchain.didcard.utils

import android.os.Build


/**
 *Author:Mr'x
 *Time:
 *Description:
 */
object ModelUtils {
    fun isMIUI(): Boolean {
        val manufacturer = Build.MANUFACTURER
        return "xiaomi".equals(manufacturer, ignoreCase = true)
    }

    fun isEMUI(): Boolean {
        val manufacturer = Build.MANUFACTURER
        return "HUAWEI".equals(manufacturer, ignoreCase = true)
    }

    fun isOPPO(): Boolean {
        val manufacturer = Build.MANUFACTURER
        return "OPPO".equals(manufacturer, ignoreCase = true)
    }

    fun isVIVO(): Boolean {
        val manufacturer = Build.MANUFACTURER
        return "vivo".equals(manufacturer, ignoreCase = true)
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    fun getSystemVersion(): Int {
        return Build.VERSION.RELEASE.toInt()
    }
}
