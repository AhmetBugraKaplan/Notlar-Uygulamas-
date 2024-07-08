package com.example.notlaruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.notlaruygulamasi.databinding.ActivityNotKayitBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NotKayitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotKayitBinding
    private lateinit var refNotlar: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotKayitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarNotDetay.title = "Not Kayıt"
        setSupportActionBar(binding.toolbarNotDetay)

        val db = FirebaseDatabase.getInstance()
        refNotlar = db.getReference("notlar")

        binding.buttonKaydett.setOnClickListener{

            val ders_adi = binding.editTextDers.text.toString().trim()
            val not1 = binding.editTextNot1.text.toString().trim()
            val not2 = binding.editTextNot2.text.toString().trim()

            if (TextUtils.isEmpty(ders_adi)){
                Snackbar.make(binding.toolbarNotDetay,"Ders adını giriniz",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(not1)){
                Snackbar.make(binding.toolbarNotDetay,"1. notu giriniz",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(not2)){
                Snackbar.make(binding.toolbarNotDetay,"2. notu giriniz",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val not = Notlar("",ders_adi,not1.toInt(),not2.toInt())
            refNotlar.push().setValue(not)

            startActivity(Intent(this@NotKayitActivity,MainActivity::class.java))
            finish()
        }
    }
}