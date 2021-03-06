package com.example.herculeswallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.herculeswallet.R
import com.example.herculeswallet.model.Crypto
import com.example.herculeswallet.model.User
import com.example.herculeswallet.viewmodels.MainViewModel


class FavsFragment : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FavRecyclerViewAdapter.ViewHolder>? = null
    private val model: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.fav_crypto)
        recyclerView.layoutManager = layoutManager
        var adapter = FavRecyclerViewAdapter()
        recyclerView.adapter = adapter

        val user = model.getUserData().value

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if(user!!.preferences[position].equals("BTC").not() && (user.preferences.size != 1)){
                    val temp = user.preferences.toMutableList()
                    temp.removeAt(position)
                    model.setPreferences(temp)
                }
                else{
                    adapter.setList(assetToCrypto(user))
                    Toast.makeText(context,"Azione non consentita", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        var repo = model.cryptoListLiveData.value

        model.userMutableLiveData.observe(viewLifecycleOwner){
            val favs = mutableListOf<Crypto>()
            //From List<String> to List<Crypto>
            val iterator = it.preferences.listIterator()
            while (iterator.hasNext()) {
                val asset = iterator.next()
                val crypto_repo = repo?.filter { it.asset_id == asset }?.firstOrNull()
                val crypto = Crypto(
                    asset,
                    "",
                    (String.format("%.3f", crypto_repo?.price_usd)).toDouble(),
                    crypto_repo?.logo_url.toString(),
                    0.0
                )
                favs.add(crypto)
            }
            adapter.setList(favs)
        }

        model.cryptoListLiveData.observe(viewLifecycleOwner){
            repo = it
        }

    }

    fun assetToCrypto(it: User) : List<Crypto>{
        val favs = mutableListOf<Crypto>()
        val repo = model.cryptoListLiveData.value
        //From List<String> to List<Crypto>
        val iterator = it.preferences.listIterator()
        while (iterator.hasNext()) {
            val asset = iterator.next()
            val crypto_repo = repo?.filter { it.asset_id == asset }?.firstOrNull()
            val crypto = Crypto(
                asset,
                "",
                (String.format("%.3f", crypto_repo?.price_usd)).toDouble(),
                crypto_repo?.logo_url.toString(),
                0.0
            )
            favs.add(crypto)
        }
        return favs
    }

}