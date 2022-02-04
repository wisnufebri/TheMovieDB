package co.android.wframadhan.themoviesuperapp.ui.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.android.wframadhan.domain.models.Trailer
import co.android.wframadhan.themoviesuperapp.R
import co.android.wframadhan.themoviesuperapp.YOUTUBE_THUMBNAIL_END_URL
import co.android.wframadhan.themoviesuperapp.YOUTUBE_THUMBNAIL_START_URL
import co.android.wframadhan.themoviesuperapp.databinding.TrailerItemBinding
import com.squareup.picasso.Picasso

class TrailerViewAdapter(private val onClickListener: OnClickListener) : ListAdapter<Trailer, TrailerViewAdapter.TrailerHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerHolder {
        Log.d("TAG", "onCreateViewHolder TRAILER")
        return TrailerHolder(TrailerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TrailerHolder, position: Int) {
        val trailer = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(trailer)
        }
        holder.bind(trailer)
    }

    class TrailerHolder(val binding: TrailerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(trailer: Trailer) {
            Log.d("TAG", "showTrailers TRAILER " + trailer.name)
            binding.trailerName.text = trailer.name

            val imgUrl = YOUTUBE_THUMBNAIL_START_URL + trailer.key + YOUTUBE_THUMBNAIL_END_URL
            imgUrl.let {
                val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                Picasso.get()
                    .load(imgUri)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(binding.trailerImage)
            }

        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Trailer>() {

        override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (trailer: Trailer) -> Unit) {
        fun onClick(trailer: Trailer) = clickListener(trailer)
    }

}