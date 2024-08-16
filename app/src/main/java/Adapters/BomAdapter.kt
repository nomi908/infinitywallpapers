package Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import Model.Bom_Model
import android.content.Intent
import com.example.infinitywallpapers.R
import com.example.infinitywallpapers.WallpaperScreen
import kotlinx.coroutines.withContext

class BomAdapter( val requireContext: Context, val listbestofmonths: ArrayList<Bom_Model>) : RecyclerView.Adapter<BomAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val rcvimg = view.findViewById<ImageView>(R.id.item_bom_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(requireContext).inflate(R.layout.item_bom, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
    return listbestofmonths.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Glide.with(requireContext).load(listbestofmonths[position].link).into(holder.rcvimg)
        val imageview = listbestofmonths[position].link
        Glide.with(requireContext).load(imageview).into(holder.rcvimg)

        holder.itemView.setOnClickListener{
            val intent = Intent(requireContext, WallpaperScreen::class.java)
            intent.putExtra("imageurl", imageview)
            holder.itemView.context.startActivity(intent)
        }


    }
}