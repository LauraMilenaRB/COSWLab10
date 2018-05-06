package co.edu.pdam.eci.persistenceapiintegration.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.pdam.eci.persistenceapiintegration.R;
import co.edu.pdam.eci.persistenceapiintegration.data.DBException;
import co.edu.pdam.eci.persistenceapiintegration.data.OrmModel;
import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;
import co.edu.pdam.eci.persistenceapiintegration.network.NetworkException;
import co.edu.pdam.eci.persistenceapiintegration.network.RequestCallback;
import co.edu.pdam.eci.persistenceapiintegration.network.RetrofitNetwork;
import co.edu.pdam.eci.persistenceapiintegration.ui.adapter.TeamsAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrmModel ormModel;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        configureRecyclerView();
        ConectToApiNetwork ca = new ConectToApiNetwork();
    }


    private void configureRecyclerView() {
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        recyclerView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );

    }

    private void createTeamLocalDB(){
        ormModel = new OrmModel();
        ormModel.init(this);
        Team team = new Team();
        team.setName("América de cali S.A.");
        team.setShortName("America");
        team.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/0/00/Renovado_America_club.png");
        try {
            ormModel.getTeamDao().createOrUpdate(team);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void getAllTeamsLocalDB(){
        try {
            List<Team> teams = ormModel.getTeamDao().getAll();
            for(Team tea: teams){
                Log.i("Name",tea.getShortName()+"");
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private class ConectToApiNetwork{
        RetrofitNetwork rfN;
        ExecutorService executorService;
        private List<Team> teams;


        public ConectToApiNetwork() {
            rfN = new RetrofitNetwork();
            executorService = Executors.newFixedThreadPool(1);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    rfN.getTeams(new RequestCallback<List<Team>>() {
                        @Override
                        public void onSuccess(List<Team> response) {
                            teams = response;
                        }

                        @Override
                        public void onFailed(NetworkException e) {
                            e.printStackTrace();
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new TeamsAdapter(teams));
                        }
                    });
                }
            });
        }





    }
}
