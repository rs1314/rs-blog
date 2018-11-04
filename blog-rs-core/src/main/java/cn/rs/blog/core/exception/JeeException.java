package cn.rs.blog.core.exception;

import cn.rs.blog.core.enums.Messages;

/**
 * @author: rs
 */
public class JeeException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1367912802202078635L;
	private Messages jeeMessage;


    public JeeException(){
        super();
        jeeMessage = Messages.ERROR;
    }

    public JeeException(String msg){
        super(msg);
    }

    public JeeException(Messages lxiMessage){
        this.jeeMessage = lxiMessage;
    }

    public Messages getJeeMessage() {
        return jeeMessage;
    }
}
