package co.edu.pdam.eci.persistenceapiintegration.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.edu.pdam.eci.persistenceapiintegration.R;
import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;

/**
 * Created by LaurarRB on 5/05/2018.
 */

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder>{
    private final List<Team> teams;
    private Context context;

    public TeamsAdapter(List<Team> response) {
        this.teams = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.team_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Team team = teams.get(position);
        holder.name.setText(team.getName());
        holder.shortName.setText(team.getShortName());
        Picasso.with(context).load(team.getImageUrl()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, shortName;
        ImageView logo;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            shortName = (TextView) view.findViewById(R.id.shortName);
            logo = (ImageView) view.findViewById(R.id.logo);
        }
    }
}
