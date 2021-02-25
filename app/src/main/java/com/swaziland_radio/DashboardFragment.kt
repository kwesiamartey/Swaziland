package com.swaziland_radio

import android.app.Dialog

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.swaziland_radio.room.ViewModel
import com.swaziland_radio.data.RadioData
import com.swaziland_radio.recyclerview.ListOfRadioStationRecyclerview
import kotlinx.android.synthetic.main.activity_layout_searchbar.*
import kotlinx.android.synthetic.main.activity_recyclerview_list.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_player.view.*


class DashboardFragment : Fragment() {

    var mediaPlayer: MediaPlayer = MediaPlayer()

    lateinit var viewModel: ViewModel


    var listOfRadio = mutableListOf<RadioData>()
    var singleListOfRadio = mutableListOf<RadioData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

        MobileAds.initialize(requireContext(), getString(R.string.admob_app_id))
        val adRequests = AdRequest.Builder().build()
        view.adViewWrap.loadAd(adRequests)
        view.adViewWrap1.loadAd(adRequests)
        view.adViewWrap2.loadAd(adRequests)
        view.adViewWrap3.loadAd(adRequests)

        Handler(Looper.myLooper()).postDelayed({
            view.adViewWrap.loadAd(adRequests)
            view.adViewWrap1.loadAd(adRequests)
            view.adViewWrap2.loadAd(adRequests)
            view.adViewWrap3.loadAd(adRequests)
        }, 9000L)

        listOfRadio.add(RadioData("https://stream.radio.co/s7ca1c5e97/listen", "Hala Radio", "Hala Radio"))
        listOfRadio.add(RadioData("http://iceant.antfarm.co.za:8000/TWR", "TWR Africa", "TWR Africa"))
        listOfRadio.add(RadioData("http://freeuk26.listen2myradio.com:7121/;;", "Voice of the Church FM - VOC 2 FM English Channel", "Voice of the Church FM - VOC 2 FM English Channel"))
        listOfRadio.add(RadioData("http://freeuk14.listen2myradio.com:6124/;", "Voice of the Church FM - VOCFM", "Voice of the Church FM - VOCFM"))
        listOfRadio.add(RadioData("http://streams.radio.co/s7dbbf9f9f/listen", "Radio Carrum", "Radio Carrum"))

        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        view.radios_recyclerview.layoutManager = layoutManager

        val adapter =
            ListOfRadioStationRecyclerview(requireContext(), listOfRadio, requireActivity())
        view.radios_recyclerview.adapter = adapter

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }

    private fun stop() {
        mediaPlayer!!.pause()
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
        mediaPlayer = MediaPlayer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        val tool = view.findViewById<Toolbar>(R.id.myToolbar)
        (activity as AppCompatActivity).setSupportActionBar(myToolbar)
        (activity as AppCompatActivity).supportActionBar!!.title = "Swaziland Radio"
        (activity as AppCompatActivity).supportActionBar!!.show()
        (activity as AppCompatActivity).setSupportActionBar(tool)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, DashboardFragment()).commit()
            }
            R.id.fav -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, MyFavouriteFragment()).commit()
            }
            R.id.exit -> {
                ActivityCompat.finishAffinity(requireActivity())
            }
            R.id.searchBar -> {

                val dialog = Dialog(requireActivity())
                dialog.setContentView(R.layout.activity_layout_searchbar)
                var srchText = dialog.editTextSearch.text
                dialog.show()
                dialog.btn_sbmit.setOnClickListener {

                    listOfRadio?.forEach {
                        when {

                            it.name == srchText.toString() -> {
                                singleListOfRadio.add(RadioData(it.url, it.name, it.description))
                                val layoutManager =
                                    StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
                                view?.radios_recyclerview!!.layoutManager = layoutManager

                                val adapter = ListOfRadioStationRecyclerview(
                                    requireContext(),
                                    singleListOfRadio,
                                    requireActivity()
                                )
                                view?.radios_recyclerview!!.adapter = adapter
                                dialog.hide()

                            }

                            else -> {
                                Toast.makeText(requireContext(), "Looking for station please wait..", Toast.LENGTH_LONG)
                                    .show()
                                dialog.hide()
                            }

                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}