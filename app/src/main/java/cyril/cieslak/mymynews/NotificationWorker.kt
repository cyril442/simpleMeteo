package cyril.cieslak.mymynews

import android.util.Log
import androidx.work.Worker
import cyril.cieslak.mymynews.NotificationActivity



class NotificationWorker : Worker() {

    val notificationActivity = NotificationActivity()

    override fun doWork(): Result {

        Log.i("testWorker", "Starting hard work")
        Thread.sleep(2000)
        notificationActivity.newsNotificationOn()
        Log.i("testWorker", "work finished")

        return Result.SUCCESS
    }
}