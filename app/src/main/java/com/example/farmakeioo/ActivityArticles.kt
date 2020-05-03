package com.example.farmakeioo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_articles.*


class ActivityArticles : AppCompatActivity() {
    val title = mutableListOf<String>()
    val description = mutableListOf<String>()
    val imageId = mutableListOf<String>()
    val products = arrayListOf<News>()
    val date= arrayListOf<String>()
    val link =arrayListOf<String>()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        database = FirebaseDatabase.getInstance().reference

        loadDatabase()

    }
    fun loadDatabase(){

        val ref = FirebaseDatabase.getInstance().getReference("news")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                products.clear()
                description.clear()
                title.clear()
                imageId.clear()
                for (productSnapshot in dataSnapshot.children) {
                    val product = productSnapshot.getValue(News::class.java)
                    products.add(product!!)
                }
                var i=0

                while (i<products.size)
                {

                    title.add( products[i].title)
                    description.add(products[i].description)
                    date.add(products[i].date.substring(4,17))



                    if (products[i].raw_description.contains("img src").not())
                    {
                        imageId.add("https://www.gsmarket.ro/7844-large_default/difuzor-htc-desire-510-original.jpg")
                    }
                    else
                    {
                        imageId.add(products[i].raw_description.substringAfter("img src=\"").substringBefore("\" /"))
                    }
                    link.add(products[i].link.substringAfter("://").substringBefore("/"))

                    i++

                }

                val myListAdapter = MyListAdapter(this@ActivityArticles,title,description,imageId,date)
                listView.adapter = myListAdapter

                listView.setOnItemClickListener(){adapterView, view, position, id ->

                    val intent = Intent(this@ActivityArticles, NewsPost::class.java)
                    intent.putExtra("description",description[position])
                    intent.putExtra("title",title[position])
                    intent.putExtra("image",imageId[position])
                    intent.putExtra("link",link[position])
                    intent.putExtra("date",date[position])

                    startActivity(intent)

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
    }
}


