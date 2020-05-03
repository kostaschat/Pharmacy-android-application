package com.example.farmakeioo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_view_pharmacy.*
import java.util.*


class ViewPharmacyActivity :AppCompatActivity(){
    private var listView: ListView? = null
    private var pharmacyList: MutableList<Pharmacy>? = null
    lateinit var option: Spinner
    lateinit var ka : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pharmacy)
        listView = findViewById(R.id.listViewPharmacy) as ListView
        option = findViewById(R.id.sp_option) as Spinner
        pharmacyList = mutableListOf()
        option = findViewById(R.id.sp_option) as Spinner

        swipe_container.setOnRefreshListener {
            loadPharmacy()
            swipe_container.isRefreshing = false
            swipe_container.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
            swipe_container.setColorSchemeColors(Color.WHITE)

        }

        val options = arrayOf("Διάλεξε τοποθεσία ","Βέροια","Καστοριά","Κοζάνης","Νάουσα","Πτολεμαΐδα")
        option.adapter = ArrayAdapter<String>(this,android.R.layout.select_dialog_item,options)
        option.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ka =options.get(position)

                loadPharmacy()

            }
        }

    }

    private fun loadPharmacy() {
        pharmacyList!!.clear()
        val stringRequest =object :StringRequest(Method.POST, EndPoints.URL_GET_PHARMACY,Response.Listener<String> { s ->
            try {
                val obj = JSONObject(s)


                val array = obj.getJSONArray("pharmacy")

                for (i in 0..array.length() - 1) {
                    val objectArtist = array.getJSONObject(i)
                    val ph = Pharmacy(
                        objectArtist.getString("onoma"),
                        objectArtist.getString("perioxh"),
                        objectArtist.getString("dieuthinsh"),
                        objectArtist.getInt("t_k"),
                        objectArtist.getString("thlefono"),
                        objectArtist.getDouble("platos"),
                        objectArtist.getDouble("mhkos"),
                        objectArtist.getString("extraweres"),
                        objectArtist.getString("hmeres_dianuktereuseis"),
                        objectArtist.getString("hmeres_dihmeureuseis")

                    )
                    pharmacyList!!.add(ph)
                    val adapter = PharmacyList(this@ViewPharmacyActivity, pharmacyList!!)
                    listView!!.adapter = adapter
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })

        {
            override fun getParams(): Map<String, String> {
                var day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                val params = HashMap<String, String>()
                params.put("ka1", ka)
                params.put("day", day.toString())
                return params
            }
        }
        val  requesQueue = Volley.newRequestQueue(this)
        requesQueue.add<String>(stringRequest)
    }
}






















