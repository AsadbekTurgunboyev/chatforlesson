package com.example.chatapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.model.user.UserModel
import com.example.chatapp.ui.chat.ChatActivity
import com.example.chatapp.utils.Const

class PersonAdapter(private val list: List<UserModel>): RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val personName : TextView = itemView.findViewById(R.id.txt_name)
        val lastMessage: TextView = itemView.findViewById(R.id.txt_last_message)
        val isRead: ImageView = itemView.findViewById(R.id.is_read)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person,parent,false)
        return PersonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.personName.text = list[position].name
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,ChatActivity::class.java)
            intent.putExtra(Const.FRIEND_KEY,list[holder.adapterPosition].key)
            holder.itemView.context.startActivity(intent)
        }
    }
}