package ru.example.birthdaygreeting

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.PendingIntent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val launch = Intent(context, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        try {
            context.startActivity(launch)
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= 26) {
                val nm = context.getSystemService(NotificationManager::class.java)
                nm.createNotificationChannel(
                    android.app.NotificationChannel("greet", "Поздравления",
                        NotificationManager.IMPORTANCE_HIGH)
                )
            }
            val npi = PendingIntent.getActivity(
                context, 0, launch,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            NotificationCompat.Builder(context, "greet")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Поздравление!")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setFullScreenIntent(npi, true)
                .setAutoCancel(true)
                .build()
                .let { NotificationManagerCompat.from(context).notify(1, it) }
        }
        Scheduler.schedule(context) // ставим будильник на следующий год
    }
}
