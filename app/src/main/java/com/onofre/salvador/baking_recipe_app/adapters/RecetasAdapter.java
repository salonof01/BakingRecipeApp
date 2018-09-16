

package com.onofre.salvador.baking_recipe_app.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.onofre.salvador.baking_recipe_app.R;
import com.onofre.salvador.baking_recipe_app.pojito.Recipes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecetasAdapter extends RecyclerView.Adapter<RecetasAdapter.RecyclerViewHolder> {

    ArrayList<Recipes> Recetas;
    Context mContext;
    final private ListItemClickListener lOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(Recipes clickedItemIndex);
    }

    public RecetasAdapter(ListItemClickListener listener) {
        lOnClickListener =listener;
    }


    public void setRecipeData(ArrayList<Recipes> recipesIn, Context context) {
        Recetas = recipesIn;
        mContext=context;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_cardview_items;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup,  false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
       holder.textRecyclerView.setText(Recetas.get(position).getName());
        String imageUrl= Recetas.get(position).getImage();

        if (imageUrl!="") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(mContext).load(builtUri).into(holder.imageRecyclerView);
        }

    }

    @Override
    public int getItemCount() {
        return Recetas !=null ? Recetas.size():0 ;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textRecyclerView;
        ImageView imageRecyclerView;


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            textRecyclerView = (TextView) itemView.findViewById(R.id.title);
            imageRecyclerView = (ImageView) itemView.findViewById(R.id.recipeImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(Recetas.get(clickedPosition));
        }

    }
}
