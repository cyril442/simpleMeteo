package cyril.cieslak.simplMeteoByGpsPoint.UtilsClass

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import cyril.cieslak.simplMeteoByGpsPoint.NotificationWorker
import java.util.concurrent.TimeUnit

class NotificationLauncherOrNot () {

    fun launchWorker () {



        // Set the Notification On or off
//        val work = OneTimeWorkRequestBuilder<NotificationWorker>()
//            .build()

        // Constraints
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        val work = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()




        //  Log.i ("TimeTest", " TWENTY : $TWENTY_FOUR_HOURS_IN_SECONDS, ACtual Time : $actualTimeStamp, lastSwitchOn : $lastSwitchedOnSwitchButton, donc au total =  $timePassedSinceLastSwitchOn")

//        when (timePassedSinceLastSwitchOn >= TWENTY_FOUR_HOURS_IN_MILLISECONDS && statusSwitchButton == NotificationActivity.ENABLE) {
//                         true ->  WorkManager.getInstance().enqueue(work)
//        }


        WorkManager.getInstance().enqueue(work)



//        WorkManager.getInstance().cancelAllWork()

//        when (statusSwitchButton && twentyHoursPassedOrNot()){
//            NotificationActivity.ENABLE && true->  WorkManager.getInstance().enqueue(work)
//        }



//        WorkManager.getInstance().getStatusById(work.id)
//            .observe(this, Observer { workStatus ->
//                Log.i ("workStatus", "workstatus = $workStatus")
//
//                if (workStatus != null && !workStatus.state.isFinished) {
//                    Log.i("workStatus", "Not yet finished ")
//                }
//            })

//        WorkManager.getInstance().getStatusById(work.id)
//            .observe(this, android.arch.lifecycle.Observer { workStatus ->
//                Log.i("workManager", "workstatus = $workStatus")
//
//                if (workStatus != null && !workStatus.state.isFinished) {
//                    Log.i("workManager", "Not yet finished ")
//                }
//            })
    }

    fun stopWorker () {

        WorkManager.getInstance().cancelAllWork()
    }
}