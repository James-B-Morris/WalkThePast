package com.example.walkthepast

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    // Map Object
    private lateinit var mMap: GoogleMap
    // gives access to the device's location
    private lateinit var mFusedLocationClient: FusedLocationProviderClient 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        initToolbar()
        initMap()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.mapToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val db = Firebase.firestore
        var location : GeoPoint
        var latLong : LatLng
        var title : String
        db.collection(getString(R.string.path_points_of_interest))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(getString(R.string.log_map),
                        "${document.id} => ${document.get("location")}")

                    location = (document.get("location") as GeoPoint)
                    latLong = LatLng(location.latitude, location.longitude)
                    title = (document.get("title") as String)
                    mMap.addMarker(MarkerOptions().position(latLong).title(title))
                }
                Log.d(getString(R.string.log_points_of_interest), "hi")
            }
            .addOnFailureListener { e ->
                Log.w(getString(R.string.log_points_of_interest),
                    getString(R.string.log_document_error))
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val view = findViewById<View>(R.id.mapToolBar)

        when (item.itemId) {
            R.id.refresh -> {
                val sb = Snackbar.make(view, getString(R.string.xml_refresh), Snackbar.LENGTH_LONG)
                sb.show()
                return true
            }
            R.id.action_logout -> {
                val snackbar =
                    Snackbar.make(view, getString(R.string.xml_logout), Snackbar.LENGTH_LONG)
                snackbar.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // CREATES A NEW MARKER POINT WHERE EVER THE USER TAPS ON THE MAP
        // mMap.setOnMapClickListener { point ->
        //    val marker = MarkerOptions().position(point).title("NewMarkerPoint")
        //    mMap.addMarker(marker)
        //}

        //handle home FAB
        val fabHome: View = findViewById(R.id.homeFAB)
        fabHome.setOnClickListener { view ->
            toCoFo()
        }

        //handle my location FAB
        val fabMy: View = findViewById(R.id.locFab)
        fabMy.setOnClickListener { view ->
            getLastLocation()
        }
    }
    
    private fun toCoFo() {
        // to get new location pull out of Google Maps URL or right click the point
        val coFo = LatLng(51.619543, -3.878634)
        mMap.addMarker(MarkerOptions().position(coFo).title("Computational Foundary"))
        //moves the camera to the specified location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coFo))
        // zoom levels 1--20 as float
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18F))
    }
    
    private fun getLastLocation() {
        if(isLocationEnabled()) {
            //checking location permission
            if (ActivityCompat.checkSelfPermission(this, 
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                //request permission
                ActivityCompat.requestPermissions(this, 
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 42)
                return
            }
            
            //once the last location is acquired
            mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task -> 
                val location: Location? = task.result
                if (location == null) {
                    //if it couldn't be aquired, get some new location data
                    requestNewLocationData()
                }
                else {
                    val lat = location.latitude
                    val long = location.longitude
                    
                    Log.i("LocLatLocation", "$lat and $long")
                    
                    val lastLoc = LatLng(lat, long)
                    
                    //update camera
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(lastLoc))
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15F))
                    mMap.addMarker(MarkerOptions().position(lastLoc).title("Current Location"))
                }
            }
            // couldn't get location, so go to Settings (may be deprecated)
        } else {
            val mRootView = findViewById<View>(R.id.map)
            val locSnack = Snackbar.make(mRootView, "R.string.location_switch", 
                Snackbar.LENGTH_LONG)
            locSnack.show()
            
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }
    
    // Request a new location
    private fun requestNewLocationData() {
        //parameters for location
        val mLocationRequest = com.google.android.gms.location.LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime= 100
        }
        
        //checking location permission
        if (ActivityCompat.checkSelfPermission(this, 
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request permission
            ActivityCompat.requestPermissions(this, 
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 42)
            return
        }
        
        //update the location client
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //add a callback so that the location is repeatedly updated according to paremeters
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, 
            mLocationCallback, Looper.myLooper()!!
        )
    }
    
    //callback for repeatedly getting location
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            val lat = mLastLocation.latitude
            val long = mLastLocation.longitude
            
            val lastLoc = LatLng(lat, long)
            Log.i("LocLatLocationCallback", "$lat and $long")
        }
    }
    
    // check whether location is enabled
    private fun isLocationEnabled():Boolean {
        val locationManager: LocationManager = 
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}