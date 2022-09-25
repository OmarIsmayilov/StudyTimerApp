package com.omarismayilov.pomodorotimer

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var runnable = Runnable(){}
    var handler = Handler(Looper.getMainLooper())
    lateinit var sharedPreferences : SharedPreferences
    var time:Long= 0
    var savedTime:Long= 0
    var base = savedTime

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = this.getSharedPreferences("com.omarismayilov.pomodorotimer",
            MODE_PRIVATE)

        textView2.text = "Total : ${sharedPreferences.getLong("base",0)} saniye"
        savedTime = sharedPreferences.getLong("base",0)
    }



    fun start(view: View){
        runnable = object :Runnable{
            @SuppressLint("SetTextI18n")
            override fun run() {
                time+=1
                textView.text = "Time : $time"
                handler.postDelayed(runnable,1000)
            }
        }
        handler.post(runnable)

    }

    fun stop(view: View){
        handler.removeCallbacks(runnable)
    }


    @SuppressLint("SetTextI18n")
    fun save(view: View){
        sharedPreferences.edit().putLong("time",time).apply()
        savedTime += sharedPreferences.getLong("time",0)
        textView2.text = "All : ${savedTime} saniye"

        Toast.makeText(applicationContext,"Saving...",Toast.LENGTH_SHORT).show()
        base = savedTime
        sharedPreferences.edit().putLong("base",savedTime).apply()
        time = 0
        textView.text = "Time : 0"
    }

    @SuppressLint("SetTextI18n")
    fun reset(view: View){
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Restart")
        alert.setMessage("Are you sure ? ")
        alert.setPositiveButton("Yes"){dialog,which ->
            time = 0
            savedTime = 0
            base = 0
            handler.removeCallbacks(runnable)
            textView.text = "Time : 0"
            textView2.text = "All : 0"
            Toast.makeText(applicationContext,"Everything cleaned",Toast.LENGTH_SHORT).show()
        }
        alert.setNegativeButton("No"){dialog,which ->}

        alert.show()
    }


}