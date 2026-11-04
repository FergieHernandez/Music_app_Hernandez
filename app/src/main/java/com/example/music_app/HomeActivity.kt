package com.example.music_app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.music_app.model.Album
import com.example.music_app.model.ITunesResponse
import com.example.music_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var tvEstado : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvEstado = findViewById(R.id.tvEstado)

        obtenerAlbum()

    }

    // MÉTODO PARA OBTENER LA RESPUESTA DEL API
    private fun obtenerAlbum(){

        RetrofitClient.api.obtenerAlbumGenero(

            genero = "kpop",
            pais = "MX",
            media = "music",
            entidad = "album",
            limite = 1

        ).enqueue(object : Callback<ITunesResponse>{

            // MÉTODO EN CASO DE OBTENER UNA RESPUESTA EXITOSA
            override fun onResponse(call: Call<ITunesResponse?>, response: Response<ITunesResponse?>) {

                // Variable que obtenga toda la respuesta del API
                val albumes = response.body()?.results ?: emptyList()

                if(albumes.isNotEmpty()){
                    mostrarAlbumes(albumes)

                }else{
                    Toast.makeText(this@HomeActivity, "No hay resultados", Toast.LENGTH_SHORT).show()
                }

            }

            // MÉTODO EN CASO DE UNA RESPUESTA FALLIDA
            override fun onFailure(call: Call<ITunesResponse?>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Error en el API", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun mostrarAlbumes( albumes : List<Album>){
        // eliminar los elementos duplicados
        contenedorIndie.removeAllviews()

        // ciclo que recorre los resultados del album
        for(album in albumes){

            val vistaAlbum = layoutInflater.inflate(
                R.layout.item_album,
                contenedorIndie,
                false
            )

            //Obtener la informacion del API
            val imgPortada = vistaAlbum.findViewById<>(R.id.imgPortadaAlbum)
            val tvNombreAlbum = vistaAlbum.findViewById<>(R.id.tvNombreAlbum)
            val tvNombreArtista = vistaAlbum.findViewById<>(R.id.tvNombreArtista)

            tvNombreAlbum.text = album.collectionName ?: "Album sin  nombre"
            tvNombreArtista.text = album.artistName ?: "Artista desconocido"

            Glide.with(this)
                .load(album.obtenerImagenGrande())
                .into(imgPortada)

            contenedorIndie.addview(vistaAlbum)

        }
    }

}