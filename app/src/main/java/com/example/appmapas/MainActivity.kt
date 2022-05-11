package com.example.appmapas

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() , OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener  {
    private lateinit var map:GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION=0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createFragment()
    }

    // para saber donde estoy yo
    private fun enableLocation(){
        if(!::map.isInitialized)return
        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled=true
        } else {
            requestLocationPermission()
        }
    }

    private fun createFragment(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)


        //tratar de acceder a la ubicación del gps
        enableLocation()
        findViewById<ImageView>(R.id.ivRestaurante1).setOnClickListener{
           // Toast.makeText(this,"Fishers", Toast.LENGTH_SHORT).show()
            crearMrker(19.485922146231005, -99.24314752694443,"Italiannis")
        }

        findViewById<ImageView>(R.id.ivRestaurante2).setOnClickListener{
            //Toast.makeText(this,"Pampas", Toast.LENGTH_SHORT).show()
            crearMrker(19.373059125228643, -99.17942579871797,"Cardenal")
        }

        findViewById<ImageView>(R.id.ivRestaurante3).setOnClickListener{
            //Toast.makeText(this,"Panama", Toast.LENGTH_SHORT).show()
            crearMrker(24.762019756180823, -107.45471285749157,"El jacal de San Antonio")
        }

        findViewById<ImageView>(R.id.ivRestaurante4).setOnClickListener{
            // Toast.makeText(this,"Santino", Toast.LENGTH_SHORT).show()
            crearMrker(19.415996104402236, -99.16974259954642,"RosaNegra")
        }
    }

    private fun crearMrker(lat: Double, lon: Double, txtUbicacion: String){
        val coordenadas = LatLng(lat,lon )
        val marker = MarkerOptions().position(coordenadas).title(txtUbicacion)
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas,18f),
            4000,null
        )

    }

    //para determinar si el usuario ya ha garantizado el permiso de ubicación
    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    //para solicitar al usuario el permiso de ubicación
    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            //mostrar la ventana de permiso
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled=true
            } else {
                Toast.makeText(this,"Para activar permisos, ve a ajustes y acepta los permisos", Toast.LENGTH_LONG).show()
            }

            else -> {}
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Ey!!!!!!", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this,"located here : latitud ${p0.latitude} and longitud ${p0.longitude} ", Toast.LENGTH_SHORT).show()
    }
}