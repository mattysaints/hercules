package com.example.herculeswallet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.beust.klaxon.Klaxon
import com.example.herculeswallet.R
import com.example.herculeswallet.model.Crypto
import com.example.herculeswallet.model.User
import com.example.herculeswallet.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import tomatobean.jsonparser.toJson


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FavRecyclerViewAdapter.ViewHolder>? = null
    private val model: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val walletText : TextView = view.findViewById(R.id.userwallet)
        var user = model.getUserData().value
        val repo = model.cryptoListLiveData.value

        model.cryptoListLiveData.observe(viewLifecycleOwner){
            if(user !=null && it.toString().isNotEmpty()) {
                //Saldo
                var total: Double = showBalance(user!!,it)
                if(total.equals(0.0)) walletText.text = "0" else walletText.text = String.format("%.3f", total)

                //ViewPage
                val TabLayout: TabLayout = view.findViewById(R.id.tabLayout)
                val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)
                val adapt = ViewPagerAdapter(childFragmentManager, lifecycle)
                viewPager.adapter = adapt

                TabLayoutMediator(TabLayout, viewPager){
                        tab,position->when(position){
                    0->{
                        tab.text="Crypto"
                    }
                    1->{
                        tab.text="Preferiti"
                    }
                }
                }.attach()
            }
        }


        model.userMutableLiveData.observe(viewLifecycleOwner){
            user = it
            var total = repo?.let { it1 -> showBalance(it, it1) }
            if (total != null) {
                if(total.equals(0.0)) walletText.text = "0" else walletText.text = String.format("%.3f", total)
            }
        }

    }

    fun showBalance(user: User, repo : List<Crypto>): Double {
        var balance: Double = 0.0
        for (entry in user.wallet.toMap().entries.iterator()) {
            val crypto = Klaxon().parse<Crypto>(entry.value.toJson())
            val priceUSD = (repo.filter { it.name == crypto!!.name })?.first()
            if (priceUSD != null) {
                balance += (priceUSD.price_usd!!.toDouble() * crypto?.quantity_user!!.toDouble())
            }else{
                balance += (crypto?.price_usd!!.toDouble() * crypto?.quantity_user!!.toDouble())
            }
        }
        return balance;
    }

}