package com.demo.toolkit.util

import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.core.app.NotificationManagerCompat
import com.demo.toolkit.App
import com.ebook.toolkit.BuildConfig
import java.io.*
import java.lang.Exception
import java.lang.reflect.Method
import java.util.*
import java.util.regex.Pattern

object SystemUtils {
    private val DEBUG: Boolean = BuildConfig.DEBUG
    private val TAG: String = SystemUtils::class.java.simpleName
    private val KEY_MIUI_VERSION_CODE: String = "ro.miui.ui.version.code"
    private val KEY_MIUI_VERSION_NAME: String = "ro.miui.ui.version.name"
    private val KEY_MIUI_INTERNAL_STORAGE: String = "ro.miui.internal.storage"
    private val CPU_ABI_UNKNOWN: String = "unknown"
    private var sVersionCode: Int = 0
    private var sFirstInstallTime: Long = -1
    private var sLastUpdateTime: Long = -1
    val appName: String = "starfm"
    private var sVersionName: String? = null
    private var sPackageName: String? = null
    val context: Context
        get() = App.INSTANCE

    /**
     * 获取app版本号
     *
     * @return 版本号码
     */
    val versionCode: Int
        get() {
            if (sVersionCode == 0) {
                try {
                    val context: Context = context
                    val info: PackageInfo =
                        context.packageManager.getPackageInfo(context.packageName, 0)
                    sVersionCode = info.versionCode
                } catch (e: PackageManager.NameNotFoundException) {
                    sVersionCode = 1
                }
            }
            return sVersionCode
        }

    /**
     * 获取app版本名
     */
    val versionName: String?
        get() {
            if (sVersionName == null) {
                try {
                    val context: Context = context
                    val info: PackageInfo =
                        context.packageManager.getPackageInfo(context.packageName, 0)
                    sVersionName = info.versionName
                } catch (e: Exception) {
                    sVersionName = "1.0.0"
                }
            }
            return sVersionName
        }

    /**
     * 获取app包名
     *
     * @return 版本号码
     */
    val packageName: String?
        get() {
            if (sPackageName == null) {
                try {
                    val context: Context = context
                    val info: PackageInfo =
                        context.packageManager.getPackageInfo(context.packageName, 0)
                    sPackageName = info.packageName
                } catch (e: Exception) {
                    sPackageName = ""
                }
            }
            return sPackageName
        }

