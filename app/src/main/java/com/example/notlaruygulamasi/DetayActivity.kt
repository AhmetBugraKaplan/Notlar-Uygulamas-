package com.example.notlaruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.example.notlaruygulamasi.databinding.ActivityDetayBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetayActivity : AppCompatActivity() {
    private lateinit var not:Notlar
    private lateinit var binding : ActivityDetayBinding
    private lateinit var refNotlar:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarNotDetay.title = "Not Detay"
        setSupportActionBar(binding.toolbarNotDetay)

        val db = FirebaseDatabase.getInstance()
        refNotlar = db.getReference("notlar")

        not = intent.getSerializableExtra("nesne") as Notlar

        binding.editTextDers.setText(not.ders_adi)
        binding.editTextNot1.setText((not.not1).toString())
        binding.editTextNot2.setText((not.not2).toString())
    }

    override fun onCreateOptionsMenu(menu:Menu?):Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sil -> {
                Snackbar.make(binding.toolbarNotDetay,"Silinsin mi?",Snackbar.LENGTH_SHORT)
                    .setAction("EVET"){
                        refNotlar.child(not.not_id!!).removeValue()
                        startActivity(Intent(this@DetayActivity,MainActivity::class.java))
                        finish()
                    }.show()
                return true
            }
            R.id.action_duzenle -> {

                val ders_adi = binding.editTextDers.text.toString().trim()
                val not1 = binding.editTextNot1.text.toString().trim()
                val not2 = binding.editTextNot2.text.toString().trim()

                if (TextUtils.isEmpty(ders_adi)){
                    Snackbar.make(binding.toolbarNotDetay,"Ders adını giriniz",Snackbar.LENGTH_SHORT).show()
                    return false
                }

                if (TextUtils.isEmpty(not1)){
                    Snackbar.make(binding.toolbarNotDetay,"1. notu giriniz",Snackbar.LENGTH_SHORT).show()
                    return false
                }

                if (TextUtils.isEmpty(not2)){
                    Snackbar.make(binding.toolbarNotDetay,"2. notu giriniz",Snackbar.LENGTH_SHORT).show()
                    return false
                }

                val bilgiler = HashMap<String,Any>()
                bilgiler.put("ders_adi",ders_adi)
                bilgiler.put("not1",not1.toInt())
                bilgiler.put("not2",not2.toInt())

                refNotlar.child(not.not_id!!).updateChildren(bilgiler)

                startActivity(Intent(this@DetayActivity,MainActivity::class.java))
                finish()
                return true
            }
            else -> return false
        }
    }
}