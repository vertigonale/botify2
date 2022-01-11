package com.example.android.botify.menu.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.example.android.botify.R
import com.example.android.botify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val LOG_TAG = this::class.java.simpleName

    private lateinit var binding: ActivityMainBinding
    private lateinit var menuTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

//        binding.bottomAppBar.menu[R.id.menu_name].title.



    }

    //TODO check if necessary (app runs without crashes when commented out)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

/*        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
            Log.i(LOG_TAG, "$methodName + MenuInflated?")*/

        menuInflater.inflate(R.menu.bottom_app_bar, menu)
        Log.i(LOG_TAG, "$methodName + bottom_app_bar?")

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.play_pause -> {
                    // TODO play pause
                    true
                }
                R.id.more -> {
                    // TODO more
                    true
                }
                else -> false
            }
        }

/*        binding.bottomAppBar.setOnMenuItemClickListener { menuI
        }*/

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

    override fun onDestroy() {
        val methodName = object{}.javaClass.enclosingMethod?.name
            Log.i(LOG_TAG, methodName!!)

        val delete: Boolean = this.cacheDir.deleteRecursively()
        Log.i(LOG_TAG, "$methodName + Delete? $delete")

        super.onDestroy()
    }
}