package com.artushock.apps.spillme.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.artushock.apps.spillme.MainActivity
import com.artushock.apps.spillme.R
import org.joda.time.DateTime
import org.joda.time.Duration
import java.util.concurrent.TimeUnit

class WateringReminderWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private val TAG = this.javaClass.simpleName

    override fun doWork(): Result {
        val plantName = inputData.getString(WATERING_WORKER_PLANT_NAME) ?: "Plant"

        showWateringNotification(context, plantName)
        return Result.success()
    }

    private fun showWateringNotification(context: Context, plantName: String) {

        val channelId = "watering_$plantName"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Watering Reminder",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Необходимо полить $plantName"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_app_logo_foreground)
            .setContentTitle("Watering reminder")
            .setContentText("$plantName watering needed")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
        Log.i(TAG, "$plantName watering notification was sent")
    }

    companion object {
        const val WATERING_WORKER_PLANT_NAME = "watering_worker_plant_name"
    }
}

fun schedulePlantWateringReminder(
    context: Context,
    plantName: String,
    intervalDays: Int,
    targetHour: Int? = null,
    targetMinute: Int? = null,
) {
    val TAG = "schedulePlantWateringReminder()"
    val delay = calculateDelayToTargetTime(targetHour, targetMinute)
    val inputData = Data.Builder()
        .putString(WateringReminderWorker.WATERING_WORKER_PLANT_NAME, plantName)
        .build()

    val workRequest =
        PeriodicWorkRequestBuilder<WateringReminderWorker>(intervalDays.toLong(), TimeUnit.DAYS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "watering_${plantName}_${DateTime.now().millis}",
        ExistingPeriodicWorkPolicy.UPDATE,
        workRequest
    )

    Log.i(TAG, "$plantName watering was scheduled every $intervalDays days.")
}

fun calculateDelayToTargetTime(targetHour: Int?, targetMinute: Int?): Long {
    if (targetHour == null || targetMinute == null){
        return 0L
    }
    val now = DateTime.now()
    var targetTime = now.withTime(targetHour, targetMinute, 0, 0)
    if (targetTime.isBeforeNow) {
        targetTime = targetTime.plusDays(1)
    }
    val duration = Duration(now, targetTime)

    return duration.millis
}