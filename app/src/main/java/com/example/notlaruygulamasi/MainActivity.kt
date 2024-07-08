package com.example.notlaruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notlaruygulamasi.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var notlarListe:ArrayList<Notlar>
    private lateinit var adapter: NotlarAdapter
    private lateinit var refNotlar: DatabaseReference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Toolbar başlık ve alt başlık ekleme işlemi
        binding.toolbar.title = "Notlar Uygulaması"
        setSupportActionBar(binding.toolbar)

        //RecyclerViewin düzgün şekilde gözükmesi için gerekli düzenlemeler
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(this)

        val db = FirebaseDatabase.getInstance()
        refNotlar = db.getReference("notlar")

        notlarListe = ArrayList()

        adapter = NotlarAdapter(this,notlarListe)

        binding.rv.adapter = adapter


        tumNotlar()


        binding.fab.setOnClickListener{
            startActivity(Intent(this@MainActivity,NotKayitActivity::class.java))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun tumNotlar(){
        refNotlar.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                notlarListe.clear()

                var toplam = 0

                for(c in snapshot.children){
                    val not = c.getValue(Notlar::class.java)

                    if (not != null){
                        not.not_id = c.key
                        notlarListe.add(not)
                        toplam = toplam +(not.not1!!+not.not2!!)/2
                    }
                }

                adapter.notifyDataSetChanged()

                if(toplam != 0){
                    binding.toolbar.subtitle = "Ortalama ${toplam/notlarListe.size}"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}