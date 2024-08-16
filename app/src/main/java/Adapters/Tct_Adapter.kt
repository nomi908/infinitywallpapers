package Adapters

import Model.tct_model
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.infinitywallpapers.R



class Tct_Adapter(val requireContext: Context, val thecolortone: ArrayList<tct_model>) : RecyclerView.Adapter<Tct_Adapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val colorstone: CardView = view.findViewById<CardView>(R.id.item_tct)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(requireContext).inflate(R.layout.item_thecolortone, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return thecolortone.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val allcolors = thecolortone[position].color
        val hashcolor = android.graphics.Color.parseColor(allcolors)

        holder.colorstone.setCardBackgroundColor(hashcolor)

        holder.itemView.setOnClickListener{
//            val intent = Intent(requireContext,  )
        }
    }
}