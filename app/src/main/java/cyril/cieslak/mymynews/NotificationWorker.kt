package cyril.cieslak.mymynews

import android.util.Log
import androidx.work.Worker
import cyril.cieslak.mymynews.NotificationActivity
import kotlinx.android.synthetic.main.fragment_notification_enable.*


class NotificationWorker : Worker() {



    override fun doWork(): Result {

        try {

            Log.i("testWorker", "Starting Sleep work")
            Thread.sleep(2000)
            Log.i("testWorker", "Sleep work finished")

        } catch (e: Exception) {
        return Result.FAILURE
    }

        try {

            Log.i("testWorker", "Starting NewsNotification")
            //   notificationActivity.newsNotificationOn()
          // retrievedNewsNotificationsOn()

            Log.i("testWorker", "NewsNotification Finished")

        } catch (e: Exception) {
            return Result.FAILURE


        }
        return Result.SUCCESS
    }



}