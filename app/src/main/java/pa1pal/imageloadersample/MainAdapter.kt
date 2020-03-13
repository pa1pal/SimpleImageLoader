package pa1pal.imageloadersample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.view.*
import pa1pal.simpleimageloader.models.ImageData
import pa1pal.simpleimageloader.repository.ImageLoader

class MainAdapter(private val imageLoader: ImageLoader): RecyclerView.Adapter<MainAdapter.ViewHolder>(){
    var imagesList: List<ImageData> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageData = imagesList[position]
        imageLoader.loadImage(holder.imageIcon, imageData.urls.full, 500, 500, 50)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun submitList(imagesList: List<ImageData>){
        this.imagesList = imagesList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageIcon = itemView.image!!
    }
}