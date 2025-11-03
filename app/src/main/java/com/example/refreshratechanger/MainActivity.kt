package com.example.refreshratechanger

import android.content.Intent
import com.google.android.gms.ads.AdRequest
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.refreshratechanger.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var adView: AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Mobile Ads SDK
        MobileAds.initialize(this) {}

        // Set up the AdView
        adView = binding.adView

        // Create an ad request
        val adRequest = AdRequest.Builder().build()

        // For test ads, set test device IDs in the MobileAds settings
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf(AdRequest.DEVICE_ID_EMULATOR, "YOUR_DEVICE_ID"))
                .build()
        )

        // Load the ad
        adView.loadAd(adRequest)
        // Set up the toolbar
        setSupportActionBar(binding.toolbar)


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            /*ACTION_SEND action to launch an email client installed on your Android device.*/
            val mIntent = Intent(Intent.ACTION_SEND)
            /*To send an email you need to specify mailto: as URI using setData() method
            and data type will be to text/plain using setType() method*/
            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            // put recipient email in intent
            /* recipient is put as array because you may wanna send email to multiple emails
               so enter comma(,) separated emails, it will be stored in array*/
            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("llabropoulos@gmail.com"))
            //put the Subject in the intent
            mIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Refresh Rate Changer")
            //put the message in the intent
            mIntent.putExtra(Intent.EXTRA_TEXT, "Feedback for Refresh Rate Changer")


            try {
                //start email intent
                startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
            } catch (e: Exception) {
                //if any thing goes wrong for example no email client application or any exception
                //get and show exception message
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}