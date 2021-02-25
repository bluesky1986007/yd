package common.frame.utils;

import com.google.common.collect.Maps;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @Desc: 生成签名
 * @Author: piteng
 * @Date: 2021/1/4 14:15
 */
public class SignUtilsOfJava {

    private static final Map<String,String> ECRET_KEY_MAP= Maps.newHashMap();
    static {
        ECRET_KEY_MAP.put("jinribaotuan", "jjjiey863osQ*Vcasdfkl;erh4Gy*PibKg*eNdDs");
    }

    public static String getSignStr(Map<String, Object> paramMap, String mapKey) {
        String secretKey=ECRET_KEY_MAP.get(mapKey);
        //1、对参数key做升序排序
        Set<String> keys = paramMap.keySet();
        Object[] arr = keys.toArray();
        Arrays.sort(arr);
        //2、对参数使用 & 进行拼接，
        String signStr = "";
        for (Object obj : arr) {
            signStr += obj + "=" + paramMap.get(obj) + "&";
        }
        signStr = signStr.substring(0, signStr.length()-1);
        signStr = secretKey + signStr + secretKey;
        return md5(signStr);
    }


    private static String md5(String text) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(text.getBytes());
        } catch (NoSuchAlgorithmException e) {
//            log.error("SignUtils md5|没有MD5加密", e);
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }


}
