package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var UserRecyclerView:RecyclerView
    private lateinit var userlist:ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        mAuth = FirebaseAuth.getInstance()
        userlist = ArrayList()
        adapter = UserAdapter(this, userlist)
        UserRecyclerView=findViewById(R.id.UserRecyclerView)
        UserRecyclerView.layoutManager=LinearLayoutManager(this)
        UserRecyclerView.adapter=adapter
        val CurrentUserUid = mAuth.currentUser?.uid
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for(postsnapshot in snapshot.children){
                    val CurrentUser=postsnapshot.getValue(User::class.java)
                    if (CurrentUser?.uid != CurrentUserUid) {
                        userlist.add(CurrentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })




        }





    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.LogOut) {
            mAuth.signOut()
            val intent=Intent(this@MainActivity,Login::class.java)
            startActivity(intent)
            finish()
            return true



        }
        return true
        }




    }
