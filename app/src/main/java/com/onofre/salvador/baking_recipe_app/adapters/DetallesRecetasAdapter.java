


package com.onofre.salvador.baking_recipe_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.onofre.salvador.baking_recipe_app.R;
import com.onofre.salvador.baking_recipe_app.pojito.Recipes;
import com.onofre.salvador.baking_recipe_app.pojito.Steps;

import java.util.List;


public class DetallesRecetasAdapter extends RecyclerView.Adapter<DetallesRecetasAdapter.RecyclerViewHolder> {

    List<Steps> Pasos;
    private String recipeName;

    final private ListItemClickListener lOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Steps> stepsOut, int clickedItemIndex, String recipeName);
    }

    public DetallesRecetasAdapter(ListItemClickListener listener) {
        lOnClickListener =listener;
    }


    public void setMasterRecipeData(List<Recipes> recipesIn, Context context) {

        Pasos= recipesIn.get(0).getSteps();
        recipeName=recipesIn.get(0).getName();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.recipe_detail_cardview_items;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
       holder.textRecyclerView.setText(Pasos.get(position).getId()+". "+ Pasos.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {

        return Pasos !=null ? Pasos.size():0 ;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textRecyclerView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            textRecyclerView = (TextView) itemView.findViewById(R.id.shortDescription);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(Pasos,clickedPosition,recipeName);
        }

    }
}
