package com.example.segundoparcial_creadordeperfiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var tv_name : TextView
    private lateinit var tv_lastname : TextView
    private lateinit var tv_age : TextView
    private lateinit var tv_nat : TextView
    private lateinit var tv_street : TextView
    private lateinit var tv_city : TextView
    private lateinit var tv_email: TextView
    private lateinit var tv_phone: TextView
    private lateinit var imageView: ImageView
    private lateinit var btnRefresh : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tv_name = findViewById(R.id.textViewName)
        tv_lastname = findViewById(R.id.textViewLastName)
        tv_age = findViewById(R.id.textViewAge)
        tv_nat = findViewById(R.id.textViewNat)
        tv_street = findViewById(R.id.textViewStreet)
        tv_city = findViewById(R.id.textViewCity)
        tv_email = findViewById(R.id.textViewEmail)
        tv_phone = findViewById(R.id.textViewPhone)
        imageView = findViewById(R.id.imageView)
        btnRefresh = findViewById(R.id.buttonRefresh)

        getUserDate()

        btnRefresh.setOnClickListener{
            getUserDate()
        }


    }

    private fun getUserDate(){
        CoroutineScope(Dispatchers.IO)
            .launch {
                val call = getRetrofit().create(ApiService::class.java).getRandomUser("api/")


                //gestiono los datos que recibo
                val response = call.body()

                runOnUiThread {
                    if(call.isSuccessful){
                        val user = response?.results?.get(0)

                        showUserData(user)

                    }

                }

            }

    }


    private fun showUserData(user: User?){
        if(user!= null){
            val name = "Name: " + user.name.first
            val lastname = "Last name: " + user.name.last
            val age = "Age: " + user.dob.age
            val nat = "Nationality: " + user.nat
            val street = "Street: " + user.location.street.name + " " + user.location.street.number
            val city = "City: " + user.location.city
            val email = "Email: " + user.email
            val phone = "Phone: " + user.phone


            tv_name.text = name
            tv_lastname.text = lastname
            tv_age.text = age
            tv_nat.text = nat
            tv_street.text = street
            tv_city.text = city
            tv_email.text = email
            tv_phone.text = phone

            Glide.with(this)
                .load(user.picture.large)
                .into(imageView)
        }
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }
}