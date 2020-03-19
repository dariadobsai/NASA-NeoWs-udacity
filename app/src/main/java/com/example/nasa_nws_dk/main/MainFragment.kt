package com.example.nasa_nws_dk.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasa_nws_dk.R
import com.example.nasa_nws_dk.databinding.FragmentMainBinding
import com.example.nasa_nws_dk.main.adapter.AsteroidsAdapter
import com.example.nasa_nws_dk.main.viewModels.AsteroidViewModelFactory
import com.example.nasa_nws_dk.main.viewModels.MainViewModel
import com.example.nasa_nws_dk.models.Asteroid

class MainFragment : Fragment(), AsteroidsAdapter.ClickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var asteroidsAdapter: AsteroidsAdapter
    //private lateinit var menu: Menu

    private val mainViewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(
            this,
            AsteroidViewModelFactory(activity.application, this.requireContext())
        ).get(MainViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
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

        setUpRecyclerView()

        populateAsteroidList()

        return binding.root
    }


    /**
     * Method to set up the RecyclerView i.e.:
     * - layoutManager
     * - adapter
     * - item decoration
     */
    private fun setUpRecyclerView() {
        asteroidsAdapter = AsteroidsAdapter(this)

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

    /**
     * Navigate to back to the DetailFragment, based on the selected asteroid
     */
    override fun onAsteroidClick(asteroid: Asteroid) {
        val action = MainFragmentDirections.actionShowDetail(asteroid)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        hideMenuItemBasedOnConnectivity(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_today_menu -> {
                // Return asteroids only from today
                mainViewModel.todayAsteroids.observe(viewLifecycleOwner, Observer { asteroids ->
                    asteroidsAdapter.setAsteroidList(asteroids)
                })
            }
            else -> populateAsteroidList()
        }

        return true
    }

    /**
     * Method listens to the isOnline variable and hides the menu item with show_all_menu if there is no Network.
     *
     * This is just an imitation of the situation when app is offline, but in reality asteroid list is supported automatically
     * for all menu items in both offline and online modes.
     */
    private fun hideMenuItemBasedOnConnectivity(menu: Menu) {
        mainViewModel.isOnline.observe(viewLifecycleOwner, Observer {
            val item: MenuItem = menu.findItem(R.id.show_all_menu)
            item.isVisible = it
        })

    }

    /**
     * Method observers the main asteroid list and sets it to the adapter
     */
    private fun populateAsteroidList() {

        mainViewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroids ->
            asteroidsAdapter.setAsteroidList(asteroids)
        })

    }
}
