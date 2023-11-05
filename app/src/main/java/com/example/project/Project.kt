package com.example.project

import android.app.Application
import com.google.firebase.FirebaseApp

class Project : Application() {

    override fun onCreate(){
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}