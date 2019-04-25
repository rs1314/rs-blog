package cn.rs.blog.commoms.utils.sign;

import java.net.URLDecoder;

public class SignDecodeUtils {

    public static String decodeprivatekey(String temp, String prikey) {
        if (temp == null || "".equalsIgnoreCase(temp)) {
            return "-1";
        }
        try {
            if (temp.contains("%")) {
                temp = URLDecoder.decode(temp, "utf-8");
            }

            byte[] decode = Base64.decode(temp);
            byte[] decodedData = RSACoder.decryptByPrivateKey(decode,
                    prikey);
            temp = new String(decodedData);
        } catch (Exception e) {

            temp = "-1";
        }
        return temp;
    }

}
