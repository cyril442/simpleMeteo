package cyril.cieslak.mymynews

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val inputEntryDate = MutableLiveData<String>()
    val inputEndDate = MutableLiveData<String>()
    val datasForResultSearchActivity = MutableLiveData<MutableList<MutableList<String>>>()
}