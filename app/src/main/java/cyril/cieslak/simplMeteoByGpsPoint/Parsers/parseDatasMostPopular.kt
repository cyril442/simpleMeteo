package cyril.cieslak.simplMeteoByGpsPoint.Parsers

import org.json.JSONException
import org.json.JSONObject

class parseDatasMostPopular() {


    val YEAR_MONTH_DAY = 10
    val YEAR_MONTH = 7
    val FIRST_FOUR = 4
    val LAST_TWO = 2
    val FIRST_ELEMENT_OF_THE_INDEX = 0
    val RANK = " > Number "
    val DUMB_PICTURE_WHEN_NO_PIC_TO_DOWNLOAD =
        "https://i5.photobucket.com/albums/y152/courtney210/wave-bashful_zps5ab77563.jpg"


    var datas = mutableListOf(
        mutableListOf<String>(
            "un",
            "deux",
            "trois",
            "quatre",
            "cinq",
            "six",
            "sept",
            "huit",
            "neuf",
            "dix",
            "onze",
            "douze",
            "treize",
            "quatorze",
            "quinze"
        )
    )


    fun parseDatasFromApi(jsonDataPreview: String): MutableList<MutableList<String>> {


        var json = jsonDataPreview


        try {

            var jo: JSONObject
            datas.clear()

            jo = JSONObject(json)

            val ja = jo.getJSONArray("results")


            for (i in 0 until ja.length()) {
                jo = ja.getJSONObject(i)


                val title = jo.getString("title")
                val section = jo.getString("section")
                val views = jo.getInt("views")
                val published_date = jo.getString("published_date")
                val urlArticle = jo.getString("url")

                //***--- PREPARATION OF THE SUBSECTION with The Ranking of the Article---***//
                var viewsReadyToPrint = whatIsTheRankingOfTheArticle(views)


                // See the function from Class parseDatas.kt()
                val dateToPrint = parseDatas().whatIsTheDateToPrint(published_date)



                val jam = jo.getJSONArray("media")
                var jom = jam[FIRST_ELEMENT_OF_THE_INDEX] as JSONObject
                var jim = jom.getJSONArray("media-metadata")
                val jem = jim[FIRST_ELEMENT_OF_THE_INDEX] as JSONObject


                var url = jem.getString("url")

                //***--- GET AN IMAGE EVEN WHEN MULTIMEDIA IS EMPTY  ---**//
                var urlToPrint: String
                when (url) {
                    "" -> urlToPrint = DUMB_PICTURE_WHEN_NO_PIC_TO_DOWNLOAD
                    else -> urlToPrint = url
                }

                val data = mutableListOf<String>(section, viewsReadyToPrint, title, dateToPrint, urlToPrint, urlArticle)
                datas.add(data)
            }
            return datas

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return datas
    }

    fun whatIsTheRankingOfTheArticle (views : Int) : String{

        //***--- PREPARATION OF THE SUBSECTION with the Ranking of te article--***//
        var viewsReadyToPrint: String
        when (views) {
            0 -> viewsReadyToPrint = views.toString()
            else -> viewsReadyToPrint = "$RANK$views"
        }
        //***--------------------------------***//
        return viewsReadyToPrint
    }

}