package com.letianpai.network.common;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User:jianbin
 */

public class StringUtils {

    private StringUtils() {
        throw new AssertionError();
    }


    /**
     * is null or its length is 0 or it is made by space
     *
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param str str
     * @return if string is null or its size is 0 or it is made by space, return
     * true, else return false.
     */
    public static boolean isBlank(String str) {

        return (str == null || str.trim().length() == 0);
    }


    /**
     * is null or its length is 0
     *
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str str
     * @return if string is null or its size is 0, return true, else return
     * false.
     */
    public static boolean isEmpty(CharSequence str) {

        return (str == null || str.length() == 0);
    }


    /**
     * get length of CharSequence
     *
     * <pre>
     * length(null) = 0;
     * length(\"\") = 0;
     * length(\"abc\") = 3;
     * </pre>
     *
     * @param str str
     * @return if str is null or empty, return 0, else return {@link
     * CharSequence#length()}.
     */
    public static int length(CharSequence str) {

        return str == null ? 0 : str.length();
    }


    /**
     * null Object to empty string
     *
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param str str
     * @return String
     */
    public static String nullStrToEmpty(Object str) {

        return (str == null
                ? ""
                : (str instanceof String ? (String) str : str.toString()));
    }


    /**
     * @param str str
     * @return String
     */
    public static String capitalizeFirstLetter(String str) {

        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c))
                ? str
                : new StringBuilder(str.length()).append(
                Character.toUpperCase(c))
                .append(str.substring(1))
                .toString();
    }


    /**
     * encoded in utf-8
     *
     * @param str string
     * @return a UTF-8 string
     */
    public static String utf8Encode(String str) {

        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                        "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }


    /**
     * @param href string
     * @return an HTML string
     */
    public static String getHrefInnerHtml(String href) {

        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg,
                Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }


    /**
     * @param source string
     * @return the HTML converted to a string
     */
    public static String htmlEscapeCharsToString(String source) {

        return StringUtils.isEmpty(source)
                ? source
                : source.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&")
                .replaceAll("&quot;", "\"");
    }


    /**
     * @param s str
     * @return String
     */
    public static String fullWidthToHalfWidth(String s) {

        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            }
            else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            }
            else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }


    /**
     * @param s string
     * @return numeric value
     */
    public static String halfWidthToFullWidth(String s) {

        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            }
            else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            }
            else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }


    /**
     * @param str source
     * @return the string after special-character replacement
     */

    public static String replaceBlanktihuan(String str) {

        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    /**
     * True when the string is null or empty.
     *
     * @param string the string
     */
    public static boolean isEmpty(String string) {
        return string == null || "".equals(string.trim());
    }


    /**
     * True when the string is not null and not empty.
     *
     * @param string the string
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }


    /**
     * True when every string in the array is null or empty.
     *
     * @param strings the strings
     */
    public static boolean isEmpty(String... strings) {
        boolean result = true;
        for (String string : strings) {
            if (isNotEmpty(string)) {
                result = false;
                break;
            }
        }
        return result;
    }


    /**
     * True when every string in the array is not null and not empty.
     *
     * @param strings the string array
     * @return true when none of them is null or empty
     */
    public static boolean isNotEmpty(String... strings) {
        boolean result = true;
        for (String string : strings) {
            if (isEmpty(string)) {
                result = false;
                break;
            }
        }
        return result;
    }


    /**
     * Return "" when the string is null or empty.
     */
    public static String filterEmpty(String string) {
        return StringUtils.isNotEmpty(string) ? string : "";
    }


    /**
     * Replace every old character with the new character.
     *
     * @param string the string
     * @param oldchar character to replace
     * @param newchar replacement character
     * @return the replaced string
     */
    public static String replace(String string, char oldchar, char newchar) {
        char chars[] = string.toCharArray();
        for (int w = 0; w < chars.length; w++) {
            if (chars[w] == oldchar) {
                chars[w] = newchar;
                break;
            }
        }
        return new String(chars);
    }


    /**
     * Split the string on the given character.
     *
     * @param string the string
     * @param ch split character
     * @return the split parts
     */
    public static String[] split(String string, char ch) {
        ArrayList<String> stringList = new ArrayList<String>();
        char chars[] = string.toCharArray();
        int nextStart = 0;
        for (int w = 0; w < chars.length; w++) {
            if (ch == chars[w]) {
                stringList.add(new String(chars, nextStart, w - nextStart));
                nextStart = w + 1;
                if (nextStart ==
                        chars.length) {    // a trailing separator adds an extra empty part
                    stringList.add("");
                }
            }
        }
        if (nextStart <
                chars.length) {    // without a trailing separator, keep the text after the last separator
            stringList.add(new String(chars, nextStart,
                    chars.length - 1 - nextStart + 1));
        }
        return stringList.toArray(new String[stringList.size()]);
    }


    /**
     * Length where one CJK character counts as 2 and one other character counts as 1.
     *
     * @param string the string
     * @return length
     */
    public static int countLength(String string) {
        int length = 0;
        char[] chars = string.toCharArray();
        for (int w = 0; w < string.length(); w++) {
            char ch = chars[w];
            if (ch >= '\u0391' && ch <= '\uFFE5') {
                length++;
                length++;
            }
            else {
                length++;
            }
        }
        return length;
    }


    private static char[] getChars(char[] chars, int startIndex) {
        int endIndex = startIndex + 1;
        // the first character is a digit
        if (Character.isDigit(chars[startIndex])) {
            // the next character is a digit
            while (endIndex < chars.length &&
                    Character.isDigit(chars[endIndex])) {
                endIndex++;
            }
        }
        char[] resultChars = new char[endIndex - startIndex];
        System.arraycopy(chars, startIndex, resultChars, 0, resultChars.length);
        return resultChars;
    }


    /**
     * True when every character is a digit.
     */
    public static boolean isAllDigital(char[] chars) {
        boolean result = true;
        for (int w = 0; w < chars.length; w++) {
            if (!Character.isDigit(chars[w])) {
                result = false;
                break;
            }
        }
        return result;
    }




    /**
     * Remove every occurrence of a character.
     *
     * @param string source string
     * @param ch character to remove
     * @return the string after removal
     */
    public static String removeChar(String string, char ch) {
        StringBuffer sb = new StringBuffer();
        for (char cha : string.toCharArray()) {
            if (cha != '-') {
                sb.append(cha);
            }
        }
        return sb.toString();
    }


    /**
     * Remove the character at the given index.
     *
     * @param string the string
     * @param index index
     */
    public static String removeChar(String string, int index) {
        String result = null;
        char[] chars = string.toCharArray();
        if (index == 0) {
            result = new String(chars, 1, chars.length - 1);
        }
        else if (index == chars.length - 1) {
            result = new String(chars, 0, chars.length - 1);
        }
        else {
            result = new String(chars, 0, index) +
                    new String(chars, index + 1, chars.length - index);
            ;
        }
        return result;
    }


    /**
     * Remove the character at the given index.
     *
     * @param string the string
     * @param index index
     * @param ch removed only when it matches the character at the index
     */
    public static String removeChar(String string, int index, char ch) {
        String result = null;
        char[] chars = string.toCharArray();
        if (chars.length > 0 && chars[index] == ch) {
            if (index == 0) {
                result = new String(chars, 1, chars.length - 1);
            }
            else if (index == chars.length - 1) {
                result = new String(chars, 0, chars.length - 1);
            }
            else {
                result = new String(chars, 0, index) +
                        new String(chars, index + 1, chars.length - index);
                ;
            }
        }
        else {
            result = string;
        }
        return result;
    }


    /**
     * Treat a blank string as empty.
     *
     * @param string the string
     * @return null when the string is blank, otherwise the string itself
     */
    public static String filterBlank(String string) {
        if ("".equals(string)) {
            return null;
        }
        else {
            return string;
        }
    }


    /**
     * Lowercase the given range.
     *
     * @param str the string
     * @param beginIndex start index, inclusive
     * @param endIndex end index, exclusive
     * @return the new string
     */
    public static String toLowerCase(String str, int beginIndex, int endIndex) {
        return str.replaceFirst(str.substring(beginIndex, endIndex),
                str.substring(beginIndex, endIndex)
                        .toLowerCase(Locale.getDefault()));
    }


    /**
     * Uppercase the given range.
     *
     * @param str the string
     * @param beginIndex start index, inclusive
     * @param endIndex end index, exclusive
     * @return the new string
     */
    public static String toUpperCase(String str, int beginIndex, int endIndex) {
        return str.replaceFirst(str.substring(beginIndex, endIndex),
                str.substring(beginIndex, endIndex)
                        .toUpperCase(Locale.getDefault()));
    }


    /**
     * Lowercase the first character.
     *
     * @param str the string
     * @return the new string
     */
    public static String firstLetterToLowerCase(String str) {
        return toLowerCase(str, 0, 1);
    }


    /**
     * Uppercase the first character.
     *
     * @param str the string
     * @return the new string
     */
    public static String firstLetterToUpperCase(String str) {
        return toUpperCase(str, 0, 1);
    }


    /**
     * MD5 of the given string.
     *
     * @param string the string
     * @return the MD5 hex string
     */
    public static String MD5(String string) {
        String result = null;
        try {
            char[] charArray = string.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }

            StringBuffer hexValue = new StringBuffer();
            byte[] md5Bytes = MessageDigest.getInstance("MD5")
                    .digest(byteArray);
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }

            result = hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * True when the string starts with the prefix, ignoring case.
     *
     * @param sourceString the string
     * @param newString the prefix or suffix
     */
    public static boolean startsWithIgnoreCase(String sourceString, String newString) {
        int newLength = newString.length();
        int sourceLength = sourceString.length();
        if (newLength == sourceLength) {
            return newString.equalsIgnoreCase(sourceString);
        }
        else if (newLength < sourceLength) {
            char[] newChars = new char[newLength];
            sourceString.getChars(0, newLength, newChars, 0);
            return newString.equalsIgnoreCase(String.valueOf(newChars));
        }
        else {
            return false;
        }
    }


    /**
     * True when the string ends with the suffix, ignoring case.
     *
     * @param sourceString the string
     * @param newString the prefix or suffix
     */
    public static boolean endsWithIgnoreCase(String sourceString, String newString) {
        int newLength = newString.length();
        int sourceLength = sourceString.length();
        if (newLength == sourceLength) {
            return newString.equalsIgnoreCase(sourceString);
        }
        else if (newLength < sourceLength) {
            char[] newChars = new char[newLength];
            sourceString.getChars(sourceLength - newLength, sourceLength,
                    newChars, 0);
            return newString.equalsIgnoreCase(String.valueOf(newChars));
        }
        else {
            return false;
        }
    }


    /**
     * If the string is longer than maxLength, keep the first maxLength characters and append appendString.
     */
    public static String checkLength(String string, int maxLength, String appendString) {
        if (string.length() > maxLength) {
            string = string.substring(0, maxLength);
            if (appendString != null) {
                string += appendString;
            }
        }
        return string;
    }


    /**
     * If the string is longer than maxLength, keep the first maxLength characters and append an ellipsis.
     */
    public static String checkLength(String string, int maxLength) {
        return checkLength(string, maxLength, "…");
    }


    /**
     * Strip HTML tags.
     *
     * @param inputString
     * @return
     */
    public static String htmlRemoveTag(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // string that may contain HTML tags
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        try {
            // script tag pattern
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // style tag pattern
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>"; // HTML tag pattern
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // drop script tags
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // drop style tags
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // drop HTML tags
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;
    }

    public static boolean isMobileNO(String mobiles) {
        /*
        China Mobile: 134, 135, 136, 137, 138, 139, 150, 151, 157(TD), 158, 159, 187, 188
        China Unicom: 130, 131, 132, 152, 155, 156, 185, 186
        China Telecom: 133, 153, 180, 189, (1349 satellite)
        The first digit is 1, the second is 3, 5, or 8, and the rest are 0-9.
        */
        String telRegex = "[1][3758]\\d{9}";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    public static boolean isPasswd(String passwd){

        String telRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,12}$";
        if (TextUtils.isEmpty(passwd))
            return false;
        else
            return passwd.matches(telRegex);
    }

    public static boolean isPwdFormat(String passwd){

        String telRegex = "^(?=[`~\\$%\\^&\\(\\)\\\\\\|\\[\\]\\{\\}:;\\\"\\',<>\\/\\d]*[a-zA-Z]+)(?=[a-zA-Z`~\\$%\\^&\\(\\)\\\\\\|\\[\\]\\{\\}:;\\\"\\',<>\\/\\?]*\\d+)[`~\\$%\\^&\\(\\)\\\\\\|\\[\\]\\{\\}:;\\\"\\',<>\\/\\?\\w]{8,12}$";
        if (TextUtils.isEmpty(passwd))
            return false;
        else
            return passwd.matches(telRegex);
    }

    public static boolean isSmsCode(String code) {
        String telRegex = "^\\d{4}$";
        if (TextUtils.isEmpty(code))
            return false;
        else
            return code.matches(telRegex);
    }

    /**
     * Phone rule: the second digit is one of 3, 5, 7, 8.
     */
    public static final String REGEX_MOBILE = "^[1][3578][0-9]{9}$";

    /**
     * Validate a mobile number.
     * @param mobile
     * @return true when the number matches
     */
    public static boolean isMobile(String mobile) {
        if(TextUtils.isEmpty(mobile)){
            return false;
        }else {
            return Pattern.matches(REGEX_MOBILE, mobile);
        }
    }

    /**
     * Tell the user the input is longer than the limit.
     * @param context
     * @param editText
     * @param max_length
     * @param err_msg
     */

    public static void lengthFilter(final Context context, EditText editText,
                                    final int max_length, final String err_msg){

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(max_length){

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // TODO Auto-generated method stub
                // character count, one CJK character counts as 2
                int destLen = StringUtils.getCharacterNum(dest.toString());
                int sourceLen = StringUtils.getCharacterNum(source.toString());
                if(destLen + sourceLen > max_length){
                    Toast.makeText(context, err_msg,Toast.LENGTH_SHORT).show();
                    return "";
                }
                return source;

            }

        };
        editText.setFilters(filters);
    }

    /**
     *
     * @param content
     * @return
     */
    public static int getCharacterNum(String content){
        if(content.equals("")||null == content){
            return 0;
        }else {
            return content.length()+getChineseNum(content);
        }
    }


    /**
     * CJK-aware length of the string.
     * @param s
     * UTF-8 estimate: length plus 2 per extra byte, one byte is treated as 3 characters.
     * @return
     */
    public static int getChineseNum(String s){
        int num = 0;
        char[] myChar = s.toCharArray();
        for(int i=0;i<myChar.length;i++){
            if((char)(byte)myChar[i] != myChar[i]){
                num=num+2;
            }
        }
        return num;
    }


    /**
     * Readable but slow and it allocates.
     *
     * @param s
     * @return
     */
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int l = s.length();
        int ch = -1;
        int b, sumb = 0;
        for (int i = 0, more = -1; i < l; i++) {
            /* Get next byte b from URL segment s */
            switch (ch = s.charAt(i)) {
                case '%':
                    ch = s.charAt(++i);
                    int hb = (Character.isDigit((char) ch) ? ch - '0'
                            : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    ch = s.charAt(++i);
                    int lb = (Character.isDigit((char) ch) ? ch - '0'
                            : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    b = (hb << 4) | lb;
                    break;
                case '+':
                    b = ' ';
                    break;
                default:
                    b = ch;
            }
            /* Decode byte b as UTF-8, sumb collects incomplete chars */
            if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)
                sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb
                if (--more == 0)
                    sbuf.append((char) sumb); // Add char to sbuf
            } else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
                sbuf.append((char) b); // Store in sbuf
            } else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
                sumb = b & 0x1f;
                more = 1; // Expect 1 more byte
            } else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
                sumb = b & 0x0f;
                more = 2; // Expect 2 more bytes
            } else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
                sumb = b & 0x07;
                more = 3; // Expect 3 more bytes
            } else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
                sumb = b & 0x03;
                more = 4; // Expect 4 more bytes
            } else /*if ((b & 0xfe) == 0xfc)*/{ // 1111110x (yields 1 bit)
                sumb = b & 0x01;
                more = 5; // Expect 5 more bytes
            }
            /* We don't test if the UTF-8 encoding is well-formed */
        }
        return sbuf.toString();
    }

    private static char[] filterStr=new char []{'，','。','、','？','！',':'};
    public static String replaceSymbol(String content){
        String newString=content;

        char[] chars = content.toCharArray();


        for (int i = 0; i < chars.length ; i++) {

            for (int j = 0; j < filterStr.length ; j++)
            {
                if (filterStr[j]==chars[i]) {
                    Log.d("repalcceFilterStr",""+filterStr[j]);
                    Log.d("repalcceChars",""+chars[i]);
//                    removeChar(newString,i);
//                    replace(newString,chars[i],'');
                    Log.d("repaaContent",""+content);
                    Log.d("repaNewString",""+newString);
                }
            }
        }
        Log.d("repalcceContent",""+content);
        return newString;
    }

    public static String formatSymbol(String s){
        String str=s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        return str;
    }

    /**
     * Join a list into a string.
     * @param list
     * @return
     */
    public static String listToString(List<String> list){
        if(list==null){
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        // do not add a comma before the first item
        for(String string :list) {
            if(first) {
                first=false;
            }else{
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }


    /**
     * Split a string into a list.
     * @param strs
     * @return
     */
    public static List<String> stringToList(String strs){
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }

    /**
     * Keep only the first 7 characters.
     * @param str
     * @return
     */
    public static String getViewString(String str) {
        if (str != null && !"".equals(str)) {
            if (str.length() > 7) {
                String str1 = str.substring(0, 7) + "...";
                return str1;
            } else {
                return str;
            }
        }else {
            return "";
        }
    }
}
