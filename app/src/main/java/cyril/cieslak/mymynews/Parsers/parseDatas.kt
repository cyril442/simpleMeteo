package cyril.cieslak.mymynews.Parsers

import cyril.cieslak.mymynews.Fragments.FragmentTopStories
import org.json.JSONException
import org.json.JSONObject

class parseDatas() {


    val YEAR_MONTH_DAY = 10
    val YEAR_MONTH = 7
    val FIRST_FOUR = 4
    val LAST_TWO = 2
    val FIRST_ELEMENT_OF_THE_INDEX = 0
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
                val subsection = jo.getString("subsection")
                val updated_date = jo.getString("updated_date")
                val urlArticle = jo.getString("url")



                // See the function Below to know know i the susbestion if prepared for printing
                val subsectionReadyToPrint = valueOfTheSubsectionReadyToPrint(subsection)



                // See the function bellow transforming the string
                val dateToPrint = whatIsTheDateToPrint(updated_date)

                val jam = jo.getJSONArray("multimedia")

                var urlToPrint: String
                if (jam.length() == 0) {
                    urlToPrint = "https://i5.photobucket.com/albums/y152/courtney210/wave-bashful_zps5ab77563.jpg"
                } else {

                    var jom = jam[FIRST_ELEMENT_OF_THE_INDEX] as JSONObject
                    var url = jom.getString("url")

                    //***--- GET AN IMAGE EVEN WHEN MULTIMEDIA IS EMPTY  ---**//

                    when (url) {
                        "" -> urlToPrint = "$DUMB_PICTURE_WHEN_NO_PIC_TO_DOWNLOAD"
                        else -> urlToPrint = url
                    }

                }
                val data =
                    mutableListOf<String>(section, subsectionReadyToPrint, title, dateToPrint, urlToPrint, urlArticle)
                datas.add(data)


            }

            return datas

        } catch (e: JSONException) {
            e.printStackTrace()

        }
        return datas
    }



    fun whatIsTheDateToPrint(updated_date : String) : String {

        //***--- FORMATTING THE DATE ---***//
        var date10char = updated_date.take(YEAR_MONTH_DAY)
        var date7char = updated_date.take(YEAR_MONTH)
        var dateYear = date10char.take(FIRST_FOUR)
        var dateMonth = date7char.takeLast(LAST_TWO)
        var dateDay = date10char.takeLast(LAST_TWO)
        var dateToPrint = "$dateDay/$dateMonth/$dateYear"
        //***--------------------------------***//

        return dateToPrint
    }

    fun valueOfTheSubsectionReadyToPrint(subsection: String): String {

        //***--- PREPARATION OF THE SUBSECTION TO PRINT with a " > " before the texte to print---***//
        var subsectionReadyToPrint: String
        when (subsection) {
            "" -> subsectionReadyToPrint = subsection
            else -> subsectionReadyToPrint = " > $subsection"
        }

        return subsectionReadyToPrint
    }
}
