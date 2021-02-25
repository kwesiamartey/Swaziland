package  com.swaziland_radio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.swaziland_radio.SplashScreenFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            this.supportFragmentManager.beginTransaction().replace(android.R.id.content, SplashScreenFragment()).commit()
        }

       // supportActionBar!!.hide()
        MobileAds.initialize(this) {}
    }
}