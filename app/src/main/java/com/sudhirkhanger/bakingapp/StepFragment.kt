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

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sudhirkhanger.bakingapp.DetailActivity.Companion.KEY_STEP_OBJECT
import com.sudhirkhanger.bakingapp.model.Steps

class StepFragment : Fragment() {

    companion object {
        private const val TAG = "StepFragment"
        private const val PLAYBACK_POSITION = "playbackPosition"
        private const val STEP_ARGS = "step_args"

        fun newInstance(step: Steps?): StepFragment {
            val stepFragment = StepFragment()
            val args = Bundle()
            Log.e(TAG, "newInstance ${step?.shortDescription}")
            args.putParcelable(STEP_ARGS, step)
            stepFragment.arguments = args
            return stepFragment
        }
    }

    private var step: Steps? = null
    private lateinit var player: SimpleExoPlayer
    private lateinit var playerView: SimpleExoPlayerView
    private lateinit var uri: Uri
    private var playWhenReady: Boolean = true
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_step, container, false)
        val args = arguments
        step = args?.getParcelable(STEP_ARGS)
        Log.e(TAG, step?.shortDescription ?: "onCreateView null")
        playerView = view.findViewById(R.id.video_view)
        val desc: TextView = view.findViewById(R.id.desc) as TextView
        desc.text = step?.description
        return view
    }

    private fun initializePlayer() {
        if (!this::player.isInitialized) {
            player = ExoPlayerFactory.newSimpleInstance(
                    DefaultRenderersFactory(activity),
                    DefaultTrackSelector(),
                    DefaultLoadControl())

            playerView.player = player
            player.playWhenReady = playWhenReady
            Log.e(TAG, "initializePlayer() currentWindow ${currentWindow} playbackPosition ${playbackPosition}")
            player.seekTo(currentWindow, playbackPosition)
        }

        if (!TextUtils.isEmpty(step?.videoUrl)) {
            uri = Uri.parse(step?.videoUrl)
            val mediaSource: MediaSource = buildMediaSource(uri)
            player.prepare(mediaSource)
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource.Factory(
                DefaultHttpDataSourceFactory("baking-app")).createMediaSource(uri)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            Log.e(TAG, "onStart()")
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
//        hideSystemUI()
        if (Util.SDK_INT <= 23) {
            Log.e(TAG, "onResume")
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            Log.e(TAG, "onPause")
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            Log.e(TAG, "onStop")
            releasePlayer()
        }
    }

    private fun hideSystemUI() {
        playerView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun releasePlayer() {
        if (this::player.isInitialized) {
            playbackPosition = player.currentPosition
            currentWindow = player.currentWindowIndex
            playWhenReady = player.playWhenReady
            Log.e(TAG, "releasePlayer() playbackPosition ${playbackPosition} currentWindow ${currentWindow}" +
                    " playWhenReady ${playWhenReady}")
            player.release()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.e(TAG, "onSaveInstanceState() playbackPosition $playbackPosition")
        outState.putLong(PLAYBACK_POSITION, player.currentPosition)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION)
            Log.e(TAG, "onViewStateRestored() playbackPosition $playbackPosition")
        }
    }
}
