package co.edu.pdam.eci.persistenceapiintegration.network;

/**
 * Created by LaurarRB on 5/05/2018.
 */

public interface RequestCallback<T>{

    void onSuccess( T response );

    void onFailed( NetworkException e );
}
