package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.adapter.PersonAdapter
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.model.user.UserModel
import com.example.chatapp.utils.Const
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    lateinit var viewBinding: ActivityMainBinding
    val personList = arrayListOf<UserModel>()

    private val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (ds in snapshot.children){

                val model = ds.getValue(UserModel::class.java)
                if (model?.key != getData("key")){

                    model?.let { personList.add(it) }
                }
            }

            viewBinding.recyclerPerson.adapter = PersonAdapter(personList)
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USERS)

        databaseReference.addValueEventListener(valueEventListener)


    }


    fun getData(name: String): String {
        val pref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val key = pref.getString(name, "")
        return key.toString()
    }
}