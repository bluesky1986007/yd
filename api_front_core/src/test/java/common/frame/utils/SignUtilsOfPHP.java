package common.frame.utils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SignUtilsOfPHP {


    public static final String SUCCESS_CODE = "00";
    public static final Map<String, String> OS_MAP_SECRET_KEY = new HashMap();
    static {
        OS_MAP_SECRET_KEY.put("app", "EDf@YOssLy8tJxSPUC$WV4B10ErEwmOq3kNZv^qL");
        OS_MAP_SECRET_KEY.put("iphone_user", "ahMDe8VPy98siswD431wPqeYG7nOzRttPxA5ltqzald");
        OS_MAP_SECRET_KEY.put("android_user", "xAbQsa4Kalkxlet8170OGI61M2iEEjvNvxkU25oaald");
        OS_MAP_SECRET_KEY.put("manage_user", "6gOk5YXXilxJyxllcRk6iIDzavj9gZERSZqqdgPzald");
        OS_MAP_SECRET_KEY.put("h5", "593uGwELo59p86NEF4VUe0FqNkHtVhObNsDHYzgOald");
        OS_MAP_SECRET_KEY.put("wx", "984uGwELo59p62NLF4Vke0FqNkHtVhKbNsDHYzgipda");
        OS_MAP_SECRET_KEY.put("minwechat", "034uJwELo00p69NLa6Hke0FqNkMtVoKbgsAGtzgidjh");
        OS_MAP_SECRET_KEY.put("jinribaotuan", "jjjiey863osQ*Vcasdfkl;erh4Gy*PibKg*eNdDs");
        OS_MAP_SECRET_KEY.put("yunhuotemai", "asdfiey863osQ*Vcasdfkl;erh4Gy*PibKg*eNdDs");
        OS_MAP_SECRET_KEY.put("huijiayanxuan", "huijiayanxuan63osQ*Vhuijiayanxuan&6372ings");
        OS_MAP_SECRET_KEY.put("jingxiaolai", "jxaliey863osQ*Vcasdfkl;erh4Gy*PibKg*eNdDs");
        OS_MAP_SECRET_KEY.put("pinkutehui", "pkth863osQ*Vcasdfkl;erh4Gy*PibKg*eNdDs");
        OS_MAP_SECRET_KEY.put("yixintuan", "yxtg63osQ*Vcasdfkl;erh4Gy*aladings");
        OS_MAP_SECRET_KEY.put("temaicangku", "tmck863osQ*Vcasdfkl;erh4Gy*PibKg*eNdDs");
        OS_MAP_SECRET_KEY.put("xiaohuojing", "rS2CBqK7S*BsM6YB!6TaT9ypNTPM5@vn#btmxu9Q");
        OS_MAP_SECRET_KEY.put("shanpinxiaopu", "spxpkoqF^Cs&lIOV@&o&qDccxYrtfq^o@vxU6hTS");
        OS_MAP_SECRET_KEY.put("dapaigou", "dpg2#Cs9OOsLSCeyMgYIqvhI4zEF@5TnDRX@d5^dpg");
        OS_MAP_SECRET_KEY.put("yizheqi", "yizheqi63osQ*Vcasdfkl;erh4Gy*PibKg*eNdDs");
        OS_MAP_SECRET_KEY.put("dapaijingpin", "MZS@&DCO5iry$2uGLHB20MUbRemtbrDzCbC0^XzM");
        OS_MAP_SECRET_KEY.put("weijia", "uda;kfj8OIj392kd@*fidj83n;ncvpidufPW739jcncud");
        OS_MAP_SECRET_KEY.put("pugongying", "PgY63osQ*Vhuijiayanxuan&6372ings");
        OS_MAP_SECRET_KEY.put("weineigou", "weineigou863osQ*Vcasdfkl;erh4Gy*PibKg*eNdDsD");
        OS_MAP_SECRET_KEY.put("xingfushangou", "XFrS2CBqK7S*BsM6YB!6TaT9ypNTPM5@vn#btmxuSG");
        OS_MAP_SECRET_KEY.put("weiyungou", "WY63osQ*Vhuijiayanxuan&6372ingG");
    }


    /**
     * 验签
     * @param params
     * @return
     */
    public static Map<String,String> checkSign(Map<String, Object> params){
        //check appKey
        if(!params.containsKey("appKey")){
            return setResult("1002","appKey不能为空");
        }
        String appKey = params.get("appKey").toString();
        if (!OS_MAP_SECRET_KEY.containsKey(appKey)) {
            return setResult("1006","appKey错误");
        }

        //check timestamp
        if(!params.containsKey("tstamp")){
            return setResult("1007","时间戳不能为空");
        }
        long currentTime = (int)(System.currentTimeMillis()/1000);
        long requestTime = Long.parseLong(params.get("tstamp").toString());
        long diffTime = currentTime-requestTime;
        if(diffTime>600 || currentTime<0){
            return setResult("1008","签名已过期");
        }

        //check sign
        if(!params.containsKey("sign")){
            return setResult("1001","签名不能为空");
        }
        String sign=params.get("sign").toString();
        params.remove("sign");
        String calcSign = calcSign(params);
        if(!sign.equals(calcSign)){
            return setResult("1001","签名错误");
        }

        return setResult(SUCCESS_CODE,"success");

    }

    /**
     * 计算签名
     * @param params
     * @return
     */
    public static String calcSign(Map<String, Object> params) {
        if(params.containsKey("sign")){
            params.remove("sign");
        }

        String appKey = params.get("appKey").toString();
        Set<String> set = params.keySet();
        Object[] arr = set.toArray();
        Arrays.sort(arr);

        String signStr = "";
        for (Object key : arr) {
            if (key.equals("base64_file")){
                continue;
            }
            Object value = params.get(key);
            if(value != null){
                signStr += key + "=" + value.toString() + "&";
            }else{
                signStr += key + "=" + ""+ "&";

            }
        }
        signStr = signStr.substring(0, signStr.length() - 1);
        String secretKey = OS_MAP_SECRET_KEY.get(appKey);
        signStr = secretKey + signStr + secretKey;
        String sign = stringToMD5(signStr);
        return sign;
    }


    /**
     * 计算md5值
     * @param str
     * @return
     */
    public static String stringToMD5(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            int c = b & 0xFF ;
            if(c < 16){
                sb.append("0");
            }
            sb.append(Integer.toHexString(c));
        }
        return sb.toString();
    }

    /**
     * 格式化返回
     * @param code
     * @param msg
     * @return
     */
    public static Map<String,String> setResult(String  code,String msg){
        Map result= new HashMap();
        result.put("code",code);
        result.put("msg",msg);
        return result;
    }
}