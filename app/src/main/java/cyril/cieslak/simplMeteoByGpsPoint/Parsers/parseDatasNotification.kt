package cyril.cieslak.simplMeteoByGpsPoint.Parsers

import org.json.JSONException
import org.json.JSONObject

class parseDatasNotification() {


    var datas = "testDatas"


    fun parseDatasFromApi(jsonDataPreview: String): String {

        var json = jsonDataPreview

        try {

            var jo: JSONObject
            var job: JSONObject

            jo = JSONObject(json)

            job = jo.getJSONObject("response")
            val ja = job.getJSONObject("meta")
            val hits = ja.getString("hits")

            datas = hits
            return datas

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return datas
    }
}
