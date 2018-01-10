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
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.sudhirkhanger.bakingapp.adapter.DetailRecipeAdapter
import com.sudhirkhanger.bakingapp.model.Recipe
import com.sudhirkhanger.bakingapp.model.Steps

class DetailFragment : Fragment() {

    lateinit var detailRecyclerView: RecyclerView
    lateinit var detailRecipeAdapter: DetailRecipeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val recipe = activity?.intent?.extras?.getParcelable(MainActivityFragment.KEY_RECIPE) as Recipe

        detailRecyclerView = view.findViewById(R.id.recyclerview_detail)
        detailRecyclerView.layoutManager = LinearLayoutManager(
                activity, LinearLayout.VERTICAL, false)

        detailRecipeAdapter = DetailRecipeAdapter(convertRecipeToList(recipe),
                object : DetailRecipeAdapter.OnItemClickListener {
                    override fun invoke(step: Steps) {
                        Log.e("DetailFragment", "step ${step.shortDescription}")
                    }
                })
        detailRecyclerView.adapter = detailRecipeAdapter
        return view
    }

    private fun convertRecipeToList(recipe: Recipe): MutableList<Any> {
        val list = mutableListOf<Any>()

        (0 until recipe.ingredients.size).forEach { list.add(recipe.ingredients[it]) }
        (0 until recipe.steps.size).forEach { list.add(recipe.steps[it]) }

        return list
    }
}