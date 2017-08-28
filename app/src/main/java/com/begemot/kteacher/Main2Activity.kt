package com.begemot.kteacher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main2.*
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.*


class Main2Activity : AppCompatActivity() {

    //lateinit var DB2 : myDB
    lateinit var DBH : DBHelp
    private val log = AnkoLogger("MYPOS")

    override fun onCreate(savedInstanceState: Bundle?) {
        log.debug("creatingMainAcctivity2")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
      //  DB2=myDB.getInstance(this)
        DBH=DBHelp.getInstance(this)

    }

    fun onClickCancel(view: View){
        finish()
    }

    fun onClickAddLesson(view:View){
//        parent.myList.adapter.not
//        parent.
//        toast("AddLesson myactivity2")
        log.warn("onclickaddlesson2")

        val p = DBH.addLessonToDB(editText.text.toString())

        var message = editText.text.toString()
       // toast(message)
        val intentMessage = Intent()
        message=p.nameLeson
        // put the message to return as result in Intent
        intentMessage.putExtra("MESSAGE", message)
        // Set The Result in Intent
        setResult(2, intentMessage)
        finish()

    }
}
