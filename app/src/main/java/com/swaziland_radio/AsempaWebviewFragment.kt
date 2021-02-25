package com.swaziland_radio

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_adon_web_player.view.*


class AsempaWebviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_asempa_webview, container, false)


        MobileAds.initialize(requireContext(), getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        view.adViewsWeb.loadAd(adRequest)

        MobileAds.initialize(requireContext(), getString(R.string.admob_app_id))
        val adRequests = AdRequest.Builder().build()
        view.adViewsWebview.loadAd(adRequests)

        Handler(Looper.myLooper()).postDelayed({
            view.adViewsWeb.loadAd(adRequest)
            view.adViewsWebview.loadAd(adRequests)
        }, 9000L)

        val mWebView = view.wbView as WebView
        // Enable Javascript

        // Enable Javascript
        val webSettings: WebSettings = mWebView.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.setJavaScriptEnabled(true)
        webSettings.setBuiltInZoomControls(false)
        webSettings.setSupportZoom(false)
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true)
        webSettings.setAllowFileAccess(true)
        webSettings.setDomStorageEnabled(true)
        mWebView.loadUrl("http://player.streamguys.com/atunwa/mgl/asempafm/player.php?l=layout-small")        // Force links and redirects to open in the WebView instead of in a browser

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(WebViewClient())
        val progress = ProgressDialog.show(
            requireActivity(),
            "Asempa Fm...",
            "Loading Player please there was a challenge with our player manage this for now. please wait wait",
            true
        )
        mWebView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (progress != null) progress.dismiss()
            }
        })



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        val tool = view.findViewById<Toolbar>(R.id.myToolbar)
        (activity as AppCompatActivity).setSupportActionBar(tool)
        (activity as AppCompatActivity).supportActionBar!!.title = "Adom"
        (activity as AppCompatActivity).supportActionBar!!.show()
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
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