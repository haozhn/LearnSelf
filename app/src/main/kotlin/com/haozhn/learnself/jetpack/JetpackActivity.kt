package com.haozhn.learnself.jetpack

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.hao.learnself.R
import com.example.hao.learnself.Util
import kotlin.random.Random

class JetpackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)

        val model = ViewModelProviders.of(this).get(MyViewModel::class.java)

        val tv1 = findViewById<TextView>(R.id.tv_1)
        model.getUsers().observe(this, Observer {
            tv1.text = "name = ${it.name}, age = ${it.age}"
        })

        tv1.setOnClickListener {
            val random = Random.Default.nextInt(1000)
            model.getUsers().value = User("name$random", random)
        }

        model.getUsers().value = User("name1", 19)

        lifecycle.addObserver(MyObserver())
        Log.e(Util.TAG, "Activity is onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.e(Util.TAG, "Activity is onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(Util.TAG, "Activity is onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(Util.TAG, "Activity is onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(Util.TAG, "Activity is onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(Util.TAG, "Activity is onDestroy")
    }
}