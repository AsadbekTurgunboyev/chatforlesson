package com.example.chatapp.ui.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.model.message.MessageModel
import com.example.chatapp.utils.Const
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity(), TextWatcher {

    private lateinit var viewBinding: ActivityChatBinding
    var friendKey: String? = ""
    var roomKey: String? = ""
    lateinit var reference: DatabaseReference

    val list = arrayListOf<MessageModel>()
    val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            list.clear()
            for (ds in snapshot.children){
                val model = ds.getValue(MessageModel::class.java)
                model?.let { list.add(it) }
            }

            viewBinding.chatRecyclerview.adapter = ChatAdapter(list)
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        friendKey = intent.getStringExtra(Const.FRIEND_KEY)

        roomKey = if (friendKey!! > getData()) {
            "$friendKey-${getData()}"
        } else {
            "${getData()}-${friendKey}"
        }

        reference = FirebaseDatabase.getInstance().getReference(Const.CHATS)

        roomKey?.let {
            reference.child(it).addValueEventListener(valueEventListener)

        }

        with(viewBinding) {
            inputEdittext.addTextChangedListener(this@ChatActivity)

            buttonSend.setOnClickListener {
                roomKey?.let {
                    val model = MessageModel(
                        message = inputEdittext.text.toString(),
                        fromTo = getData(),
                        sendTo = friendKey,
                        timeStamp = getCurrentDateTime().toString("dd.MM.yyyy hh:mm:ss")
                    )
                    reference.child(it).push().setValue(model)
                    inputEdittext.text.clear()
                }

            }
        }

    }

    private fun visibleSend() {
        viewBinding.buttonSend.visibility = View.VISIBLE
        viewBinding.buttonVoice.visibility = View.GONE
    }

    private fun visibleUnSend() {
        viewBinding.buttonSend.visibility = View.GONE
        viewBinding.buttonVoice.visibility = View.VISIBLE
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text.isNullOrEmpty()) {
            visibleUnSend()
        } else {
            visibleSend()
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    private fun getData(): String {
        val pref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val key = pref.getString("key", "")
        return key.toString()
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}