package co.edu.pdam.eci.persistenceapiintegration.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import co.edu.pdam.eci.persistenceapiintegration.data.dao.TeamDao;
import co.edu.pdam.eci.persistenceapiintegration.data.dao.TeamOrmLiteDao;
import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;

/**
 * @author Santiago Carrillo
 */

public class DatabaseHelper
    extends OrmLiteSqliteOpenHelper
{
    private final String TAG = DatabaseHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 6;

    private static final String DATABASE_NAME = "my_database.db";

    private TeamDao teamDao;

    public DatabaseHelper( Context context )
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    /**
     * Creates/updated tables if necessary
     *
     * @throws IllegalStateException if db operations failed
     */
    @Override
    public void onCreate( SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource )
    {

        try
        {
            TableUtils.createTableIfNotExists( connectionSource, Team.class );
        }
        catch ( SQLException e )
        {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    @Override
    public void onUpgrade( SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion )
    {

    }


    public TeamDao getTeamDao()
        throws SQLException
    {
        if ( teamDao == null )
        {
            Dao<Team, Long> dao = getDao( Team.class );
            teamDao = new TeamOrmLiteDao( dao );
        }
        return teamDao;
    }

}

