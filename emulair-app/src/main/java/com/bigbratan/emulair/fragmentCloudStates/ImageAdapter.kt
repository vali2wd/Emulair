package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.R
import com.bumptech.glide.Glide



fun composeString(inputString: String): String {
    val gameName = inputString.substring(0, 15)
    val startIndex = inputString.indexOf('_') + 1
    val endIndex = inputString.indexOf('.')
    val substring = inputString.substring(startIndex, endIndex)



    return gameName + ".../" + substring
}

class ImageViewHolder(parent: View, private val selectListener: SelectListener?) : RecyclerView.ViewHolder(parent) {
    private var textView: TextView? = null
    var imageView: ImageView
    private var originalTitle: String? = null
    private var progressBar: ProgressBar? = null

    init {
        textView = itemView.findViewById(R.id.text)
        imageView = itemView.findViewById(R.id.image)
        imageView.setOnClickListener{
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                selectListener?.onItemClicked(position)
            }
        }
    }

    fun bind(photo: Bitmap?, text: String?)  {
        if (photo != null) {

            originalTitle = text
            Glide.with(itemView.context).load(photo).into(imageView)
            val textToShow = composeString(text.toString())
            textView?.text = textToShow
        } else {
            Glide.with(itemView.context).load(R.drawable.ic_launcher_background).into(imageView)
            textView?.text = composeString(text.toString())
        }
    }
}



class ImageAdapter(
    private val selectListener: SelectListener?
): RecyclerView.Adapter<ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_image, parent, false),
            selectListener
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Log.d("myapp", "Binding item at position $position" + "/"+differ.currentList.size)
        val cloudState = differ.currentList[position]
        holder.bind(cloudState.image, cloudState.title)
    }
    private val differCallBack = object : DiffUtil.ItemCallback<CloudState>() {
        override fun areItemsTheSame(oldItem: CloudState, newItem: CloudState): Boolean {
            Log.d("myapp", "areItemsTheSame")
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: CloudState, newItem: CloudState): Boolean {
            Log.d("myapp", "areContentstheSame")
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
}

//class ImageAdapter(
//    /*private val context: Context,*/
//    private val cloudList: List<CloudState>?,
//    private val selectListener: SelectListener?
//
//) : RecyclerView.Adapter<ImageViewHolder>() {
//
//    private var isLoading = false
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        return ImageViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.layout_image, parent, false),
//            selectListener
//        )
//    }
//
//
//    override fun getItemCount(): Int {
//        return cloudList?.size ?: 0
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//         holder.bind(cloudList?.get(position)?.image , cloudList?.get(position)?.title)
//    }
//}
