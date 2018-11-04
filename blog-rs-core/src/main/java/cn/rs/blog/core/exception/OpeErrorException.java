package cn.rs.blog.core.exception;


import cn.rs.blog.core.enums.Messages;

/**
 * @author rs
 */
public class OpeErrorException extends JeeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4006983054016596884L;

	public OpeErrorException(){
        super(Messages.ERROR);
    }

    public OpeErrorException(Messages message){
        super(message);
    }

    public OpeErrorException(String message){
        super(message);
    }
}
