package com.example.nasa_nws_dk.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nasa_nws_dk.R
import com.example.nasa_nws_dk.databinding.ListAsteroidsBinding
import com.example.nasa_nws_dk.models.Asteroid

class AsteroidsAdapter(val clickListener: ClickListener) :
    RecyclerView.Adapter<AsteroidsAdapter.AsteroidViewHolder>() {

    private var asteroids: List<Asteroid> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AsteroidViewHolder {

        val binding: ListAsteroidsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_asteroids,
            parent,
            false
        )

        return AsteroidViewHolder(binding)
        //return AsteroidViewHolder(ListAsteroidsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    interface ClickListener {
        fun onAsteroidClick(asteroid : Asteroid)
    }

    override fun getItemCount(): Int {
        return asteroids.size
    }

    // Piece of code form Udacity course content ->
    // https://github.com/udacity/andfun-kotlin-mars-real-estate/blob/Step.05-Solution-Displaying-a-Grid-of-Internet-Images/app/src/main/java/com/example/android/marsrealestate/overview/PhotoGridAdapter.kt
    /*  companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
          override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
              return oldItem === newItem
          }

          override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
              return oldItem.id == newItem.id
          }
      }*/

    fun setAsteroidList(asteroids: List<Asteroid>) {
        if (this.asteroids !== asteroids)
            this.asteroids = asteroids

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(asteroids[position])
    }

    inner class AsteroidViewHolder(val binding: ListAsteroidsBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(asteroid: Asteroid) {
            with(binding) {
                asteroidModel = asteroid
                executePendingBindings()
            }
        }

        override fun onClick(v: View?) {
            clickListener.onAsteroidClick(asteroids[adapterPosition])
        }
    }


    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MarsProperty]
     * has been updated.
     *//*
    companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }
    }*/
}