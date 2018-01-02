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

package com.sudhirkhanger.bakingapp.model

class Recipe(val id: Int,
             val name: String,
             val ingredients: MutableList<Ingredients>,
             val steps: MutableList<Steps>,
             val servings: Int,
             val image: String)

class Ingredients(val quantity: Int,
                  val measure: String,
                  val ingredient: String)

class Steps(val id: Int,
            val shortDescription: String,
            val description: String,
            val videoUrl: String,
            val thumbnailUrl: String)