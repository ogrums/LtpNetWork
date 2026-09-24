package com.letianpai.network.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * jianbin
 */
public class HeaderUtils {

    /**
     * Android/X600 ABXXXXXXXXXS;Kitchen 3.1.2xx;Debug
     * */
    public static String getUerAgent(Context context){
        StringBuffer ua = new StringBuffer();
        ua.append("Android/").append(getModel(context)).append(" ").append(getAliseVersion(context)).append(";")
                .append(Contents.APP_NAME).append(" ")
                .append(getAppVersionName(context) + "." + getAppVersionCode(context)).append(";");
                // .append(BuildConfig.BUILD_TYPE);
        return ua.toString();
    }


    public static String getAppKey(Context context){
        String aAppKey = getMetaData(context, "APPKEY");
        return aAppKey.split("a")[1];
    }


    public static int getAppVersionCode(Context mContext) {
        int versionCode = 0;
        try { // versionCode from AndroidManifest.xml
            versionCode = mContext.getPackageManager(). getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getAppVersionName(Context mContext){
        String versionName = "";
        try{
            versionName =
                    mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return versionName;
    }


    public static String getMacAddressBlow6(Context context) {

        // Below Android 6, read it from WifiManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String macAddress0 = getMacAddress0(context);
            if (!TextUtils.isEmpty(macAddress0)) {
                return macAddress0;
            }
        }

        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                    break;
                }
            }
        } catch (Exception ex) {
            Log.e("----->" + "NetInfoManager", "getMacAddress:" + ex.toString());
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress:" + e.toString());
            }

        }
        return macSerial.replace(":", "").toUpperCase();
    }

    private static String getMacAddress0(Context context) {
        if (isAccessWifiStateAuthorized(context)) {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = null;
            try {
                wifiInfo = wifiMgr.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } catch (Exception e) {
                Log.e("----->" + "NetInfoManager",
                        "getMacAddress0:" + e.toString());
            }

        }
        return "";

    }

    private static boolean isAccessWifiStateAuthorized(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context
                .checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
            Log.e("----->" + "NetInfoManager", "isAccessWifiStateAuthorized:"
                    + "access wifi state is enabled");
            return true;
        } else
            return false;
    }

    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    //V020406RCN03C010020D2104251D
    public static String getVersion(Context context) {
        return SystemPropertiesProxy.get(context,"ro.build.display.id");
    }

    public static String getAliasModel(Context context) {
        if(SystemPropertiesProxy.get(context,"ro.alias.model")==null){
            return "ABCDEFG";
        }else {
            return SystemPropertiesProxy.get(context,"ro.alias.model");
        }
    }

    public static String getModel(Context context) {
        if(SystemPropertiesProxy.get(context,"ro.product.model")==null){
            return "ABCDEFG";
        }else {
            return SystemPropertiesProxy.get(context,"ro.product.model");
        }
    }

    public static String getSN(Context context) {
        if(SystemPropertiesProxy.get(context,"ro.serialno")==null){
            return  "123456789";
        }else {
            return  SystemPropertiesProxy.get(context,"ro.serialno");
        }
    }

    public static String getAliseVersion(Context context){
        if(SystemPropertiesProxy.get(context,"ro.alias.version")==null){
            return  "123456789";
        }else {
            return  SystemPropertiesProxy.get(context,"ro.alias.version");
        }

    }

    public static String getWifiMac(Context context){
        if (SystemPropertiesProxy.get(context,"ro.boot.wifimac") == null) {
            return "123456789";
        } else {
            return SystemPropertiesProxy.get(context,"ro.boot.wifimac");
        }
    }

    public static boolean isDebug(){
        return SystemPropertiesProxy.getBoolean("com.puppyai.debug", false);
    }


    public static String md5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            int tem;
            StringBuffer buffer = new StringBuffer();
            for (byte it : md.digest()) {
                tem = it;
                if (tem < 0) {
                    tem += 256;
                }
                if (tem < 16) {
                    buffer.append("0");
                }
                buffer.append(Integer.toHexString(tem));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    /**
     * Confirmed with the ROM team on 2021-08-10
     *
     * @return
     */
    public static String getMacAddress(Context context) {
        if (SystemPropertiesProxy.get(context,"ro.boot.wifimac") != null) {
            return SystemPropertiesProxy.get(context,"ro.boot.wifimac");
        } else {
            return "123456789";
        }
    }


    public static String getRegion(Context context) {
        return "NA".equals(SystemPropertiesProxy.get(context,"ro.puppy.region")) ? "NA" : "CN";
    }

    /**
     * Read a value from the manifest metadata
     * @param context
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getMetaData(Context context, String name) {
        try {
            final ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);

            if (ai.metaData != null) {
                return (T) ai.metaData.get(name);
            }
        }
        catch (Exception e) {
            Log.e("<<<","Couldn't find meta-data: " + name);
        }

        return null;
    }
}
