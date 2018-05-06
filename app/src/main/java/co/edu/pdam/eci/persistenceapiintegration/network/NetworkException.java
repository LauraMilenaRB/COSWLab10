package co.edu.pdam.eci.persistenceapiintegration.network;

/**
 * Created by LaurarRB on 5/05/2018.
 */

public class NetworkException extends Exception {

    public NetworkException(int i, Object o, Exception e){
        e.printStackTrace();
    }
}