    /**
     * version1 == version2 返回 0
     * version1 < version2 返回 -1
     * version1 > version2 返回 1
     *
     * @param version1
     * @param version2
     * @return
     */
    fun compareVersion(version1: String, version2: String): Int {
        if ((version1 == version2)) {
            return 0
        }
        var rst: Int = 0
        try {
            val subVersion1: Array<String> = version1.split("\\.").toTypedArray()
            val subVersion2: Array<String> = version2.split("\\.").toTypedArray()
            val size: Int = Math.max(subVersion1.size, subVersion2.size)
            for (i in 0 until size) {
                if (i >= subVersion1.size) {
                    rst = if (subVersion2.get(i)
                            .toInt() == 0
                    ) 0 else -1 // assume the missing subVersion1 = 0
                } else if (i >= subVersion2.size) {
                    rst = if (subVersion1.get(i)
                            .toInt() == 0
                    ) 0 else 1 // assume the missing subVersion2 = 0
                } else { // both exist
                    if (subVersion1.get(i).toInt() != subVersion2.get(i).toInt()) {
                        rst = if (subVersion1.get(i).toInt() < subVersion2.get(i).toInt()) -1 else 1
                    }
                }
                if (rst != 0) {
                    return rst
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return rst
    }

    val firstInstallTime: Long
        get() {
            if (sFirstInstallTime == -1L) {
                try {
                    val context: Context = context
                    val info: PackageInfo =
                        context.packageManager.getPackageInfo(context.packageName, 0)
                    sFirstInstallTime = info.firstInstallTime
                } catch (e: PackageManager.NameNotFoundException) {
                    sFirstInstallTime = 0
                }
            }
            return sFirstInstallTime
        }
    val lastUpdateTime: Long
        get() {
            if (sLastUpdateTime == -1L) {
                try {
                    val context: Context = context
                    val info: PackageInfo =
                        context.packageManager.getPackageInfo(context.packageName, 0)
                    sLastUpdateTime = info.lastUpdateTime
                } catch (e: PackageManager.NameNotFoundException) {
                    sLastUpdateTime = 0
                }
            }
            return sLastUpdateTime
        }

    /**
     * 获取当前app原始版本号
     * @return
     */
    val versionNameOriginal: String?
        get() {
            try {
                val context: Context = context
                val packageManager: PackageManager = context.packageManager
                val packageInfo: PackageInfo = packageManager.getPackageInfo(context.packageName, 0)
                return packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

    /**
     * 判断是否安装某个程序
     *
     * @param pkgName 要检测的包名
     * @return true表示已安装
     */
    fun isPackageInstalled(pkgName: String?): Boolean {
        try {
            val context: Context = context
            val packageInfo: PackageInfo? = context.packageManager.getPackageInfo(
                (pkgName)!!, 0
            )
            return packageInfo != null
        } catch (ex: PackageManager.NameNotFoundException) {
            if (DEBUG) ex.printStackTrace()
        }
        return false
    }

    /**
     * 获取系统语言
     *
     * @return 语言代码
     */
    val language: String
        get() {
            val locale: Locale = context.resources.configuration.locale
            return locale.language
        }



    /**
     * 是否Android5.0及以上系统
     * @return
     */
    val isLollipop: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    /**
     * 是否4.4系统（包括KitKat_Watch）
     * @return
     */
    val isKitKat: Boolean
        get() = (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH)

    /**
     * 是否7.1系统
     * @return
     */
    val isN_MR1: Boolean
        get() {
            return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1
        }

    /**
     * 获取屏幕尺寸
     *
     * @return 屏幕尺寸：320*480
     */
    val screenSize: String
        get() {
            try {
                val metrics: DisplayMetrics = context.resources.displayMetrics
                return metrics.widthPixels.toString() + "*" + metrics.heightPixels
            } catch (e: Exception) {
                return ""
            }
        }

    // 屏幕宽度（像素）
    val screenWidth: Int
        get() {
            val metric: DisplayMetrics = context.resources.displayMetrics
            return metric.widthPixels // 屏幕宽度（像素）
        }

    // 屏幕宽度（像素）
    val screenHeight: Int
        get() {
            val metric: DisplayMetrics = context.resources.displayMetrics
            return metric.heightPixels // 屏幕宽度（像素）
        }

    fun getActivityHeight(activity: Activity): Int {
        val display: Display = activity.windowManager.defaultDisplay
        val size: Point = Point()
        display.getSize(size)
        return size.y
    }

    val screenRealHeight: Int
        get() {
            val wm: WindowManager? =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            if (wm != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                val display: Display = wm.defaultDisplay
                val size: Point = Point()
                display.getRealSize(size)
                return size.y
            }
            return screenHeight
        }

    fun isStarMakerMainProcess(context: Context): Boolean {
        val pid: Int = Process.myPid()
        val activityManager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val packageName: String = context.packageName
        val infos: List<ActivityManager.RunningAppProcessInfo>? =
            activityManager.runningAppProcesses
        if (infos != null) {
            for (runningAppProcessInfo: ActivityManager.RunningAppProcessInfo in activityManager.runningAppProcesses) {
                if (runningAppProcessInfo.pid == pid) {
                    return (runningAppProcessInfo.processName == packageName)
                }
            }
        }
        return false
    }

    /**
     * 获取系统版本，如Android422
     * @return
     */
    val systemSDK: String
        get() {
            return "Android" + Build.VERSION.RELEASE.replace(".", "")
        }

    /**
     * 获取当前app版本号
     * @return
     */
    fun getVersionCode(context: Context): Int {
        try {
            val packageName: String = context.packageName
            val info: PackageInfo = context.packageManager.getPackageInfo(packageName, 0)
            return info.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            return 1
        }
    }

    /**
     * @param context
     * @return
     * 获取底部虚拟按键高度
     */
    fun getNavigationBarHeight(context: Context): Int {
        var result: Int = 0
        if (hasNavBar(context)) {
            val res: Resources = context.resources
            val resourceId: Int = res.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun hasNavBar(context: Context): Boolean {
        if (context is Activity) {
            return isNavigationBarShow(context)
        }
        val res: Resources = context.resources
        val resourceId: Int = res.getIdentifier("config_showNavigationBar", "bool", "android")
        if (resourceId != 0) {
            var hasNav: Boolean = res.getBoolean(resourceId)
            // check override flag
            val sNavBarOverride: String? = navBarOverride
            if (("1" == sNavBarOverride)) {
                hasNav = false
            } else if (("0" == sNavBarOverride)) {
                hasNav = true
            }
            return hasNav
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey()
        }
    }

    fun isNavigationBarShow(activity: Activity?): Boolean {
        if (activity == null) {
            return true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val display: Display = activity.windowManager.defaultDisplay
            val size: Point = Point()
            val realSize: Point = Point()
            display.getSize(size)
            display.getRealSize(realSize)
            return realSize.y != size.y
        } else {
            val menu: Boolean = ViewConfiguration.get(activity).hasPermanentMenuKey()
            val back: Boolean = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            if (menu || back) {
                return false
            } else {
                return true
            }
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    val navBarOverride: String?
        get() {
            var sNavBarOverride: String? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    val c: Class<*> = Class.forName("android.os.SystemProperties")
                    val m: Method = c.getDeclaredMethod("get", String::class.java)
                    m.isAccessible = true
                    sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
                } catch (e: Throwable) {
                }
            }
            return sNavBarOverride
        }

    fun isNavigationBarVisible(activity: Activity): Boolean {
        val viewGroup: ViewGroup? = activity.window.decorView as ViewGroup
        if (viewGroup != null) {
            for (i in 0 until viewGroup.childCount) {
                val id: Int = viewGroup.getChildAt(i).id
                if (id != View.NO_ID) {
                    val resourceEntryName: String = activity
                        .resources
                        .getResourceEntryName(id)
                    if ((("navigationBarBackground" == resourceEntryName) && viewGroup.getChildAt(i).visibility == View.VISIBLE)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * 判断应用是否允许通知
     */
    fun hasNotificationPermisson(): Boolean {
        try {
            val manager: NotificationManagerCompat = NotificationManagerCompat.from(App.INSTANCE)
            return manager.areNotificationsEnabled()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 打开应用详情设置页面
     */
    fun goToAppInfoSetting(context: Context) {
        val intent: Intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    fun isServiceRunning(context: Context?, serviceClass: Class<*>?): Boolean {
        if (context == null || serviceClass == null) {
            return false
        }
        var isServiceRunning: Boolean = false
        val manager: ActivityManager? =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (manager != null) {
            try {
                val runningServiceInfoList: List<ActivityManager.RunningServiceInfo> =
                    manager.getRunningServices(
                        Int.MAX_VALUE
                    )
                for (service: ActivityManager.RunningServiceInfo in runningServiceInfoList) {
                    if ((serviceClass.name == service.service.className)) {
                        isServiceRunning = true
                    }
                }
            } catch (e: Exception) {
            }
        }
        return isServiceRunning
    }

    //是否阿拉伯语
    val isAr: Boolean
        get() {
            return "ar".equals(Locale.getDefault().language, ignoreCase = true)
        }

    /**
     * 隐藏虚拟按键
     */
    fun hideBottomUIMenu(context: Activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            val v: View = context.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val decorView: View = context.window.decorView
            val uiOptions: Int = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            decorView.systemUiVisibility = uiOptions
            //            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 是否低内存环境
     * @param activity
     * @return
     */
    fun isLowMemory(activity: Activity?): Boolean {
        if (activity != null) {
            try {
                val activityManager: ActivityManager =
                    activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val info: ActivityManager.MemoryInfo = ActivityManager.MemoryInfo()
                activityManager.getMemoryInfo(info)
                return info.lowMemory
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }

    fun setDarkStatusIcon(activity: Activity, bDark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView: View? = activity.window.decorView
            if (decorView != null) {
                var vis: Int = decorView.systemUiVisibility
                if (bDark) {
                    vis = vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    vis = vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
                decorView.systemUiVisibility = vis
            }
        }
    }

    //华为手机判定是否有刘海。外部调用getNotchFeature
    private fun hasNotchInScreen(context: Context): Boolean {
        var ret: Boolean = false
        try {
            val cl: ClassLoader = context.classLoader
            val HwNotchSizeUtil: Class<*> = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get: Method = HwNotchSizeUtil.getMethod("hasNotchInScreen")
            ret = get.invoke(HwNotchSizeUtil) as Boolean
        } catch (e: ClassNotFoundException) {
            Log.w("tag", "hasNotchInScreen ClassNotFoundException")
        } catch (e: NoSuchMethodException) {
            Log.w("tag", "hasNotchInScreen NoSuchMethodException")
        } catch (e: Exception) {
            Log.w("tag", "hasNotchInScreen Exception")
        } finally {
            return ret
        }
    }

    //打开系统设置页面
    fun goToSystemSettings(context: Context) {
        val intent: Intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Settings.ACTION_SETTINGS
        context.startActivity(intent)
    }

    private var total_mem_size_in_mb: Int = 0// 主要为了防止某种未知错误导致死循环
    /* \\s表示   空格,回车,换行等空白符,
+号表示一个或多个的意思     */
    // 获得系统总内存，单位是KB，除以1024转换为MB
    /**
     * 返回系统总内存，单位MB
     *
     * @return
     */
    val totalMemInMB: Int
        get() {
            if (total_mem_size_in_mb != 0) {
                return total_mem_size_in_mb
            }
            try {
                val fileReader: FileReader = FileReader("/proc/meminfo")
                val bufferedReader: BufferedReader = BufferedReader(fileReader, 4 * 1024)
                var str: String
                var count: Int = 0 // 主要为了防止某种未知错误导致死循环
                while ((bufferedReader.readLine().also { str = it }) != null) {
                    str = str.toLowerCase()
                    if (str.contains("memtotal")) {
                        break
                    }
                    count++
                    if (count > 100) {
                        break
                    }
                }
                bufferedReader.close()
                /* \\s表示   空格,回车,换行等空白符,
    +号表示一个或多个的意思     */
                val array: Array<String> = str.split("\\s+").toTypedArray()
                // 获得系统总内存，单位是KB，除以1024转换为MB
                total_mem_size_in_mb = Integer.valueOf(array.get(1)).toInt() / 1024
            } catch (e: Exception) {
                total_mem_size_in_mb = -1
            }
            return total_mem_size_in_mb
        }
    private var cpu_core_count: Int =
        0// Default to return -1 means exception// Get directory containing CPU info

    // Filter to only list the devices we care about
    // Return the number of cores (virtual CPU devices)
// Check if filename is "cpu", followed by a single digit number
    // Private Class to display only CPU devices in the directory listing
    val numCores: Int
        get() {
            if (cpu_core_count != 0) {
                return cpu_core_count
            }

            // Private Class to display only CPU devices in the directory listing
            class CpuFilter() : FileFilter {
                override fun accept(pathname: File): Boolean {
                    // Check if filename is "cpu", followed by a single digit number
                    if (Pattern.matches("cpu[0-9]", pathname.name)) {
                        return true
                    }
                    return false
                }
            }
            try {
                // Get directory containing CPU info
                val dir: File = File("/sys/devices/system/cpu/")
                // Filter to only list the devices we care about
                val files: Array<File> = dir.listFiles(CpuFilter())
                // Return the number of cores (virtual CPU devices)
                cpu_core_count = files.size
            } catch (e: Exception) {
                // Default to return -1 means exception
                cpu_core_count = -1
            }
            return cpu_core_count
        }
    private val CPUMaxPath: String =
        "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" //保存CPU可运行最大频率
    private var cpu_max_freq: Int = 0

    //获取CPU可运行最大频率
    val maxCPU: Int
        get() {
            if (cpu_max_freq != 0) {
                return cpu_max_freq
            }
            var fr: FileReader? = null
            var br: BufferedReader? = null
            try {
                fr = FileReader(CPUMaxPath)
                br = BufferedReader(fr)
                val text: String = br.readLine()
                cpu_max_freq = text.trim { it <= ' ' }.toInt()
            } catch (e: Exception) {
                cpu_max_freq = -1
            } finally {
                if (fr != null) {
                    try {
                        fr.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                if (br != null) {
                    try {
                        br.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return cpu_max_freq
        }
}
