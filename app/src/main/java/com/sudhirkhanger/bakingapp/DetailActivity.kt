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
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.FrameLayout
import com.sudhirkhanger.bakingapp.DetailActivity.Companion.KEY_STEP_OBJECT
import com.sudhirkhanger.bakingapp.model.Steps

class DetailActivity : AppCompatActivity(), DetailFragment.OnStepSelectedInterface {

    companion object {
        private const val TAG = "DetailActivity"
        private const val FRAGMENT_STEP = "step_fragment"
        const val KEY_STEP_OBJECT = "step_object_key"
    }

    private var isTwoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (findViewById<FrameLayout>(R.id.step_detail_container) != null) {
            Log.e(TAG, "Tablet layout displayed")
            isTwoPane = true

            if (savedInstanceState == null) {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.step_detail_container,
                                StepFragment.newInstance(null),
                                "step_fragment")
                        .commit()
            }
        } else {
            isTwoPane = false
        }
    }

    override fun onStepSelected(step: Steps) {
        if (isTwoPane) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.step_detail_container, StepFragment.newInstance(step), FRAGMENT_STEP)
                    .commit()
        } else {
            val stepActivityIntent = Intent(this, StepActivity::class.java)
            stepActivityIntent.putExtra(KEY_STEP_OBJECT, step)
            startActivity(stepActivityIntent)
        }
    }
}
