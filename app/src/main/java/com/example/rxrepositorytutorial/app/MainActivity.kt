package com.example.rxrepositorytutorial.app


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rxrepositorytutorial.R
import com.example.rxrepositorytutorial.app.screens.users_list.view.UsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.frag_container,
                    UsersFragment()
                ).commit()
        }
    }
}
