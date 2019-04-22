package cyril.cieslak.mymynews

import android.content.ClipData
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import cyril.cieslak.mymynews.Fragments.FragmentTwo
import kotlinx.android.synthetic.main.fragment_fragment_one.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class ItemNewsAdapter(val datas : ArrayList<String>) : RecyclerView.Adapter<ItemNewsAdapter.ViewHolder>() {


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
        holder.icon.setImageResource(R.mipmap.ic_launcher_round)
        holder.section.text = data
        holder.subsection.text = data
        holder.date.text = data
        holder.title.text = data

    }

    override fun getItemCount(): Int {
        return datas.size

    }

}