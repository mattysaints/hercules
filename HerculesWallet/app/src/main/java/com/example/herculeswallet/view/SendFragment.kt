package com.example.herculeswallet.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.beust.klaxon.Klaxon
import com.example.herculeswallet.R
import com.example.herculeswallet.model.Crypto
import com.example.herculeswallet.model.User
import com.example.herculeswallet.utils.Encryption
import com.example.herculeswallet.viewmodels.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tomatobean.jsonparser.parseJson
import tomatobean.jsonparser.toJson


class SendFragment : Fragment(R.layout.fragment_send){

    private val model: MainViewModel by activityViewModels()
    private val encryption : Encryption = Encryption()
    private var database: DatabaseReference = Firebase.database.getReference("Users")
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var receiver_id : String = ""

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

        var user = model.userMutableLiveData.value!!
        val quantity_send : EditText = view.findViewById(R.id.quantity_send)
        val address_receiver : EditText = view.findViewById(R.id.address_receiver)
        val list_crypto : AutoCompleteTextView = view.findViewById(R.id.list_crypto)
        val action_button : FloatingActionButton = view.findViewById(R.id.fab_send)

        val adapterText =
            activity?.let {
                CustomArrayAdapter(it,user.preferences as ArrayList<String>)
            }

        list_crypto.setAdapter(adapterText)

        model.userMutableLiveData.observe(viewLifecycleOwner){
            adapterText!!.setList(it.preferences)
        }


        val md5_address = user.email.let { encryption.md5(it + "/"+ list_crypto.text) }
        CoroutineScope(Dispatchers.IO).launch { getReceiverId(address_receiver.text.toString()) }

        val crypto = Klaxon().parse<Crypto>(user.wallet.get(md5_address)!!.toJson())
        val qnty_crypto = crypto?.quantity_user!!.toDouble()

        action_button.setOnClickListener { view ->
            if(qnty_crypto >= quantity_send.text.toString().toDouble()){
                val temp_quantity = qnty_crypto - quantity_send.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    if (md5_address != null && receiver_id.isNotEmpty()) {
                        database.child(firebaseAuth.currentUser!!.uid).child("wallet").child(md5_address).child("quantity_user").setValue(temp_quantity)
                        database.child(receiver_id).get().addOnSuccessListener {
                            val qReceiver = it.child("wallet").child(address_receiver.text.toString()).child("quantity_user").getValue()
                            database.child(receiver_id).child("wallet").child(address_receiver.text.toString()).child("quantity_user").setValue(
                                quantity_send.text.toString().toDouble() + qReceiver.toString().toDouble()
                            )
                        }
                    } else {Snackbar.make(view, "Indirizzo errato", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show()}
                }
            } else {
                Snackbar.make(view, "Non hai fondi sufficienti", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun getReceiverId(address: String){
        database.get().addOnSuccessListener { i ->
            val utenti : HashMap<String,User> = HashMap()
            for (temp : DataSnapshot in i.children.toMutableSet()){
                temp.key?.let { temp.value.toString().parseJson(User::class)
                    ?.let { it1 -> utenti.put(it, it1) } }
            }
            for ((key, value) in utenti) {
                if(value.wallet.containsKey(address)){
                    receiver_id = key
                }
            }
        }
    }


    //GESTIRE QUANDO INDIRIZZO DESTINATARIO E' ERRATO

}