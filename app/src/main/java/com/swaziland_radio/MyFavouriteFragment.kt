package com.swaziland_radio

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.swaziland_radio.room.MyRoomEntity
import com.swaziland_radio.room.ViewModel
import com.swaziland_radio.recyclerview.FavouriteRecyclerview
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_my_favourite.view.*

class MyFavouriteFragment : Fragment() {

    lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_favourite, container, false)

        MobileAds.initialize(requireContext(), getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        view.adViewf.loadAd(adRequest)
        view.adViewg.loadAd(adRequest)
        view.adViews.loadAd(adRequest)

        Handler(Looper.myLooper()).postDelayed({
            view.adViewf.loadAd(adRequest)
            view.adViewg.loadAd(adRequest)
            view.adViews.loadAd(adRequest)
        }, 9000L)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        viewModel.getEnt!!.observe(requireActivity(), Observer {
            val layoutManager = LinearLayoutManager(requireActivity())
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            view.iRecyclerview.layoutManager = layoutManager

            var recyclerView = FavouriteRecyclerview(
                requireContext(),
                it as MutableList<MyRoomEntity>,
                requireActivity()
            )
            view.iRecyclerview.adapter = recyclerView
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        val tool = view.findViewById<Toolbar>(R.id.myToolbar)
        (activity as AppCompatActivity).setSupportActionBar(tool)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = "Favourite Stations"
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().supportFragmentManager.beginTransaction().replace(android.R.id.content, DashboardFragment()).commit()
            }
            R.id.fav -> {
                requireActivity().supportFragmentManager.beginTransaction().replace(android.R.id.content, MyFavouriteFragment()).commit()
            }
            /* R.id.share_btn -> {
                 val int = Intent(Intent.ACTION_SEND)
                 int.putExtra(
                     Intent.EXTRA_TEXT,
                     "Try this new app, Radio Nigeria\nand stream all Nigeria radio station for free\nUse this link\nhttps://play.google.com/store/apps/details?id=com.adom"
                 )
                 int.type = "text/plain"
                 startActivity(int)
             }
            R.id.fav_star -> {
                 findNavController()!!.navigate(R.id.myFavouriteFragment)
             }*/
            R.id.exit -> {
                ActivityCompat.finishAffinity(requireActivity())
            }
        }
        return super.onOptionsItemSelected(item)
    }
}