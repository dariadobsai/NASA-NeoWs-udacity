package com.example.nasa_nws_dk.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.nasa_nws_dk.R
import com.example.nasa_nws_dk.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.activity_main.*

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater)

        val asteroid = DetailFragmentArgs.fromBundle(arguments!!).asteroid
        binding.asteroid = asteroid

        // Set toolbar title to the selected asteroid name
        activity?.toolbar?.title = asteroid.name

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(activity!!)
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
