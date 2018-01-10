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
import com.sudhirkhanger.bakingapp.model.Ingredients
import com.sudhirkhanger.bakingapp.model.Steps


class DetailRecipeAdapter(private val items: MutableList<Any>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val INGREDIENT_VIEW = 0
        const val STEP_VIEW = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            INGREDIENT_VIEW -> {
                val view = LayoutInflater.from(parent?.context)
                        .inflate(R.layout.recipe_list_item, parent, false)
                return IngredientViewHolder(view)
            }
            STEP_VIEW -> {
                val view = LayoutInflater.from(parent?.context)
                        .inflate(R.layout.recipe_list_item, parent, false)
                return StepViewHolder(view)
            }
            else -> throw IllegalArgumentException("view type is not correct")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder?.itemViewType) {
            INGREDIENT_VIEW -> {
                val ingredientHolder = holder as IngredientViewHolder
                ingredientHolder.bindIngredient(items[position] as Ingredients)
            }
            STEP_VIEW -> {
                val stepHolder = holder as StepViewHolder
                stepHolder.bindStep(items[position] as Steps)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Ingredients -> INGREDIENT_VIEW
            is Steps -> STEP_VIEW
            else -> -1
        }
    }

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView = itemView.findViewById<TextView>(R.id.textview_recipe)

        fun bindIngredient(ingredient: Ingredients) {
            with(ingredient) {
                titleTextView.text = ingredient.ingredient
            }
        }
    }

    class StepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView = itemView.findViewById<TextView>(R.id.textview_recipe)

        fun bindStep(step: Steps) {
            with(step) {
                titleTextView.text = shortDescription
            }
        }
    }
}