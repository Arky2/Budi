package ru.example.birthdaygreeting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

object Scheduler {
    const val PREFS = "greeting_prefs"
    const val KEY_DATES = "dates" // формат "MM-dd", несколько через запятую: "05-10,12-25"
    const val KEY_TIME = "time"   // "HH:mm"
    const val KEY_TEXT = "text"   // текст поздравления на экране

    fun schedule(context: Context) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val dates = prefs.getString(KEY_DATES, "")!!.split(",")
            .map { it.trim() }
            .filter { Regex("""\d{2}-\d{2}""").matches(it) }
        val (h, m) = prefs.getString(KEY_TIME, "12:00")!!.split(":").map { it.toInt() }

        var best: Calendar? = null
        for (d in dates) {
            val (mm, dd) = d.split("-").map { it.toInt() }
            val c = Calendar.getInstance().apply {
                set(Calendar.MONTH, mm - 1)
                set(Calendar.DAY_OF_MONTH, dd)
                set(Calendar.HOUR_OF_DAY, h)
                set(Calendar.MINUTE, m)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            if (c.timeInMillis <= System.currentTimeMillis()) c.add(Calendar.YEAR, 1)
            if (best == null || c.before(best)) best = c
        }
        best ?: return

        val pi = PendingIntent.getBroadcast(
            context, 0, Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val am = context.getSystemService(AlarmManager::class.java)
        am.cancel(pi)
        if (Build.VERSION.SDK_INT >= 31 && !am.canScheduleExactAlarms()) {
            am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, best.timeInMillis, pi)
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, best.timeInMillis, pi)
        }
    }
}
