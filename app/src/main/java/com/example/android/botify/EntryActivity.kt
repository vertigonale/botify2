package com.example.android.botify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.botify.databinding.ActivityEntryBinding
import com.example.android.botify.menu.main.MainActivity

class EntryActivity : AppCompatActivity() {
    private val LOG_TAG = this::class.java.simpleName

    private lateinit var binding: ActivityEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_entry)

        binding.entryButton.setOnClickListener {
            Log.i(LOG_TAG, "$methodName + sOCL")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        val methodName = object{}.javaClass.enclosingMethod?.name
        Log.i(LOG_TAG, methodName!!)

        super.onDestroy()
    }
}