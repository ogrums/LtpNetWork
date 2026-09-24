package com.letianpai.network.common;

import android.content.Context;
import dalvik.system.DexFile;

import java.io.File;
import java.lang.reflect.Method;

/**
 * created by yujianbin on 2019/3/21
 */
@SuppressWarnings("rawtypes")
public class SystemPropertiesProxy {



    /**
     * Read the value for the given key.
     *
     * @return empty string when the key does not exist
     * @throws IllegalArgumentException if the key is longer than 32 characters
     */
    public static String get(Context context, String key) throws IllegalArgumentException {
        String ret;
        try {
            ClassLoader cl = context.getClassLoader();
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
// parameter types
            Class[] paramTypes = new Class[1];
            paramTypes[0] = String.class;
            Method get = SystemProperties.getMethod("get", paramTypes);
// arguments
            Object[] params = new Object[1];
            params[0] = key;
            ret = (String) get.invoke(SystemProperties, params);
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
            ret = "";
//TODO
        }
        return ret;
    }

    /**
     * Read the value for a key.
     *
     * @return def when the key is missing and def is not empty, otherwise an empty string
     * @throws IllegalArgumentException if the key is longer than 32 characters
     */
    public static String get(Context context, String key, String def) throws IllegalArgumentException {
        String ret;
        try {
            ClassLoader cl = context.getClassLoader();
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
// parameter types
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = String.class;
            Method get = SystemProperties.getMethod("get", paramTypes);
// arguments
            Object[] params = new Object[2];
            params[0] = key;
            params[1] = def;
            ret = (String) get.invoke(SystemProperties, params);
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
            ret = def;
        }
        return ret;
    }

    /**
     * Return the int value for the given key.
     *
     * @param key key to query
     * @param def default value
     * @return the int value, or the default when the key is missing
     * @throws IllegalArgumentException if the key is longer than 32 characters
     */
    public static Integer getInt(Context context, String key, int def) throws IllegalArgumentException {
        Integer ret = def;
        try {
            ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
// parameter types
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = int.class;
            Method getInt = SystemProperties.getMethod("getInt", paramTypes);
// arguments
            Object[] params = new Object[2];
            params[0] = key;
            params[1] = new Integer(def);
            ret = (Integer) getInt.invoke(SystemProperties, params);
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
            ret = def;
//TODO
        }
        return ret;
    }

    /**
     * Return the long value for the given key.
     *
     * @param key key to query
     * @param def default value
     * @return the long value, or the default when the key is missing
     * @throws IllegalArgumentException if the key is longer than 32 characters
     */
    public static Long getLong(Context context, String key, long def) throws IllegalArgumentException {
        Long ret = def;
        try {
            ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
// parameter types
            @SuppressWarnings("rawtypes")
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = long.class;
            Method getLong = SystemProperties.getMethod("getLong", paramTypes);
// arguments
            Object[] params = new Object[2];
            params[0] = key;
            params[1] = new Long(def);
            ret = (Long) getLong.invoke(SystemProperties, params);
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
            ret = def;
//TODO
        }
        return ret;
    }

    /**
     * Return the boolean value for the given key.
     * Returns false for 'n', 'no', '0', 'false', or 'off'.
     * Returns true for 'y', 'yes', '1', 'true', or 'on'.
     * Returns the default when the key is missing or the value is something else.
     *
     * @param key key to query
     * @param def default value
     * @return the boolean value, or the default when the key is missing
     * @throws IllegalArgumentException if the key is longer than 32 characters
     */
    public static Boolean getBoolean(Context context, String key, boolean def) throws IllegalArgumentException {
        Boolean ret = def;
        try {
            ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
            // parameter types
            @SuppressWarnings("rawtypes")
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = boolean.class;
            Method getBoolean = SystemProperties.getMethod("getBoolean", paramTypes);
            // arguments
            Object[] params = new Object[2];
            params[0] = new String(key);
            params[1] = new Boolean(def);
            ret = (Boolean) getBoolean.invoke(SystemProperties, params);
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
            ret = def;
//TODO
        }
        return ret;
    }

    /**
     * Set a property. This needs a privileged permission.
     *
     * @throws IllegalArgumentException if the key is longer than 32 characters
     * @throws IllegalArgumentException if the value is longer than 92 characters
     */
    public static void set(Context context, String key, String val) throws IllegalArgumentException {
        try {
            @SuppressWarnings("unused")
            DexFile df = new DexFile(new File("/system/app/Settings.apk"));
            @SuppressWarnings("unused")
            ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = Class.forName("android.os.SystemProperties");
// parameter types
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = String.class;
            Method set = SystemProperties.getMethod("set", paramTypes);
// arguments
            Object[] params = new Object[2];
            params[0] = key;
            params[1] = val;
            set.invoke(SystemProperties, params);
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
//TODO
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        Method getNameMethod = null;

        try {
            Class mClass = Class.forName("android.os.SystemProperties");
            getNameMethod = mClass.getDeclaredMethod("getBoolean", String.class, Boolean.TYPE);
            boolean value = (Boolean)getNameMethod.invoke(mClass, key, defaultValue);
            return value;
        } catch (Exception var5) {
            return defaultValue;
        }
    }
}
