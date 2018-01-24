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

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.sudhirkhanger.bakingapp.adapter.RecipeAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityInstrumentedTest {

    @get:Rule
    val activityTestRule: ActivityTestRule<MainActivity> =
            ActivityTestRule(MainActivity::class.java)

    @Before
    fun init() {
        activityTestRule.activity.supportFragmentManager.beginTransaction()
    }

    @Test
    fun clickThirdItem() {
        onView(withId(R.id.recyclerview_recipe))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecipeAdapter.RecipeViewHolder>(2, click()))
    }

    @Test
    fun clickBrownies() {
        onView(withId(R.id.recyclerview_recipe))
                .perform(RecyclerViewActions.actionOnItem<RecipeAdapter.RecipeViewHolder>(
                        hasDescendant(withText("Brownies")), click()))
    }
}