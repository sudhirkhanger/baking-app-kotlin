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

package com.sudhirkhanger.bakingapp.widget

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.text.TextUtils
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sudhirkhanger.bakingapp.DetailFragment.Companion.KEY_INGREDIENTS
import com.sudhirkhanger.bakingapp.model.Ingredients
import com.sudhirkhanger.bakingapp.model.Recipe


class BakingAppRemoteViewsService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return BakingAppRemoteViewsFactory(applicationContext, intent)
    }

    inner class BakingAppRemoteViewsFactory(private val context: Context,
                                            private val intent: Intent?) : RemoteViewsFactory {

        private val TAG = "BakingAppFctry"
        private var ingredientsList: MutableList<String> = mutableListOf()

        override fun onCreate() {
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun onDataSetChanged() {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val recipeString = preferences.getString(KEY_INGREDIENTS, "")
            if (!TextUtils.isEmpty(recipeString)) {
                val recipe: Recipe = Gson().fromJson(recipeString, Recipe::class.java)
                (0 until recipe.ingredients.size).forEach { ingredientsList.add(recipe.ingredients[it].ingredient) }
            }
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun getViewAt(position: Int): RemoteViews {
            val views = RemoteViews(context.packageName,
                    android.R.layout.simple_list_item_1)
            views.setTextViewText(android.R.id.text1, ingredientsList[position])
            return views
        }

        override fun getCount(): Int {
            return ingredientsList.size
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun onDestroy() {
        }

        private fun initData() {
            ingredientsList.add("Monday")
            ingredientsList.add("Tuesday")
            ingredientsList.add("Wednesday")
            ingredientsList.add("Thursday")
            ingredientsList.add("Friday")
            ingredientsList.add("Saturday")
            ingredientsList.add("Sunday")
        }
    }
}