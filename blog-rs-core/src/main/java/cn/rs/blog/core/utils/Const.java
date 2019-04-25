package cn.rs.blog.core.utils;

public class Const {

	public static final String SYSTEM_NAME = "rs";
	public static final String SYSTEM_VERSION = "1.3";
	public static final String SYSTEM_UPDATE_TIME = "2018-08-03";
	public static final String LAST_SYSTEM_VERSION = "1.3";
	public static final String LAST_SYSTEM_UPDATE_TIME = "2018-08-03";

	/**
	 * 项目路径
	 */
	public static String PROJECT_PATH;

	/**
	 * 文件上传路径
     */
	public static final String UPLOAD_PATH = "/upload";

	/**
	 * SESSION中会员的key
     */
	public static final String SESSION_MEMBER = "loginMember";
	/**
	 * SESSION中管理员的key
     */
	public static final String SESSION_ADMIN = "LOGIN_ADMIN";

	/**
	 * 后台错误页面的ftl路径
	 */
	public static final String MANAGE_ERROR_FTL_PATH = "/manage/common/error";

	/**
	 * 前台错误页面的ftl路径
	 */
	public static final String INDEX_ERROR_FTL_PATH = "/common/error";

	/**
	 * 默认图片路径
	 */
	//public web final String DEFAULT_IMG_URL = "/res/common/images/nopic.png";
	public static final String DEFAULT_IMG_URL = "https://ws3.sinaimg.cn/large/9406510dgy1fwut2ueho3j20n409uglx.jpg";

	/**
	 * 默认头像
	 */
	//public web final String DEFAULT_AVATAR = "/res/common/images/default-avatar.png";
	public static final String DEFAULT_AVATAR = "https://ws2.sinaimg.cn/large/9406510dgy1fwut1entzuj205k05kq2q.jpg";

	/**
	 * 默认相册封面
	 */
	//public web final String DEFAULT_PICTURE_COVER = "/res/common/images/empty_album.png";
	public static final String DEFAULT_PICTURE_COVER = "https://ws1.sinaimg.cn/large/9406510dgy1fwut3rw6nej205k05kt8j.jpg";
	/**
	 * 群组路径
	 */
	public static String GROUP_PATH = "/group/";
	/**
	 * 微博路径
	 */
	public static String WEIBO_PATH = "/weibo/";

}
