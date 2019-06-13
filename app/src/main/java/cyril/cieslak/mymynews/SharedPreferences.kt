package cyril.cieslak.mymynews

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "kotlincodes"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(KEY_NAME, text)

        editor!!.commit()
    }

    fun saveLong(KEY_NAME: String, text: Long) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putLong(KEY_NAME, text)

        editor!!.commit()
    }

    fun getValueString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, null)


    }

    fun getValueLong(KEY_NAME: String): Long? {

        return sharedPref.getLong(KEY_NAME, 0)


    }
}

