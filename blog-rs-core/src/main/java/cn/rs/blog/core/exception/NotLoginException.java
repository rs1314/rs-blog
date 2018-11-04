package cn.rs.blog.core.exception;

import cn.rs.blog.core.enums.Messages;

/**
 * 未登录异常
 * Created by rs
 */
public class NotLoginException extends JeeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4252307407841147300L;

	public NotLoginException(){
        super(Messages.UN_LOGIN);
    }

	public NotLoginException(String msg){
		super(msg);
	}
}
