package com.tunaskelapa.pantunn.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.tunaskelapa.pantunn.data.DataPantun
import com.tunaskelapa.pantunn.databinding.ActivityMainBinding
import com.tunaskelapa.pantunn.ui.createPantun.CreatePantunActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var db : DatabaseReference
    private lateinit var listPantun : ArrayList<DataPantun>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MainAdapter()
        db = FirebaseDatabase.getInstance().getReference("Pantunn")
        listPantun = ArrayList()

        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        binding.rvMain.setHasFixedSize(true)

        getDatabase()

        binding.imageButtonSearch.setOnClickListener {
            val intent = Intent(it.context, CreatePantunActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDatabase() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (item in snapshot.children) {
                        binding.progressBarMain.visibility = View.GONE
                        val pantun = item.getValue(DataPantun::class.java)
                        listPantun.add(pantun!!)
                        adapter.notifyDataSetChanged()
                        adapter.setData(listPantun)
                    }
                } else {
                    Log.d("FirebaseClient", "Snapshot null")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FirebaseClient", "Cancelled")
            }
        })
    }
}