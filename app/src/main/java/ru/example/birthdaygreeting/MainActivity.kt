package ru.example.birthdaygreeting

import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.VideoView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val video = VideoView(this)
        val label = TextView(this).apply {
            val prefs = getSharedPreferences(Scheduler.PREFS, MODE_PRIVATE)
            text = prefs.getString(Scheduler.KEY_TEXT, "С днём рождения!")
            textSize = 44f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            setShadowLayer(6f, 2f, 2f, Color.BLACK)
        }
        val frame = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
        frame.addView(video, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        frame.addView(label, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER))
        setContentView(frame)
        hideSystemUi()

        video.setVideoURI(Uri.parse("android.resource://$packageName/${R.raw.greeting}"))
        video.setOnCompletionListener { finish() }
        video.setOnErrorListener { _, _, _ -> finish(); true }
        video.start()
    }

    private fun hideSystemUi() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
}
