package com.temicanada.tdsb

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robotemi.sdk.NlpResult
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnRobotReadyListener
import com.temicanada.tdsb.adapter.CompanyAdapter
import com.temicanada.tdsb.model.Company

class MainActivity : AppCompatActivity(), Robot.NlpListener, OnRobotReadyListener {

    var companyList: ArrayList<Company> = ArrayList()
    val ACTION_OPEN_LAUNCHER = "home.launcher"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv_company: RecyclerView = findViewById(R.id.recyclerView)

        companyList.add(
            Company(
                "Euler 4.0",
                R.drawable.euler,
                "",
                ""
            )
        )

        companyList.add(
            Company(
                "Guessing Game",
                R.drawable.guessing_game,
                "",
                "walidsodki.com.charrado"
            )
        )
        companyList.add(
            Company(
                "Tic Tac Toe",
                R.drawable.tic_tac_toe,
                "",
                "br.com.odawara.FlutterTicTacToe"
            )
        )
        companyList.add(
            Company(
                "Temi Vacuum",
                R.drawable.temi_vacuum,
                "Sick Kids Hospital",
                "com.example.temivaccum"
            )
        )
//        companyList.add(
//            Company(
//                "Catch Me",
//                R.drawable.catch_me,
//                "",
//                "com.example.catchme"
//            )
//        )

        companyList.add(
            Company(
                "Ask Wiki",
                R.drawable.askwiki,
                "",
                "com.robocore.askwiki"
            )
        )

        companyList.add(
            Company(
                "YouTube Kids",
                R.drawable.youtube_kids,
                "",
                "com.google.android.apps.youtube.kids"
            )
        )

        companyList.add(
            Company(
                "Swiss Chalet Presentation",
                R.drawable.swiss_chalet_logo,
                "",
                "com.temicanada.swisschalet"
            )
        )

        rv_company.layoutManager = GridLayoutManager(this, 3)
        // Access the RecyclerView Adapter and load the data into it
        rv_company.adapter = CompanyAdapter(companyList, this)


    }

    override fun onStart() {
        super.onStart()
        Robot.getInstance().addOnRobotReadyListener(this);
        Robot.getInstance().addNlpListener(this);
    }

    override fun onStop() {
        super.onStop()
        Robot.getInstance().removeOnRobotReadyListener(this)
        Robot.getInstance().removeNlpListener(this)
    }

    override fun onNlpCompleted(nlpResult: NlpResult) {
        when (nlpResult.action) {
            ACTION_OPEN_LAUNCHER -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    override fun onRobotReady(isReady: Boolean) {
        if (isReady) {
            try {
                val activityInfo =
                    packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
                Robot.getInstance().onStart(activityInfo)
            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException(e)
            }

            Robot.getInstance().hideTopBar()
        }
    }
}