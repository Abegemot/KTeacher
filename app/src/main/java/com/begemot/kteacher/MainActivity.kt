package com.begemot.kteacher

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.widget.AdapterView.OnItemClickListener
import android.content.Intent


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.widget.*
import org.jetbrains.anko.*


class MainActivity : AppCompatActivity() {

    var lesonList = ArrayList<KLesson>()
    lateinit var myListAdapter:ArrayAdapter<KLesson>


    private val log = AnkoLogger("MYPOS")
    lateinit var DBH : DBHelp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        myListAdapter =  ArrayAdapter(this,android.R.layout.simple_list_item_1,lesonList)

        myList.adapter=myListAdapter
        myList.onItemClickListener = object : OnItemClickListener {override fun onItemClick
                (parent: AdapterView<*>, view: View, position: Int, id: Long) {
                log.warn("onItemClickListener position = $position    id= $id")
        } }

        DBH=DBHelp.getInstance(this)
        loadLessons()



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun deleteLessonClick(view: View){
        log.warn ("deleteLessonClick")
        DBH.DeleteLeson()
        lesonList.clear()
    }

    fun editLessonClick(view: View){
        log.warn ("editLessonClick")
    }

    fun addLessonClick(view: View){
        log.warn("addlessonclick")
        val intento1 = Intent(this, Main2Activity::class.java)
        startActivityForResult(intento1,2 )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //toast("activity result  resultCode $resultCode")
        if (resultCode === 2) {
            // fetch the message String
            val message = data?.getStringExtra("MESSAGE")
            // Set the message string in textView
           // toast(message)
            lesonList.add(KLesson(message!!,33))
            myListAdapter.notifyDataSetChanged()
           //   toast("XXXXXactivity result  Code : $message")
        }




    }

    fun loadLessons(){

        log.warn ("loadLessons")
        var a:List<KLesson> = DBH.Test()
        for(item in a) {
            lesonList.add(item)
        }
        //
    }
}
