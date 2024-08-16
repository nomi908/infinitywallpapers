package Adapters

import Model.Categories_Model
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infinitywallpapers.CategoriesDataActivity
import com.example.infinitywallpapers.R

class Cat_Adapter(val requireContext: Context, val categroiesdata: ArrayList<Categories_Model>) : RecyclerView.Adapter<Cat_Adapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardimage = view.findViewById<ImageView>(R.id.item_cat_img)
        val cardtext = view.findViewById<TextView>(R.id.item_cat_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(requireContext).inflate(R.layout.item_cat, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
       return categroiesdata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val allcategory = categroiesdata[position]
        Glide.with(requireContext).load(allcategory.link).into(holder.cardimage)
        holder.cardtext.text = allcategory.name

        holder.itemView.setOnClickListener{
            val intent = Intent(requireContext, CategoriesDataActivity::class.java)
            intent.putExtra("allcatname", allcategory.name)
            intent.putExtra("allcatlink", allcategory.link)
            intent.putExtra("id",allcategory.id)

            holder.itemView.context.startActivity(intent)

        }

    }
}