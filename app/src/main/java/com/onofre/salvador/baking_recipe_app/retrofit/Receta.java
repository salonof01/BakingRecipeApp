


package com.onofre.salvador.baking_recipe_app.retrofit;


import com.onofre.salvador.baking_recipe_app.pojito.Recipes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Receta {
    @GET("baking.json")
    Call<ArrayList<Recipes>> getRecipe();
}