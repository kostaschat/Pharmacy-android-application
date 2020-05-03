package com.example.farmakeioo

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class MyListHospital(private val context: ActivityHospitals, private val name: MutableList<String>, private val address: MutableList<String>, private val imageId: MutableList<String>,
                     private val phone: MutableList<String>,private val website: MutableList<String>,private val latitude: MutableList<String>,private val longitude: MutableList<String>
                     ,private val distance: MutableList<String>)
    : ArrayAdapter<String>(context, R.layout.hospital_view, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.hospital_view, null, true)
        val text_name = rowView.findViewById(R.id.text_name) as TextView
        val image = rowView.findViewById(R.id.imageView) as ImageView
        val text_address = rowView.findViewById(R.id.text_address) as TextView
        val text_website = rowView.findViewById(R.id.text_website) as TextView
        val text_distance = rowView.findViewById(R.id.text_distance) as TextView
        val text_phone = rowView.findViewById(R.id.text_phone) as TextView
        val text_direction = rowView.findViewById(R.id.text_direction) as TextView
        val text_ph = rowView.findViewById(R.id.text_ph) as TextView
        var eikona=imageId[position]


        text_address.text="Διεύθυνση: "+address[position]
        text_website.text="Ιστοσελίδα: "+website[position]
        text_phone.text=phone[position]
        text_ph.text="Τηλέφωνο: "
        text_distance.text="Aπόσταση: "+distance[position]+" χλμ."
        text_name.text=name[position]
        text_phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phone[position]}"))
            context.startActivity(intent)
        }
        Picasso.with(context).load(eikona).resize(1000, 455).centerCrop().into(image)
        text_direction.setOnClickListener {
            val url =
                "https://www.google.com/maps/dir/?api=1&destination=" + latitude[position] + "," + longitude[position] + "&travelmode=driving"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
        return rowView
    }
}