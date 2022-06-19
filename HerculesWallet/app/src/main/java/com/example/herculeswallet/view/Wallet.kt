package com.example.herculeswallet.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.herculeswallet.R
import com.example.herculeswallet.databinding.WalletBinding

class Wallet : AppCompatActivity() {

    private lateinit var binding: WalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()!!.hide();
        binding = WalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, HomeFragment())
        fragmentTransaction.commit()

        /*binding.bottomNavigatinView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.homeNav -> {
                    Toast.makeText(this,"Test",Toast.LENGTH_SHORT)
                    return@setOnItemSelectedListener true
                }
                else -> {false}
            }
        }*/


    }
}