package app.junsu.onui_android.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import app.junsu.onui.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Service for handling Firebase Cloud Messaging (FCM) messages.
 * This class extends [FirebaseMessagingService] and provides methods
 * for processing incoming messages, handling new registration tokens, and
 * scheduling background work.
 */
class MessagingService : FirebaseMessagingService() {

    /**
     * Called when a new FCM message is received. It processes the message
     * and either displays a notification or sends a message notification,
     * depending on the content of the message.
     *
     * @param message The received FCM message.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.i(TAG, message.toString())

        if (message.notification != null) {
            sendNotification(message.notification?.title, message.notification?.body!!)
            scheduleJob()
        } else if (message.data.isNotEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                sendMessageNotification(message.data)
            } else {
                sendNotification(message.notification?.title, message.notification?.body!!)
            }
            scheduleJob()
        }
    }

    /**
     * Called when a new FCM token is generated. It logs the new token.
     *
     * @param token The new FCM token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }
            val deviceToken = task.result
            Log.d(TAG, "Token: $deviceToken")
        })
    }

    /**
     * Schedules a one-time background work using [MyWorker].
     */
    private fun scheduleJob() {
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .build()
        WorkManager.getInstance().beginWith(work).enqueue()
    }

    /**
     * Displays a notification with the provided title and body.
     *
     * @param title The title of the notification.
     * @param body The body of the notification.
     */
    private fun sendNotification(title: String?, body: String) {
        Log.d("title", body)
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, -1, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = getString(R.string.firebase_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setChannelId(channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_SOUND)

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
        channel.apply {
            setShowBadge(false)
        }
        notificationManager.notify(uniId, notificationBuilder.build())
    }

    /**
     * Displays a message notification with the provided data.
     *
     * @param message The data of the message notification.
     */
    private fun sendMessageNotification(message: Map<String, String>) {
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()
        val title = message["title"]!!
        val body = message["body"]!!
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            uniId,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = getString(R.string.firebase_notification_channel_id)

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setChannelId(channelId)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_SOUND)


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
        channel.apply {
            setShowBadge(false)
        }
        notificationManager.notify(uniId, notificationBuilder.build())

    }
}

/**
 * A background worker class for handling one-time work requests.
 * This class extends [Worker] and performs a simple task.
 */
internal class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    /**
     * The method where the background work is performed.
     * In this case, it simply returns a success result.
     *
     * @return The result of the background work.
     */
    override fun doWork(): Result {
        return Result.success()
    }
}
