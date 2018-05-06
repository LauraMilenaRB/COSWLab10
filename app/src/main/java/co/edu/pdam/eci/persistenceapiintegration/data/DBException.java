package co.edu.pdam.eci.persistenceapiintegration.data;

/**
 * @author Santiago Carrillo
 */

public class DBException
    extends Exception
{
    public DBException( String detailMessage, Throwable throwable )
    {
        super( detailMessage, throwable );
    }
}
