package com.letianpai.network.common;

/**
 * Created by jianbin 18-7-14.
 * public field
 */

public class Contents {

    public static final String APP_NAME = "AIFeedback";

    /**
     * Header中需要添加的请求参数
     * 具体说明，参考wiki: http://jira.xgrobotics.com:8090/pages/viewpage.action?pageId=655396
     * */
    public interface HEADER {
        String USER_AGENT = "User-Agent";
        String CHANNEL = "Channel";
        String APPKEY = "Appkey";
        String APPVERSION = "Appversion";
        String APPVERSION_CODE = "Appversioncode";
        String APPVERSION_TYPE = "Appversiontype";
        String PLATFORM = "Platform";
        String SIGN = "Sign";
        String TIME = "Time";
        String AK = "Ak";
        String SK = "Sk";
        String JWTTK = "jwttk";
        String UUID = "uuid";
        String ACCEPT_LANGUAGE = "Accept-Language";
        String MAC = "Mac";
        String SN = "Sn";
        String MODEL = "Model";
        String DEVICEID = "Devid";
        String DEVTYPE = "Devtype";
        String SYSTEMVERSION = "Systemversion";
    }
}

