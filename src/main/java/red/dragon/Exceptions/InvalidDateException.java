package red.dragon.Exceptions;

public class InvalidDateException extends Exception
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public InvalidDateException()
	{
        super();
    }
	
	public InvalidDateException(String s)
	{
        super(s);
    }
}
