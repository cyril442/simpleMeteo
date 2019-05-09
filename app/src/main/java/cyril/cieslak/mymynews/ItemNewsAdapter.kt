package cyril.cieslak.mymynews

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext


class ItemNewsAdapter(val datas: List<MutableList<String>>) : RecyclerView.Adapter<ItemNewsAdapter.ViewHolder>() {


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

        val icon = itemView.findViewById<ImageView>(R.id.icon)
        val section = itemView.findViewById<TextView>(R.id.section)
        val subsection = itemView.findViewById<TextView>(R.id.subsection)
        val date = itemView.findViewById<TextView>(R.id.date)
        val title = itemView.findViewById<TextView>(R.id.title)



    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem = inflater.inflate(R.layout.item_nyt, parent, false)


        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = datas[position]

        //  holder.icon.setImageResource(R.mipmap.ic_launcher)
        Picasso.get()?.load(datas[position][4])?.placeholder(R.mipmap.ic_launcher_round)?.error(R.mipmap.ic_launcher)?.into(holder.icon)
        holder?.section?.text = datas[position][0]
        holder?.subsection?.text = datas[position][1]
        holder?.date?.text = datas[position][3]
        holder?.title?.text = datas[position][2]

        holder?.urlArticle = datas[position][5]

    }

    override fun getItemCount(): Int {
        return datas.size

    }


}
