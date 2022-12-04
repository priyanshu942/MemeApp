package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var flag:Int=1
    var temp:String?=null
    var imgurl:String?=null
    var imgurl1:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme(flag)
    }

    private fun loadmeme(flag:Int):Unit {
        //val textView = findViewById<TextView>(R.id.text)
// ...


// Instantiate the RequestQueue.
        progressBar.visibility=View.VISIBLE

        val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,

            { response ->
                // Display the first 500 characters of the response string.
                 //temp=imgurl
                //println("$temp")
                imgurl1 = response.getString("url")




                if(flag==1)
                {
                    temp=imgurl
                    imgurl=imgurl1


                }

                else if(flag==0)
                {
                    imgurl=temp
                }


                Glide.with(this).load(imgurl).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }


                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }


                }).into(memeimage)


            },
            {
            Toast.makeText(this,"some error occured",Toast.LENGTH_LONG).show()
            //here we get error
            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun next(view: View) {
        loadmeme(1)
    }

    fun share(view: View) {
   val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Here is the cool meme I got from redit $imgurl")
    val choser=Intent.createChooser(intent,"Shaare this meme...")
        startActivity(choser)
    }

    fun previous(view: View) {
            loadmeme(0)
    }
}