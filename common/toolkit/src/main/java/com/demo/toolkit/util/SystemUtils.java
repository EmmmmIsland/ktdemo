package com.demo.toolkit.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.core.app.NotificationManagerCompat;

import com.demo.toolkit.App;
import com.ebook.toolkit.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SystemUtils {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TAG = SystemUtils.class.getSimpleName();

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    private static final String CPU_ABI_UNKNOWN = "unknown";
    private static String[] sSupportedABIs;
    private static String sMostPreferredABI = null;
    private static boolean sHaveGetCPUABI = false;

    private static int sVersionCode = 0;
    private static long sFirstInstallTime = -1;
    private static long sLastUpdateTime = -1;

    private static String APP_NAME = "starfm";


    private static String sVersionName;

    private static String sPackageName;

    public static Context getContext() {
        return App.INSTANCE;
    }

    /**
     * 获取app版本号
     *
     * @return 版本号码
     */
    public static int getVersionCode() {
        if (sVersionCode == 0) {
            try {
                Context context = getContext();
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                sVersionCode =  info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                sVersionCode =  1;
            }
        }
        return sVersionCode;
    }

    public static String getAppName() {
        return APP_NAME;
    }


    /**
     * 获取app版本名
     */
    public static String getVersionName() {
        if (sVersionName == null) {
            try {
                Context context = getContext();
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                sVersionName = info.versionName;
            } catch (Exception e) {
                sVersionName = "1.0.0";
            }
        }
        return sVersionName;
    }

    /**
     * 获取app包名
     *
     * @return 版本号码
     */
    public static String getPackageName() {
        if (sPackageName == null) {
            try {
                Context context = getContext();
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                sPackageName = info.packageName;
            } catch (Exception e) {
                sPackageName = "";
            }
        }
        return sPackageName;
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
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        int rst = 0;
        try {
            String[] subVersion1 = version1.split("\\.");
            String[] subVersion2 = version2.split("\\.");
            int size = Math.max(subVersion1.length, subVersion2.length);
            for (int i = 0; i < size; i++) {
                if (i >= subVersion1.length) {
                    rst = Integer.parseInt(subVersion2[i]) == 0 ? 0 : -1; // assume the missing subVersion1 = 0
                } else if (i >= subVersion2.length) {
                    rst = Integer.parseInt(subVersion1[i]) == 0 ? 0 : 1; // assume the missing subVersion2 = 0
                } else { // both exist
                    if (Integer.parseInt(subVersion1[i]) != Integer.parseInt(subVersion2[i])) {
                        rst = Integer.parseInt(subVersion1[i]) < Integer.parseInt(subVersion2[i]) ? -1 : 1;
                    }
                }
                if (rst != 0) {
                    return rst;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;

    }

    public static long getFirstInstallTime() {
        if (sFirstInstallTime == -1) {
            try {
                Context context = getContext();
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                sFirstInstallTime = info.firstInstallTime;
            } catch (PackageManager.NameNotFoundException e) {
                sFirstInstallTime = 0;
            }
        }
        return sFirstInstallTime;
    }

    public static long getLastUpdateTime() {
        if (sLastUpdateTime == -1) {
            try {
                Context context = getContext();
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                sLastUpdateTime = info.lastUpdateTime;
            } catch (PackageManager.NameNotFoundException e) {
                sLastUpdateTime = 0;
            }
        }
        return sLastUpdateTime;
    }

    /**
     * 获取当前app原始版本号
     * @return
     */
    public static String getVersionNameOriginal() {
        try {
            Context context = getContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否安装某个程序
     *
     * @param pkgName 要检测的包名
     * @return true表示已安装
     */
    public static boolean isPackageInstalled(String pkgName) {
        try {
            Context context = getContext();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
            return packageInfo != null;
        } catch (PackageManager.NameNotFoundException ex) {
            if (DEBUG) ex.printStackTrace();
        }
        return false;
    }

    /**
     * 获取系统语言
     *
     * @return 语言代码
     */
    public static String getLanguage() {
        Locale locale = getContext().getResources().getConfiguration().locale;
        return locale.getLanguage();
    }

    @SuppressLint("MissingPermission")
    public static String getPhoneNumber() {
        TelephonyManager telephonyManager
                = (TelephonyManager) getContext().getSystemService(
                Context.TELEPHONY_SERVICE);
        String phoneNumber = "";
        // 获取手机号需要两个权限,且为或,所以直接try catch
        try {
            phoneNumber = telephonyManager.getLine1Number();
        } catch (Exception ex) {
            if (DEBUG) ex.printStackTrace();
        }
        return phoneNumber;
    }

    /**
     * 是否Android5.0及以上系统
     * @return
     */
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 是否4.4系统（包括KitKat_Watch）
     * @return
     */
    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH;
    }

    /**
     * 是否7.1系统
     * @return
     */
    public static boolean isN_MR1() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1;
    }

    /**
     * 获取屏幕尺寸
     *
     * @return 屏幕尺寸：320*480
     */
    public static String getScreenSize() {
        try {
            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            return metrics.widthPixels + "*" + metrics.heightPixels;
        } catch (Exception e) {
            return "";
        }
    }

    public static int getScreenWidth() {
        DisplayMetrics metric = getContext().getResources().getDisplayMetrics();
        return metric.widthPixels;// 屏幕宽度（像素）
    }

    public static int getScreenHeight() {
        DisplayMetrics metric = getContext().getResources().getDisplayMetrics();
        return metric.heightPixels;// 屏幕宽度（像素）
    }


    public static int getActivityHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getScreenRealHeight() {
        WindowManager wm = (WindowManager)  getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm !=  null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getRealSize(size);
            return size.y;
        }
        return getScreenHeight();
    }

    public static boolean isStarMakerMainProcess(final Context context) {

        int pid = android.os.Process.myPid();
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> infos = activityManager.getRunningAppProcesses();
        if (infos != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == pid) {
                    return runningAppProcessInfo.processName.equals(packageName);
                }
            }
        }

        return false;
    }

    /**
     * 获取系统版本，如Android422
     * @return
     */
    public static String getSystemSDK() {
        return "Android" + Build.VERSION.RELEASE.replace(".", "");
    }

    /**
     * 获取当前app版本号
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    /**
     * @param context
     * @return
     * 获取底部虚拟按键高度
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        if (context instanceof Activity) {
            return isNavigationBarShow((Activity) context);
        }
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    public static boolean isNavigationBarShow(Activity activity){
        if (activity == null) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y!=size.y;
        }else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if(menu || back) {
                return false;
            }else {
                return true;
            }
        }
    }


    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    public static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    public static boolean isNavigationBarVisible(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        if (viewGroup != null) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                int id = viewGroup.getChildAt(i).getId();
                if (id != View.NO_ID) {
                    String resourceEntryName = activity
                            .getResources()
                            .getResourceEntryName(id);
                    if ("navigationBarBackground".equals(resourceEntryName)
                            && viewGroup.getChildAt(i).getVisibility() == View.VISIBLE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断应用是否允许通知
     */
    public static boolean hasNotificationPermisson(){
        try {
            NotificationManagerCompat manager = NotificationManagerCompat.from(App.INSTANCE);
            return manager.areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 打开应用详情设置页面
     */
    public static void  goToAppInfoSetting(Context context){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        if (context == null || serviceClass == null) {
            return false;
        }
        boolean isServiceRunning = false;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            try {
                List<ActivityManager.RunningServiceInfo> runningServiceInfoList = manager.getRunningServices(Integer.MAX_VALUE);
                for (ActivityManager.RunningServiceInfo service : runningServiceInfoList) {
                    if (serviceClass.getName().equals(service.service.getClassName())) {
                        isServiceRunning = true;
                    }
                }
            } catch (Exception e) {
            }
        }
        return isServiceRunning;
    }

    //是否阿拉伯语
    public static boolean isAr(){
        return "ar".equalsIgnoreCase(Locale.getDefault().getLanguage());
    }

    /**
     * 隐藏虚拟按键
     */
    public static void hideBottomUIMenu(Activity context) {
        if (Build.VERSION.SDK_INT >  Build.VERSION_CODES.HONEYCOMB && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            View v = context.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = context.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
//            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 是否低内存环境
     * @param activity
     * @return
     */
    public static boolean isLowMemory(Activity activity) {
        if (activity != null) {
            try {
                ActivityManager activityManager = (ActivityManager) activity.getSystemService(activity.ACTIVITY_SERVICE);
                ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
                activityManager.getMemoryInfo(info);
                return info.lowMemory;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void setDarkStatusIcon(Activity activity, boolean bDark) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decorView = activity.getWindow().getDecorView();
            if(decorView != null){
                int vis = decorView.getSystemUiVisibility();
                if(bDark){
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else{
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    //华为手机判定是否有刘海。外部调用getNotchFeature
    private static boolean hasNotchInScreen(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.w("tag", "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.w("tag", "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.w("tag", "hasNotchInScreen Exception");
        } finally {
            return ret;
        }
    }

    //打开系统设置页面
    public static void goToSystemSettings(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    private static int total_mem_size_in_mb = 0;

    /**
     * 返回系统总内存，单位MB
     *
     * @return
     */
    public static int getTotalMemInMB() {
        if (total_mem_size_in_mb != 0) {
            return total_mem_size_in_mb;
        }

        try {
            FileReader fileReader = new FileReader("/proc/meminfo");
            BufferedReader bufferedReader = new BufferedReader(fileReader, 4 * 1024);
            String str;
            int count = 0; // 主要为了防止某种未知错误导致死循环
            while ((str = bufferedReader.readLine()) != null) {
                str = str.toLowerCase();
                if (str.contains("memtotal")) {
                    break;
                }
                count++;
                if (count > 100) {
                    break;
                }
            }
            bufferedReader.close();
            /* \\s表示   空格,回车,换行等空白符,
            +号表示一个或多个的意思     */
            String[] array = str.split("\\s+");
            // 获得系统总内存，单位是KB，除以1024转换为MB
            total_mem_size_in_mb = Integer.valueOf(array[1]).intValue() / 1024;
        } catch (Exception e) {
            total_mem_size_in_mb = -1;
        }

        return total_mem_size_in_mb;
    }

    private static int cpu_core_count = 0;

    public static int getNumCores() {
        if (cpu_core_count != 0) {
            return cpu_core_count;
        }

        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            // Return the number of cores (virtual CPU devices)
            cpu_core_count = files.length;
        } catch (Exception e) {
            // Default to return -1 means exception
            cpu_core_count = -1;
        }

        return cpu_core_count;
    }

    private final static String CPUMaxPath = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";//保存CPU可运行最大频率
    private static int cpu_max_freq = 0;

    //获取CPU可运行最大频率
    public static int getMaxCPU(){
        if (cpu_max_freq != 0) {
            return cpu_max_freq;
        }

        FileReader fr = null;
        BufferedReader br = null;

        try{
            fr = new FileReader(CPUMaxPath);
            br = new BufferedReader(fr);
            String text = br.readLine();
            cpu_max_freq = Integer.parseInt(text.trim());
        } catch (Exception e){
            cpu_max_freq = -1;
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return cpu_max_freq;
    }
}
