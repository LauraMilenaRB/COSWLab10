package co.edu.pdam.eci.persistenceapiintegration.data.dao;

import com.j256.ormlite.dao.Dao;

import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;

/**
 * @author Santiago Carrillo
 */

public class TeamOrmLiteDao
    extends OrmLiteDao<Team, Long>
    implements TeamDao
{
    public TeamOrmLiteDao( Dao<Team, Long> dao )
    {
        super( dao, Team.class );
    }
}
