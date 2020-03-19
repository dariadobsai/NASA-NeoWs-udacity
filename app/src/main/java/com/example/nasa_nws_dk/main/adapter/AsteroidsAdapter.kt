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
    }

    interface ClickListener {
        fun onAsteroidClick(asteroid : Asteroid)
    }

    override fun getItemCount(): Int {
        return asteroids.size
    }

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
}