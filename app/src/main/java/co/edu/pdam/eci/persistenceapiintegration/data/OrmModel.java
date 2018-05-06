package co.edu.pdam.eci.persistenceapiintegration.data;

import android.content.Context;

import java.sql.SQLException;

import co.edu.pdam.eci.persistenceapiintegration.data.dao.TeamDao;

/**
 * @author Santiago Carrillo
 */

public class OrmModel
    implements Model
{
    private DatabaseHelper helper;

    public synchronized void init( Context context )
    {
        if ( helper == null )
        {
            helper = new DatabaseHelper( context );
        }
    }

    @Override
    public TeamDao getTeamDao()
    {
        try
        {
            return helper.getTeamDao();
        }
        catch ( SQLException e )
        {
            throw new IllegalStateException( "We go DB error while creation dao. App cannot work further", e );
        }
    }
}
