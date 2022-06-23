package com.example.herculeswallet.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.herculeswallet.R
import com.example.herculeswallet.model.Crypto

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    private var name = arrayOf("Bitcoin","Ethereum");
    private var crypto_list = mutableListOf<Crypto>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //var imageView: AppCompatImageView
        val name_crypto: TextView
        //var price_crypto: TextView

        init {
            //imageView = itemView.findViewById(R.id.image_crypto)
            name_crypto = itemView.findViewById(R.id.name_crypto)
            //price_crypto = itemView.findViewById(R.id.price_crypto)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.crypto_list_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name_crypto.text = name[position]
    }

    override fun getItemCount(): Int {
        return name.size
    }

    fun setList(list: List<Crypto>) {
        this.crypto_list = list.toMutableList()
        notifyDataSetChanged()
    }


}