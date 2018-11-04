package cn.rs.blog.commoms.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class EmailSendUtil {

	private EmailSendUtil(){

	}

	private static boolean sendMail(HttpServletRequest request, String email, String content,String title) {
		final String account = (String)request.getServletContext().getAttribute(ConfigUtil.SITE_SEND_EMAIL_ACCOUNT.toUpperCase());
		final String passWord = (String)request.getServletContext().getAttribute(ConfigUtil.SITE_SEND_EMAIL_PASSWORD.toUpperCase());
		final String smtp = (String)request.getServletContext().getAttribute(ConfigUtil.SITE_SEND_EMAIL_SMTP.toUpperCase());
		SendMail mail = new SendMail(account, passWord);

		Map<String, String> map = new HashMap<String, String>();
		map.put("mail.smtp.host", smtp);
		map.put("mail.smtp.auth", "true");
		map.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		map.put("mail.smtp.port", "465");
		map.put("mail.smtp.socketFactory.port", "465");
		mail.setPros(map);
		mail.initMessage();

		List<String> list = new ArrayList<String>();
		list.add(email);
		try {
			mail.setRecipients(list);
			mail.setSubject(title);
			mail.setDate(new Date());
			mail.setFrom(ConfigUtil.RS_BLOG);
			mail.setContent(content,"text/html; charset=UTF-8");
			mail.sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return  false;
		}

		return true;
	}

	/**
	 * 会员激活
	 * @param email
	 * @param randomCode
	 * @return
	 */
	public static boolean activeMember(HttpServletRequest request, String email, String randomCode){
		String siteName = (String) request.getServletContext().getAttribute(ConfigUtil.SITE_NAME.toUpperCase());
		String title = siteName + "会员账号激活";
		String content = "欢迎加入"+siteName+"，您的账号激活验证码为：【"+randomCode+"】，30分钟内有效，请马上进行验证。若非本人操作，请忽略此邮件。";
		return sendMail(request, email, content, title);
	}

	/**
	 * 忘记密码
	 * @param email
	 * @param randomCode
	 * @return
	 */
	public static boolean forgetpwd(HttpServletRequest request, String email, String randomCode){
		String siteName = (String) request.getServletContext().getAttribute(ConfigUtil.SITE_NAME.toUpperCase());
		String siteDomain = (String) request.getServletContext().getAttribute(ConfigUtil.SITE_DOMAIN.toUpperCase());
		String title = siteName + "找回密码";
		String content = "<h4>您好，" + email + "：</h4><p>请点击下面的链接来重置您的密码<br  />" +
				"<a href='" + siteDomain+"member/resetpwd?email=" + email + "&token=" + randomCode + "' target='_blank'>" +
				siteDomain + "member/resetPwd?email=" + email + "&token=" + randomCode + "</a><br  />" +
				"本链接30分钟内有效。<br />" +
				"(如果点击链接无反应，请复制链接到浏览器里直接打开)<p>" ;
		return sendMail(request, email, content, title);
	}

}
