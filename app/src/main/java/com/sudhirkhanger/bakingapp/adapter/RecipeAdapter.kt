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

package com.sudhirkhanger.bakingapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sudhirkhanger.bakingapp.R
import com.sudhirkhanger.bakingapp.model.Recipe

// https://medium.com/@paul.allies/kotlin-with-recyclerview-1637145b170f
class RecipeAdapter(val recipes: MutableList<Recipe>,
                    private val itemClick: OnItemClickListener) :
        RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    interface OnItemClickListener {
        operator fun invoke(recipe: Recipe)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bindRecipe(recipes[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.recipe_list_item, parent, false)
        return RecipeViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    class RecipeViewHolder(view: View, private val itemClick: OnItemClickListener)
        : RecyclerView.ViewHolder(view) {
        val recipeName = view.findViewById<TextView>(R.id.textview_recipe)

        fun bindRecipe(recipe: Recipe) {
            with(recipe) {
                recipeName.text = name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}