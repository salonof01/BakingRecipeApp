
/*
 * Copyright 2018 Onofre Salvador
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.onofre.salvador.baking_recipe_app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.onofre.salvador.baking_recipe_app.R;


import com.onofre.salvador.baking_recipe_app.IdlingResource.SimpleIdlingResource;
import com.onofre.salvador.baking_recipe_app.adapters.RecetasAdapter;
import com.onofre.salvador.baking_recipe_app.pojito.Recipes;
import com.onofre.salvador.baking_recipe_app.retrofit.Receta;
import com.onofre.salvador.baking_recipe_app.retrofit.RetrofitBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.onofre.salvador.baking_recipe_app.ui.RecipeActivity.ALL_RECIPES;



public class RecipeFragment extends Fragment {



    public RecipeFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;

        View rootView = inflater.inflate(R.layout.recipe_fragment_body_part, container, false);

        recyclerView=(RecyclerView)  rootView.findViewById(R.id.recipe_recycler);
        RecetasAdapter recipesAdapter =new RecetasAdapter((RecipeActivity)getActivity());
        recyclerView.setAdapter(recipesAdapter);



        if (rootView.getTag()!=null && rootView.getTag().equals("phone-land")){
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),4);
            recyclerView.setLayoutManager(mLayoutManager);
        }
        else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
        }

      Receta iRecipe = RetrofitBuilder.Retrieve();
        Call<ArrayList<Recipes>> recipe = iRecipe.getRecipe();

        SimpleIdlingResource idlingResource = (SimpleIdlingResource)((RecipeActivity)getActivity()).getIdlingResource();

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }


        recipe.enqueue(new Callback<ArrayList<Recipes>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipes>> call, Response<ArrayList<Recipes>> response) {
                Integer statusCode = response.code();
                Log.v("status code: ", statusCode.toString());

                ArrayList<Recipes> recipes = response.body();

                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

                recipesAdapter.setRecipeData(recipes,getContext());
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Recipes>> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
            }
        });

        return rootView;
    }


}
