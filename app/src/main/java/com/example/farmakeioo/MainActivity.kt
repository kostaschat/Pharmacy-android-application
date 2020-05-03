package com.example.farmakeioo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import com.example.farmakeioo.PharmacyList.Companion.pla
import com.example.farmakeioo.PharmacyList.Companion.mka
import com.example.farmakeioo.ActivityHospitals.Companion.platos_hospitla
import com.example.farmakeioo.ActivityHospitals.Companion.mhkos_hospital

class MainActivity : AppCompatActivity() {

    val  PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

         // sundesh ths arxikhs selidas me tis nees acivities

        hospital.setOnClickListener{
            val intent= Intent(this,ActivityHospitals::class.java)
            startActivity(intent)
        }
        pharmacy.setOnClickListener{
            val intent= Intent(this,ViewPharmacyActivity::class.java)
            startActivity(intent)
        }

        articles.setOnClickListener{
            val intent= Intent(this,ActivityArticles::class.java)
            startActivity(intent)
        }

        scanner.setOnClickListener{
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }
    }
                         //barcode scanner
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode== Activity.RESULT_OK)
        {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    val url1 = "https://www.galinos.gr/web/drugs/main/packages/"
                 //   val url1 = "https://www.google.com/m/products?q="
                  //  val url2 ="&source=zxing&fbclid=IwAR2T2K-yFv_EG7XbQJCL1g0LT0s6hTaf5SRsMSg5ehLmjiTte4hWTF5l0fI"
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url1+result.getContents())))

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
    fun pro1(glo1:Double)
    {
        pla=glo1 //epistrefei thn timh tou geografikou platous sto opoio einai o xrhsths
        platos_hospitla=glo1// epistrefei to platos gia na upologistei h apostash tou nosokomeiou
    }
    fun pro2(glo2:Double)
    {
        mka=glo2//epistrefei thn timh tou geografikou mhkos sto opoio einai o xrhsths
        mhkos_hospital=glo2// epistrefei to mhkos gia na upologistei h apostash tou nosokomeiou
    }
    fun getLastLocation() { // briskei thn topothesia tou xrhsth

        if (checkPermissions()) {
            Log.d("checkPermissions",checkPermissions().toString())
            if (isLocationEnabled()) {
                Log.d("isLocationEnabled",isLocationEnabled().toString())
                mFusedLocationClient.lastLocation.addOnCompleteListener(this){ task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                        Log.d("requestNewLocationData",requestNewLocationData().toString())
                    } else {
                        var   globalVar1=location.latitude
                        var   globalVar2=location.longitude

                        pro1(globalVar1)
                        pro2(globalVar2)
                    }
                }
            } else
            {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else
        {
            requestPermissions()
            Log.d("requestPermissions",requestPermissions().toString())
        }
    }


    private fun requestNewLocationData() {

        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            var   globalVar1= mLastLocation.latitude
            var   globalVar2=mLastLocation.longitude
            pro1(globalVar1)
            pro2(globalVar2)


        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

}

