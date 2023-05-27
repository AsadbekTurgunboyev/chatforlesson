package com.example.chatapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.MainActivity
import com.example.chatapp.databinding.ActivityLoginBinding
import com.example.chatapp.model.user.UserModel
import com.example.chatapp.utils.Const
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityLoginBinding
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USERS)


        with(viewBinding) {
            nextDetailCarFbn.setOnClickListener {
                if (inputNameEt.editText?.text.toString().isNullOrEmpty()) {
                    inputNameEt.error = "Ismingizni kiriting!"
                    return@setOnClickListener
                }
                if (inputSurnameEt.editText?.text.toString().isNullOrEmpty()) {
                    inputNameEt.error = "Familyangizni kiriting!"
                    return@setOnClickListener
                }

                val key = databaseReference.push().key
                val model = UserModel(
                    name = inputNameEt.editText?.text.toString(),
                    surname = inputSurnameEt.editText?.text.toString(),
                    key = key
                )
               saveData("name",inputNameEt.editText?.text.toString())
                key?.let { it1 -> databaseReference.child(it1).setValue(model).addOnCompleteListener {
                    if (it.isSuccessful){
                        saveData("key",key)
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                } }



            }

        }
    }

    fun saveData(name : String ,data : String){
        val preference = getSharedPreferences("MyPref", MODE_PRIVATE)
        preference.edit().putString(name,data).apply()
    }
}