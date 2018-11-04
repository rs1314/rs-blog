package cn.rs.blog.core.exception;

import cn.rs.blog.core.enums.Messages;

/**
 * 参数异常
 * Created by rs
 */
public class ParamException extends JeeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8430082695118026470L;

	public ParamException(String msg){
        super(msg);
    }

    public ParamException(Messages messages){
        super(messages);
    }

    public ParamException(){
        super(Messages.PARAM_ERROR);
    }
}
