package red.dragon.Exceptions;

public class DuplicateItemException extends Exception
{
	/**
	 * Creates a new duplicate item exception.
	 */
	private static final long serialVersionUID = 1L;
	public DuplicateItemException()
    {
        super();
    }
    public DuplicateItemException(String s)
    {
        super(s);
    }
}
