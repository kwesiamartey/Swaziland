package com.swaziland_radio

import android.app.AlertDialog
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
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
import com.swaziland_radio.room.ViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.swaziland_radio.room.MyRoomEntity
import kotlinx.android.synthetic.main.fragment_player.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerFragment: Fragment() {

    var mediaPlayer:MediaPlayer = MediaPlayer()

    lateinit var viewModel: ViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

        //Toast.makeText(context, "${viewModel.playerUrl} + ${viewModel.title} + ${viewModel.desc}", Toast.LENGTH_LONG).show()

        init(view)

        Glide.with(requireContext()).load("https://i.gifer.com/7ZNT.gif").into(view.img)

        MobileAds.initialize(requireContext(), getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        view.adView12.loadAd(adRequest)
        view.adView22.loadAd(adRequest)
        view.adView122.loadAd(adRequest)
        view.adView44.loadAd(adRequest)

        Handler(Looper.myLooper()).postDelayed({
            view.adView12.loadAd(adRequest)
            view.adView22.loadAd(adRequest)
            view.adView122.loadAd(adRequest)
            view.adView44.loadAd(adRequest)
        }, 9000L)

        view.b_play.setOnClickListener{
            onDestroy()

            try {
                view.progresbar_loading.visibility = View.VISIBLE
                view.txt_nowPlaying.setText("Loading....")
                mediaPlayer.setDataSource("${viewModel.playerUrl}")
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                mediaPlayer.prepareAsync()

                mediaPlayer!!.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
                    mp.start()
                    mp.start()
                    view.progresbar_loading.visibility = View.GONE
                    view.b_stop.visibility = View.VISIBLE
                    view.b_play.visibility = View.GONE
                    view.txt_nowPlaying.setText("Now Playing...")
                })

            }catch (e: Exception){
                var toast= Toast.makeText(requireContext(), "${e} Error", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
        view.b_stop.setOnClickListener {
            onDestroy()
            view.b_stop.visibility = View.GONE
            view.b_play.visibility = View.VISIBLE
            view.txt_nowPlaying.setText("Click on play")
        }
        view.feedback_button.setOnClickListener{

            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("FeedBack")
            alertDialog.setMessage("Please let us know what you think about this app.")
            alertDialog.setNegativeButton("Cancel"){_,_ ->

            }
            alertDialog.setPositiveButton("Send Feedback"){_,_->
                val int = Intent(Intent.ACTION_VIEW)
                int.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.swaziland"))
                startActivity(int)
            }
            alertDialog.show()
        }
        view.favourite_button.setOnClickListener{
            //Toast.makeText(requireContext(), "Station added to fourite", Toast.LENGTH_SHORT).show()
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Favourite")
            alertDialog.setMessage("Do you want to add station to your fovourite list?")
            alertDialog.setNegativeButton("Cancel"){_,_->

            }
            alertDialog.setPositiveButton("Add Station to favourite"){_,_->
                GlobalScope.launch(Dispatchers.IO) {
                    val entity = MyRoomEntity(name = "${viewModel.title}", urlLink = "${viewModel.playerUrl}")
                    viewModel.insertRepository(entity)
                }
            }
            alertDialog.show()
        }
        view.share_button.setOnClickListener {
            val int = Intent(Intent.ACTION_SEND)
            int.putExtra(Intent.EXTRA_TEXT, "Try this new app, Radio Nigeria\nand stream all Nigeria radio station for free\nUse this link\nhttps://play.google.com/store/apps/details?id=com.swaziland")
            int.type = "text/plain"
            startActivity(int)
        }

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
        (activity as AppCompatActivity).setSupportActionBar(tool)
        (activity as AppCompatActivity).supportActionBar!!.title = "${viewModel.title}"
        (activity as AppCompatActivity).supportActionBar!!.show()
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.playermenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
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
             }*/
            R.id.searchBar -> {
                setMenuVisibility(false)
             }
            R.id.exit -> {
                ActivityCompat.finishAffinity(requireActivity())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun init(view : View){
        view.title.text = "${viewModel.title}"
        view.descs.text = "${viewModel.desc}"
    }

}