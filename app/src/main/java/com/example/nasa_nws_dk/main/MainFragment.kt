package com.example.nasa_nws_dk.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasa_nws_dk.databinding.FragmentMainBinding
import com.example.nasa_nws_dk.main.adapter.AsteroidsAdapter
import com.example.nasa_nws_dk.main.viewModels.AsteroidViewModelFactory
import com.example.nasa_nws_dk.main.viewModels.MainViewModel
import com.example.nasa_nws_dk.models.Asteroid


class MainFragment : Fragment(), AsteroidsAdapter.ClickListener {

    lateinit var binding: FragmentMainBinding
    lateinit var asteroidsAdapter: AsteroidsAdapter

    private val mainViewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, AsteroidViewModelFactory(activity.application, this.requireContext()))
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = this@MainFragment
            viewModel = mainViewModel
        }

        populateAsteroidList()

        // setHasOptionsMenu(true)

        return binding.root
    }

    // TODO: add progress bar
    private fun populateAsteroidList() {
        asteroidsAdapter = AsteroidsAdapter(this)

        mainViewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroids ->
            if (asteroids.isNotEmpty()) {
                asteroidsAdapter.setAsteroidList(asteroids)
            } else
                return@Observer
        })


        binding.asteroidRecycler.apply {
            adapter = asteroidsAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onAsteroidClick(asteroid: Asteroid) {
        val action = MainFragmentDirections.actionShowDetail(asteroid)
        findNavController().navigate(action)
    }

    /*  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
          inflater.inflate(R.menu.main_overflow_menu, menu)
          super.onCreateOptionsMenu(menu, inflater)
      }

      override fun onOptionsItemSelected(item: MenuItem): Boolean {
          return true
      }*/
}
