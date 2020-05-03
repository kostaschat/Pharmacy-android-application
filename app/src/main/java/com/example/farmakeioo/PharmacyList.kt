package com.example.farmakeioo

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Button
import java.util.*
import kotlin.math.round


class PharmacyList(private val context: Activity, internal var pharmacy: List<Pharmacy>) : ArrayAdapter<Pharmacy>(context, R.layout.layout_list_pharmacy, pharmacy) {

        var mhkos:Double = 0.0
        var platos:Double = 0.0

        companion object
        {
        var pla = 0.0
        var mka=0.0
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.layout_list_pharmacy, null, true)
        val textViewName = listViewItem.findViewById(R.id.textViewName) as TextView
        val textViewNomos = listViewItem.findViewById(R.id.textViewNomos) as TextView
        val textViewWrario= listViewItem.findViewById(R.id.textViewWrario) as TextView
        val textViewThlefono =listViewItem.findViewById(R.id.textViewThlefono) as Button
        val directions =listViewItem.findViewById(R.id.directions) as Button
        val leitourgia1 =listViewItem.findViewById(R.id.leitourgia) as TextView
        val apostash=listViewItem.findViewById(R.id.apostash) as TextView
        var wrario=""
        var leitourgia=""
        var meros1=" "
        var meros2=" "
        var meros1a=" "
        var meros1b=" "
        var meros2a=" "
        var meros2b=" "
        var day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        var hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var minutes = Calendar.getInstance().get(Calendar.MINUTE)
        var seconds = Calendar.getInstance().get(Calendar.SECOND)
        var dayMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        val kpharmacy = pharmacy[position]
        meros1=kpharmacy.extraweres.take(4)
        meros2=kpharmacy.extraweres.takeLast(4)
        if (meros1=="null")
        {
            meros1="0000"
            meros2="0000"
        }
        meros1a=meros1.take(2)
        meros1b=meros1.takeLast(2)
        meros2a=meros2.take(2)
        meros2b=meros2.takeLast(2)
        mhkos = kpharmacy.mhkos
        platos=kpharmacy.platos




        if ( (day==3)||(day==5)||(day==6))
        {
           wrario="08:00-13:30 & 17:30-21:00"

            if (((hour>=8)&&(hour<13)||(hour==13)&&(minutes<30))||((hour>17)&& (hour<21))||((hour==17)&&(minutes>30)&&(seconds>1))||((hour==20)
                        &&(minutes<59)&&(seconds<59))||((hour>=meros1a.toInt())&& (hour<=meros2a.toInt())||(hour==meros1a.toInt() && minutes>=meros1b.toInt()
                        ||(hour==meros2a.toInt() && minutes<=meros2b.toInt())))     )
            {
                leitourgia="ANOIXTΟ"
            }
            else
            {
                leitourgia = "ΚΛΕΙΣΤΟ"
            }
        }
        else if ((day==2)||(day==4))
        {
            wrario="08:00-14:30"
            if (((hour>=8)&&(hour<14)||(hour==14)&&(minutes<30))||((hour>meros1a.toInt()) && (hour<meros2a.toInt())||(hour==meros1a.toInt())
                        && (minutes>=meros1b.toInt()) ||(hour==meros2a.toInt()) && (minutes<meros2b.toInt()))   )
            {
                leitourgia="ANOIXTΟ"
            }
            else
            {
                leitourgia = "ΚΛΕΙΣΤΟ"
            }
        }
        if (day==7)
            if (((hour>=meros1a.toInt())&& (hour<=meros2a.toInt())||(hour==meros1a.toInt() && minutes>=meros1b.toInt() ||(hour==meros2a.toInt()
                        && minutes<meros2b.toInt()))))
            {
                leitourgia="ANOIXTΟ"
            }
            else
            {
                leitourgia = "ΚΛΕΙΣΤΟ"
            }
        if (day == 1)
        {
            leitourgia = "ΚΛΕΙΣΤΟ"
        }

        if (leitourgia=="ΚΛΕΙΣΤΟ")
        {
            leitourgia1.setBackgroundColor(Color.rgb(236,160,160))
        }
        else
        {
            leitourgia1.setBackgroundColor(Color.rgb(198,233,158))
        }

