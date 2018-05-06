package co.edu.pdam.eci.persistenceapiintegration.services;

import java.util.List;

import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by JuanArevaloMerchan on 11/04/2018.
 */

public interface TeamsService {

    @GET( "teams.json" )
    Call<List<Team>> getTeamsList( );
}
