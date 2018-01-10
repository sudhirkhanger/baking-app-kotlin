/*
 * Copyright 2018 Sudhir Khanger
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

package com.sudhirkhanger.bakingapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.sudhirkhanger.bakingapp.model.Recipe
import com.sudhirkhanger.bakingapp.adapter.RecipeAdapter
import com.sudhirkhanger.bakingapp.utils.BakingAppJsonUtils
import com.sudhirkhanger.bakingapp.utils.NetworkUtils
import java.net.URL

/**
 * A simple [Fragment] subclass.
 * https://github.com/antoniolg/Kotlin-for-Android-Developers
 */
class MainActivityFragment : Fragment() {

    companion object {
        const val KEY_RECIPE = "recipe_object"
    }

    lateinit var recyclerView: RecyclerView
    lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_activity, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_recipe)
        recyclerView.layoutManager = LinearLayoutManager(
                activity, LinearLayout.VERTICAL, false)
        recipeAdapter = RecipeAdapter(mutableListOf(),
                object : RecipeAdapter.OnItemClickListener {
                    override fun invoke(recipe: Recipe) {
                        val detailActivityIntent = Intent(activity, DetailActivity::class.java)
                        detailActivityIntent.putExtra(KEY_RECIPE, recipe)
                        startActivity(detailActivityIntent)
                    }
                })
        recyclerView.adapter = recipeAdapter

        GetRecipesTask().execute()

        return view
    }

    // https://github.com/irontec/android-kotlin-samples
    inner class GetRecipesTask : AsyncTask<Unit, Unit, MutableList<Recipe>>() {

        override fun doInBackground(vararg params: Unit?): MutableList<Recipe> {
            val url = URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
            val recipeJson = NetworkUtils.getResponseFromHttpUrl(url)
            val recipes = BakingAppJsonUtils.extractRecipeFromJson(recipeJson)
            return recipes
        }

        override fun onPostExecute(result: MutableList<Recipe>?) {
            super.onPostExecute(result)
            recipeAdapter.recipes.clear()
            recipeAdapter.recipes.addAll(result as MutableList<Recipe>)
            recipeAdapter.notifyDataSetChanged()
        }
    }
}
