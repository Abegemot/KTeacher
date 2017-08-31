package com.begemot.kteacher

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main3.*
import org.jetbrains.anko.*


class   Main3Activity : AppCompatActivity() {
    private val log = AnkoLogger("MYPOS")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setSupportActionBar(toolbar)
        log.warn("Main3Activity constructor")
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        if (savedInstanceState == null) {
            log.warn("Main3Activity begintransaction")
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(Main3ActivityFragment(),"fragment_main3")
                    //  .add(R.layout.activity_main3, Main3ActivityFragment(), "fragment_main3")
                    .commit();
            log.warn("Main3Activity endtransaction")
        }
    }

}