        textViewWrario.text="Ώρες Λειτουργίας:" +wrario + " & " +meros1a+":"+meros1b+"-"+meros2a +":"+meros2b
        if (meros1.toInt()==0 )
        {
            textViewWrario.text="Ώρες Λειτουργίας:" +wrario
        }
        leitourgia1.text=leitourgia
        var la = distance(kpharmacy.platos,kpharmacy.mhkos,pla,mka)
        apostash.text="Χλμ:"+ la.toString()

        var ef_meros1= kpharmacy.hmeres_dianuktereuseis.take(4)
        var ef_meros2= kpharmacy.hmeres_dianuktereuseis.takeLast(4)
        if (ef_meros1=="null")
        {
            ef_meros1="0000"
            ef_meros2="0000"
        }
        var efi_1= ef_meros1.take(2)
        var efi_2= ef_meros1.takeLast(2)
        var efi_3= ef_meros2.take(2)
        var efi_4= ef_meros2.takeLast(2)

        if (efi_1.toInt() == dayMonth||efi_2.toInt() == dayMonth||efi_3.toInt() == dayMonth||efi_4.toInt() == dayMonth) {

            textViewWrario.text = "Ώρες Λειτουργίας: 08:00-14:30 & 17:30-08:00 ΕΠΟΜΕΝΗΣ"
            leitourgia1.text = "ΕΦΗΜΕΡΕΥΕΙ"
            leitourgia1.setBackgroundColor(Color.rgb(198, 233, 158))
            if ((kpharmacy.perioxh == "Κοζάνης" || kpharmacy.perioxh == "Πτολεμαΐδα") && day == 7) {
                textViewWrario.text = "Ώρες Λειτουργίας: 17:30-08:00 ΕΠΟΜΕΝΗΣ"
            }
            if (kpharmacy.perioxh == "Βέροια") {
                textViewWrario.text = "Ώρες Λειτουργίας: 08:30-14:30 & 19:00-08:00 ΕΠΟΜΕΝΗΣ"
            }
        }

        ef_meros1= kpharmacy.hmeres_dihmeureuseis.take(4)
        ef_meros2= kpharmacy.hmeres_dihmeureuseis.takeLast(4)
        if (ef_meros1=="null")
        {
            ef_meros1="0000"
            ef_meros2="0000"
        }
        efi_1= ef_meros1.take(2)
        efi_2= ef_meros1.takeLast(2)
        efi_3= ef_meros2.take(2)
        efi_4= ef_meros2.takeLast(2)

        if (efi_1.toInt() == dayMonth||efi_2.toInt() == dayMonth||efi_3.toInt() == dayMonth||efi_4.toInt() == dayMonth) {
            textViewWrario.text = "Ώρες Λειτουργίας: 08:00-21:00"
            leitourgia1.text = "ΔΙΗΜΕΡΕΥΕΙ"
            leitourgia1.setBackgroundColor(Color.rgb(198, 233, 158))
            if (kpharmacy.perioxh == "Βέροια" && (day == 3 || day == 5 || day == 6)) {
                textViewWrario.text = "Ώρες Λειτουργίας: 08:00-17:30"
            }
            if (kpharmacy.perioxh == "Βέροια" && (day == 7 || day == 1)) {
                textViewWrario.text = "Ώρες Λειτουργίας: 08:00-14:30"
            }

        }

        textViewName.text = kpharmacy.onoma
        textViewNomos.text = kpharmacy.dieuthinsh+ ", "+kpharmacy.perioxh +", "+kpharmacy.t_k
        textViewThlefono.text=kpharmacy.thlefono

            // koumpi gia na mporeis na pareis thlefono
        textViewThlefono.setOnClickListener{

         val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${kpharmacy.thlefono}"))
            context.startActivity(intent)
        }

        directions.setOnClickListener{
           val url =
                 "https://www.google.com/maps/dir/?api=1&destination=" + kpharmacy.platos + "," + kpharmacy.mhkos + "&travelmode=driving"
           val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
        return listViewItem

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




