package cyril.cieslak.simplMeteoByGpsPoint

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class ItemWeatherAdapter(val datas: List<MutableList<String>>) : RecyclerView.Adapter<ItemWeatherAdapter.ViewHolder>() {


    class ViewHolder(itemView: View, var urlArticle : String = "") : RecyclerView.ViewHolder(itemView) {

        companion object{

            val URL_ARTICLE_LINK = "URL_ARTICLE_LINK"
        }

        init {
            itemView.setOnClickListener {
                println("Ca gaze")

                val intent = Intent(itemView.context, WebViewActivity::class.java)

                intent.putExtra(URL_ARTICLE_LINK, urlArticle)
                itemView.context.startActivity(intent)

            }
        }

        val icon_weather = itemView.findViewById<ImageView>(R.id.icon_weather)
        val cityName = itemView.findViewById<TextView>(R.id.cityName)
        val country = itemView.findViewById<TextView>(R.id.country)
        val temperature = itemView.findViewById<TextView>(R.id.temperature)
        val speed = itemView.findViewById<TextView>(R.id.speed)



    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem = inflater.inflate(R.layout.itemweather_now, parent, false)


        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val iconCode = datas[position][4]
        val pictureToDownload =  "http://openweathermap.org/img/wn/$iconCode@2x.png"

        val data = datas[position]



        //  holder.icon_weather.setImageResource(R.mipmap.ic_launcher)
        Picasso.get()
            ?.load(pictureToDownload)
            ?.placeholder(R.drawable.logo_nyt_400x400)
            ?.error(R.mipmap.ic_launcher)
            ?.into(holder.icon_weather)

        holder?.cityName?.text = datas[position][0]
        holder?.country?.text = datas[position][1]
        holder?.temperature?.text = datas[position][2]
        holder?.speed?.text = datas[position][3]

       // holder?.urlArticle = datas[position][5]

    }

    override fun getItemCount(): Int {
        return datas.size

    }


}
