package ipvc.estg.smartcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.smartcity.api.EndPoints
import ipvc.estg.smartcity.api.Pontos
import ipvc.estg.smartcity.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_mapa.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Mapa : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var preferences: SharedPreferences
    private lateinit var Pontos: List<Pontos>
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var locAdd: LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try { this.supportActionBar!!.hide()} catch (e: NullPointerException) {}
        setContentView(R.layout.activity_mapa)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Iniciar FusedLocation
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        carregar_pontos(null,null)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                locAdd = loc
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 9.0f))
                //Mostra as coordenadas periodicamente
                Log.d("Coords",loc.latitude.toString() + " - " + loc.longitude.toString())
            }
        }
        createLocationRequest()


        bottomBarMap.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout-> {
                    preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.clear()
                    editor.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }


        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontos()
        var position: LatLng

        preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
        val idPref = preferences.getInt("ID", 0)
        Log.d("userId", idPref.toString())





       val buttonfab = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabMapa)
        buttonfab.setOnClickListener{
            val intent = Intent(this, addponto::class.java)
            intent.putExtra("LocLat", locAdd.latitude)
            intent.putExtra("LocLon", locAdd.longitude)
            startActivity(intent)
        }
    }


    fun carregar_pontos(tipo: String?, distance: Int?) {

        //Listar TUDO
        val request = ServiceBuilder.buildService(EndPoints::class.java)

        var call: Call<List<Pontos>>
        if( tipo != null ) call = request.getPontosTipo(tipo)
        call = request.getPontos()

        var position: LatLng

        call.enqueue(object : Callback<List<Pontos>> {

            override fun onResponse(call: Call<List<Pontos>>, response: Response<List<Pontos>>) {

                if (response.isSuccessful){

                    Pontos = response.body()!!

                    if (Pontos != null) {

                        for (Ponto in Pontos) {

                            position = LatLng(Ponto.lat.toDouble(), Ponto.lon.toDouble())

                            val distanceOf = 100

                            preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
                            val idPref = preferences.getInt("ID", 0)

                            if (distance != null) {
                                if (distanceOf < distance) {
                                    if (Ponto.user_id.equals(idPref)) {
                                        val marker = mMap.addMarker(MarkerOptions().position(position).title(Ponto.titulo).icon(
                                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)))
                                        marker.tag = Ponto.id
                                    } else {
                                        val marker = mMap.addMarker(MarkerOptions().position(position).title(Ponto.titulo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                                        marker.tag = Ponto.id
                                    }
                                }
                            } else {
                                if (Ponto.user_id.equals(idPref)) {
                                    val marker = mMap.addMarker(MarkerOptions().position(position).title(Ponto.titulo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN  )))
                                    marker.tag = Ponto.id

                                } else {
                                    val marker = mMap.addMarker(MarkerOptions().position(position).title(Ponto.titulo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                                    marker.tag = Ponto.id
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Pontos>>, t: Throwable) {
                Toast.makeText(this@Mapa, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        // add to implement last known location
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        //added to implement location periodic updates
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    //added to implement location periodic updates
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }


    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 5000 //Rate de 5 segundos
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        mMap.setOnInfoWindowClickListener( object: GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(m: Marker) {

                val intent = Intent(this@Mapa, markerdescri::class.java)

                //Buscar valor do ID do marker
                val id: Int

                //Valor do ID
                id = m.tag as Int
                intent.putExtra("idDoMarker",id)
                startActivity(intent)
                finish()
            }
        })

    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("Valor:", "Pause")
    }

    //Resumir a receção de coordenadas
    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("Valor:", "Resume")
    }

}