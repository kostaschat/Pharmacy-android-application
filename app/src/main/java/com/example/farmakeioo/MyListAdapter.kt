package com.example.farmakeioo

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class MyListAdapter(private val context: ActivityArticles, private val title: MutableList<String>, private val description: MutableList<String>,
                    private val imgid: MutableList<String>, private val datee: MutableList<String>)
     : ArrayAdapter<String>(context, R.layout.news_view, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
       val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.news_view, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val date = rowView.findViewById(R.id.date) as TextView
        var eikona=imgid[position]
        titleText.text = title[position]
        Picasso.with(context).load(eikona).resize(500, 455).into(imageView)

        date.text=datee[position]

        return rowView
    }
}