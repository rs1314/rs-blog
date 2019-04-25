package cn.rs.blog.commoms.utils.sign;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	private static final char[] C={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	public static String getInstanceofMD5(String recource){
		String md5=null;
		try {
			//获得加密对象
			MessageDigest digest = MessageDigest.getInstance("md5");
			//获得加密的字符串
			byte[] bytes = recource.getBytes();
			//转成16位的数据
			bytes=digest.digest(bytes);
			//加密前拼接的字符串
			StringBuilder builder=new StringBuilder();
			//取出每个byte进行加密
			for (byte b : bytes) {
				//获得高位
				int start = (b>>4)&15;
				//获得低位
				int end=b&15;
				//进行加密
				builder.append(C[start]).append(C[end]);
			}
			md5=builder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return md5;
	}
}
