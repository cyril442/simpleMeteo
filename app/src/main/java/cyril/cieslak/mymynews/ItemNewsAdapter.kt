package cyril.cieslak.mymynews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class ItemNewsAdapter(val datas: List<MutableList<String>>) : RecyclerView.Adapter<ItemNewsAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        Picasso.get().load(datas[position][4]).into(holder.icon)
        holder.section.text = datas[position][0]
        holder.subsection.text = datas[position][1]
        holder.date.text = datas[position][3]
        holder.title.text = datas[position][2]


    }

    override fun getItemCount(): Int {
        return datas.size

    }

}