package com.example.rxrepositorytutorial


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rxrepositorytutorial.user.view.UsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, UsersFragment()).commit()
        }
    }
}
