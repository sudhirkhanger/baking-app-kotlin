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

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.sudhirkhanger.bakingapp.R


class BakingAppWidgetProvider : AppWidgetProvider() {

    companion object {
        public const val KEY_INTENT = "BakingAppWidgetProvider"
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        appWidgetIds?.forEach {
            val intent = Intent(context, BakingAppRemoteViewsService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, it)
            intent.putExtra("random", Math.random() * 1000)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val remoteViews = RemoteViews(context?.packageName, R.layout.widget_baking_app)
            remoteViews.setRemoteAdapter(R.id.widget_list_view, intent)
            appWidgetManager?.updateAppWidget(it, remoteViews)
            appWidgetManager?.notifyAppWidgetViewDataChanged(it, R.id.widget_list_view)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val appWidgetManager = AppWidgetManager.getInstance(context?.applicationContext)
        val bakingWidget = ComponentName(context?.applicationContext, BakingAppWidgetProvider::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(bakingWidget)
        onUpdate(context, appWidgetManager, appWidgetIds)
    }
}