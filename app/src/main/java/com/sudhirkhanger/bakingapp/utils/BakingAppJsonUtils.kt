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

package com.sudhirkhanger.bakingapp.utils

import com.sudhirkhanger.bakingapp.Ingredients
import com.sudhirkhanger.bakingapp.Recipe
import com.sudhirkhanger.bakingapp.Steps
import org.json.JSONArray


class BakingAppJsonUtils {

    companion object {
        fun extractRecipeFromJson(recipeJson: String?): MutableList<Recipe> {
            val recipes = mutableListOf<Recipe>()

            val recipeArray = JSONArray(recipeJson)

            for (recipeCounter in 0..recipeArray.length() - 1) {
                val recipeObject = recipeArray.getJSONObject(recipeCounter)
                val recipeId = recipeObject.optInt("id")
                val name = recipeObject.optString("name")

                val ingredientArray = recipeObject.optJSONArray("ingredients")
                val ingredients = mutableListOf<Ingredients>()

                for (ingredientCounter in 0..ingredientArray.length() - 1) {
                    val ingredientObject = ingredientArray.getJSONObject(ingredientCounter)
                    val quantity = ingredientObject.optInt("quantity")
                    val measure = ingredientObject.optString("measure")
                    val ingredient = ingredientObject.optString("ingredient")
                    val ingreadientItem = Ingredients(quantity, measure, ingredient)
                    ingredients.add(ingreadientItem)
                }

                val stepArray = recipeObject.optJSONArray("steps")
                val steps = mutableListOf<Steps>()

                for (stepCounter in 0..stepArray.length() - 1) {
                    val stepObject = stepArray.getJSONObject(stepCounter)
                    val id = stepObject.optInt("id")
                    val shortDescription = stepObject.optString("shortDescription")
                    val description = stepObject.optString("description")
                    val videoUrl = stepObject.optString("videoURL")
                    val thumbnailUrl = stepObject.optString("thumbnailURL")
                    val step = Steps(id, shortDescription, description, videoUrl, thumbnailUrl)
                    steps.add(step)
                }

                val servings = recipeObject.optInt("servings")
                val image = recipeObject.optString("image")

                val recipe = Recipe(recipeId, name, ingredients, steps, servings, image)

                recipes.add(recipe)
            }

            return recipes
        }
    }
}