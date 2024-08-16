package Adapters

import Model.Bom_Model
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infinitywallpapers.CategoriesDataActivity
import com.example.infinitywallpapers.R
import com.example.infinitywallpapers.WallpaperScreen

class CatDataAdapter(
    val categoriesDataActivity: CategoriesDataActivity,
    val allcatcoldata: ArrayList<Bom_Model>
) : RecyclerView.Adapter<CatDataAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageview = view.findViewById<ImageView>(R.id.item_cat_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(categoriesDataActivity)
            .inflate(R.layout.item_cat_holder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allcatcoldata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val catdataimg = allcatcoldata[position]

        Log.d("img", "catdataimg: " + catdataimg)

        Glide.with(categoriesDataActivity).load(catdataimg.link).into(holder.imageview)

        holder.itemView.setOnClickListener{
            val intent = Intent(categoriesDataActivity, WallpaperScreen::class.java)
            intent.putExtra("imageurl", catdataimg.link)
            holder.itemView.context.startActivity(intent)

         }




    }
}