package OperationPlatform;

import Tools.MD5Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SignUtils {

    public static String createSign(Map<String, String> params, boolean encode) throws UnsupportedEncodingException {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }
            if (encode) {
                temp.append(URLEncoder.encode(valueString, "UTF-8"));
            } else {
                temp.append(valueString);
            }
        }

        return MD5Util.encode(temp.toString()).toUpperCase();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Map<String,String> mp = new HashMap<String,String>();
        mp.put("username", "AutoTest05");
        mp.put("mima", "1234");


        String str = createSign(mp, true);
        System.err.println(str);
    }

}