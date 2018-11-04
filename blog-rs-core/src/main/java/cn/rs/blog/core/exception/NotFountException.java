package cn.rs.blog.core.exception;

import cn.rs.blog.core.enums.Messages;

/**
 * 找不到异常
 */
public class NotFountException extends JeeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6998003012044947788L;

	public NotFountException(Messages messages){
        super(messages);
    }

    public NotFountException(String msg){
        super(msg + "不存在");
    }
}
