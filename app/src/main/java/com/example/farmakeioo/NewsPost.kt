package com.example.farmakeioo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_post.*

class NewsPost : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.news_post)

            val title= intent.getStringExtra("title")
            val description= intent.getStringExtra("description")
            val image= intent.getStringExtra("image")
            val datee =intent.getStringExtra("date")
            val link =intent.getStringExtra("link")

            titlee.text=title
            Picasso.with(this).load(image).resize(500, 1000).centerInside().into(imagee)
            date.text=datee
            source.text="Πηγή: "+link
            descriptionn.text=description
        }
}