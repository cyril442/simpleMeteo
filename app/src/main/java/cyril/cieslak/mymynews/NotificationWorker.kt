package cyril.cieslak.mymynews

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import android.util.Log
import androidx.work.Worker
import cyril.cieslak.mymynews.NotificationActivity.Companion.ENABLE
import cyril.cieslak.mymynews.NotificationActivity.Companion.KEY_NAME_CHECKBOX_TERMS_FOR_NOTIFICATION_REQUEST
import cyril.cieslak.mymynews.NotificationActivity.Companion.KEY_NAME_QUERYTEXT_FOR_NOTIFICATION_REQUEST
import cyril.cieslak.mymynews.Parsers.parseDatasNotification
import cyril.cieslak.mymynews.UtilsClass.JSONDownloaderNotification
import java.text.SimpleDateFormat
import java.util.*


class NotificationWorker() : Worker() {

    val TWENTY_FOUR_HOURS_IN_MILLISECONDS = 86400000 // 900000 pour 15 minutes
    val ZERO_LONG = 0


    override fun doWork(): Result {

//        try {
//
//
//            Log.i("testWorker", "Starting Sleep work")
//            Thread.sleep(2000)
//            var threadPassed = "ThreadPassed"
//            Log.i("testWorker", "Sleep work finished")
//
//        } catch (e: Exception) {
//            return Result.FAILURE
//        }
//
//        try {
//
//            Log.i("testWorker", "Starting NewsNotification")
//            //   notificationActivity.newsNotificationOn()
//            // retrievedNewsNotificationsOn()
//            val expensive = doExpensiveCalculation()
//            Log.i("testWorker", "expensive : $expensive")
//            Log.i("testWorker", "NewsNotification Finished")
//
//        } catch (e: Exception) {
//            return Result.FAILURE
//        }

        try {


            // ---- Dates for the Notification Search ---- //
            val sdf = SimpleDateFormat("yyyyMMdd")
            var todayInMilliSeconds = Date().time
            var yesterdayInMilliSeconds = todayInMilliSeconds - TWENTY_FOUR_HOURS_IN_MILLISECONDS

            val today = sdf.format(todayInMilliSeconds)
            val yesterday = sdf.format(yesterdayInMilliSeconds)

            Log.i("dateToday", " today is : $today and yesterday was $yesterday")


            // ---- terms For Research (CheckBox)retrieved from SharedPreferences ---- //
            val stringTermsForResearchApi =
                SharedPreference(this.applicationContext).getValueString(KEY_NAME_CHECKBOX_TERMS_FOR_NOTIFICATION_REQUEST)
            Log.i("testWorker", " checkbox_terms_For_Notification_request : $stringTermsForResearchApi")

            // ---- terms For Research (Text Input) retrieved from SharedPreferences ---- //
            val queryText =
                SharedPreference(this.applicationContext).getValueString(KEY_NAME_QUERYTEXT_FOR_NOTIFICATION_REQUEST)
            Log.i("testWorker", " checkbox_terms_For_Notification_request : $queryText")


            // ---- Global String to Request HTTP ---- //
            val jsonResultSearchActivity =
                "https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=${yesterday}&end_date=$today&q=$queryText&fq=news_desk($stringTermsForResearchApi)&sort=relevance&api-key=92Nbf4KeZSKhJXGm5QA3eTgNJjFW61gW"
            Log.i("testWorker", "$jsonResultSearchActivity")


            /// ----- Downloading the datas from the server ---- ////
            val jsonDataPreview = JSONDownloaderNotification(applicationContext, jsonResultSearchActivity).download()
            Log.i("testWorker", "voici les données récupérrées en JSONDATAPREVIEW : $jsonDataPreview")


            /// ---- Parse The datas ---- ///
            var datas = parseDatasNotification().parseDatasFromApi(jsonDataPreview)
            Log.i("testWorker", "voici les données récupérrées AFTER PARSING : $datas")



//            // Launch or Not the Notification:
            // ---- terms For Research (CheckBox)retrieved from SharedPreferences ---- //
            val statusSwitchButton: String? =
                SharedPreference(this.applicationContext).getValueString(NotificationActivity.SWITCH_BUTTON_STATUS)
            Log.i("testWorker", " STATUS SWITCH BUTTON : $statusSwitchButton")
//
//            var lastSwitchedOnSwitchButton = SharedPreference(this.applicationContext).getValueLong(NotificationActivity.LAST_SWITCH_ACTION_SAVED) as Long
//            val actualTimeStamp = Date().time
//
//
//            when(lastSwitchedOnSwitchButton.toInt() == ZERO_LONG)  {
//                true -> lastSwitchedOnSwitchButton = actualTimeStamp
//            }
//            val timePassedSinceLastSwitchOn = actualTimeStamp - lastSwitchedOnSwitchButton
//
//            val isEnoughTimePassed : Boolean = timePassedSinceLastSwitchOn > TWENTY_FOUR_HOURS_IN_MILLISECONDS
//
            if (statusSwitchButton == ENABLE) {

                // Launch Notification
                sendNotification(datas)
            }


        } catch (e: Exception) {
            return Result.FAILURE
        }

        return Result.SUCCESS
    }


    fun doExpensiveCalculation(): String {
        var doexpensive = "123"
        return doexpensive
    }


    fun sendNotification(datas: String) {
        val notificationManager =
            getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(getApplicationContext(), "default")

        notification.setContentTitle("New York Times")
        notification.setContentText("You have $datas new articles to read ")
        notification.setSmallIcon(R.mipmap.ic_launcher_round)
        notification.setColor(Color.CYAN)

        notificationManager.notify(1, notification.build())
    }


}