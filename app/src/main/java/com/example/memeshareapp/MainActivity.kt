package com.example.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var CurrentImageurl1 : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }
     private fun loadMeme()
    {
        // Instantiate the RequestQueue.
        progressbar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val CurrentImageurl1 =  response.getString("url")
                Glide.with(this).load(CurrentImageurl1).listener(object: RequestListener<Drawable>
                {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }

                }).into(memeimageView)
            },
            Response.ErrorListener {  })

// Add the request to the RequestQueue.
        queue.add(JsonObjectRequest)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND )
        intent.type  = "text / plain "
        intent.putExtra(Intent.EXTRA_TEXT , "Hey got from Reddit $CurrentImageurl1 ")
        val chooser = Intent.createChooser(intent, "Share this meme using.....")
        startActivity(chooser)
    }
}