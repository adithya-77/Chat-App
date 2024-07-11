package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context:Context,val userlist:ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view:View=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentuser=userlist[position]
        holder.txtname.text=currentuser.nme
        holder.itemView.setOnClickListener{
            val intent=Intent(context,ChatActivity::class.java)

            intent.putExtra("name",currentuser.nme)
            intent.putExtra("uid",currentuser.uid)

            context.startActivity(intent)
        }

    }


    class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val txtname=itemView.findViewById<TextView>(R.id.txt_name)


    }

}