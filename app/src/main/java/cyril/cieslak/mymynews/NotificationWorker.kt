package cyril.cieslak.mymynews

import android.content.Context
import android.util.Log
import androidx.work.Worker
import cyril.cieslak.mymynews.NotificationActivity
import cyril.cieslak.mymynews.Parsers.parseDatas
import kotlinx.android.synthetic.main.fragment_notification_enable.*



class NotificationWorker  : Worker() {


    override fun doWork(): Result {



        try {

            Log.i("testWorker", "Starting Sleep work")
            Thread.sleep(2000)
            var threadPassed = "ThreadPassed"
            Log.i("testWorker", "Sleep work finished")

        } catch (e: Exception) {
        return Result.FAILURE
    }

        try {

            Log.i("testWorker", "Starting NewsNotification")
            //   notificationActivity.newsNotificationOn()
          // retrievedNewsNotificationsOn()
            var expensive = doExpensiveCalculation()
            Log.i("testWorker", "expensive : $expensive")
            Log.i("testWorker", "NewsNotification Finished")

        } catch (e: Exception) {
            return Result.FAILURE


        }
        return Result.SUCCESS
    }



fun doExpensiveCalculation() : String {
    var doexpensive = "123"
    return doexpensive
}



}