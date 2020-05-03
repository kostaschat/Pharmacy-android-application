package com.example.farmakeioo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_articles.*
import kotlin.math.round

class ActivityHospitals : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    companion object
    {
        var platos_hospitla = 0.0
        var mhkos_hospital=0.0
    }

    val products = arrayListOf<Hospital>()
    val name = mutableListOf<String>()
    val address = mutableListOf<String>()
    val imageId = mutableListOf<String>()
    val phone= arrayListOf<String>()
    val website =arrayListOf<String>()
    val latitude =arrayListOf<String>()
    val longitude =arrayListOf<String>()
    val distance =arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospitals)
        database = FirebaseDatabase.getInstance().reference

        loadDatabase()
    }

   private fun loadDatabase(){

        val ref = FirebaseDatabase.getInstance().getReference("hospital")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                products.clear()
                name.clear()
                address.clear()
                imageId.clear()
                phone.clear()
                website.clear()
                latitude.clear()
                longitude.clear()

                for (productSnapshot in dataSnapshot.children) {
                    val product = productSnapshot.getValue(Hospital::class.java)
                    products.add(product!!)
                }


                var i=0

                while (i<products.size)
                {
                    name.add( products[i].name)
                    address.add(products[i].address)
                    imageId.add(products[i].image)
                    phone.add(products[i].phone)
                    website.add(products[i].website)
                    latitude.add(products[i].latitude)
                    longitude.add(products[i].longitude)
                   distance.add(distance(latitude[i].toDouble(),longitude[i].toDouble(), platos_hospitla, mhkos_hospital).toString())

                    i++
                }

                for (i in 0 until distance.size)
                    for (j in 0 until distance.size)
                        if (distance[i]<distance[j])
                        {
                            var t=distance[i]
                            distance[i]=distance[j]
                            distance[j]=t

                            t=name[i]
                            name[i]=name[j]
                            name[j]=t

                            t=address[i]
                            address[i]=address[j]
                            address[j]=t

                            t=imageId[i]
                            imageId[i]=imageId[j]
                            imageId[j]=t

                            t=phone[i]
                            phone[i]=phone[j]
                            phone[j]=t

                            t=website[i]
                            website[i]=website[j]
                            website[j]=t

                            t=latitude[i]
                            latitude[i]=latitude[j]
                            latitude[j]=t

                            t=longitude[i]
                            longitude[i]=longitude[j]
                            longitude[j]=t

                        }

                val myListAdapter = MyListHospital(this@ActivityHospitals,name,address,imageId,phone,website,latitude,longitude,distance)
                listView.adapter = myListAdapter
            }
            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(
            Math.toRadians(lat1)
        ) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta))
        dist = Math.acos(dist)
        dist = Math.toDegrees(dist)
        dist = dist * 60 * 1.1515
        dist = dist * 1.609344
        dist= round(dist*1000) /1000
        return (dist)
    }
}
