package XML.xmlmanager.exceptions;

public abstract class AbstractVoogaException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Exception causeException;
	
//	public AbstractVoogaException() { super(); }
//	public AbstractVoogaException(String message) { super(message); }
//	public AbstractVoogaException(String message, Throwable cause) { super(message, cause); }
//	public AbstractVoogaException(Throwable cause) { super(cause); }
	public AbstractVoogaException(Exception ex){ causeException = ex; }
	
		
	public Exception getCauseException(){
		return causeException;
	}
}
