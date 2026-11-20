package ru.example.birthdaygreeting

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout

class SettingsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences(Scheduler.PREFS, MODE_PRIVATE)

        val dates = EditText(this).apply { hint = "Даты: MM-dd через запятую, напр. 05-10,12-25" }
        dates.setText(prefs.getString(Scheduler.KEY_DATES, ""))
        val time = EditText(this).apply { hint = "Время HH:mm" }
        time.setText(prefs.getString(Scheduler.KEY_TIME, "12:00"))
        val text = EditText(this).apply { hint = "Текст поздравления" }
        text.setText(prefs.getString(Scheduler.KEY_TEXT, "С днём рождения!"))
        val save = Button(this).apply {
            this@apply.text = "Сохранить"
            setOnClickListener {
                prefs.edit()
                    .putString(Scheduler.KEY_DATES, dates.text.toString().trim())
                    .putString(Scheduler.KEY_TIME, time.text.toString().trim())
                    .putString(Scheduler.KEY_TEXT, text.text.toString().trim())
                    .apply()
                Scheduler.schedule(applicationContext)
                finish()
            }
        }
        setContentView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 48, 48, 48)
            addView(dates); addView(time); addView(text); addView(save)
        })
    }
}
