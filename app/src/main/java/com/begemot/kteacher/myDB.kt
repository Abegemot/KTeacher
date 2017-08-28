/**
 * Created by Dad on 26-Aug-17.
 */
package com.begemot.kteacher

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.*
import org.jetbrains.anko.warn


class myDB(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
    companion object {
        private var instance: myDB? = null
        private val log = AnkoLogger("MYPOS")
        @Synchronized
        fun getInstance(ctx: Context): myDB {

            if (instance == null) {
                instance = myDB(ctx.getApplicationContext())

                log.warn("myDB create instance")
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables

        log.warn("myDB.OnCreateDB")

        db.createTable("PROBA", true, Pair("PEPE", TEXT), Pair("ID", INTEGER))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        log.warn("JSJSJSJSJSJ  OnUpgradeDB")

        //db.dropTable("User", true)
    }

    //fun getUsers(db: ManagedSQLiteOpenHelper): List = db.use {
    //    db.select("Users")
    //            .whereSimple("family_name = ?", "John")
    //            .doExec()
    //            .parseList(UserParser)
    //}

}

// Access property for Context
val Context.database: myDB
    get() = myDB.getInstance(getApplicationContext())


class DBHelp(ctx: Context) {
    companion object {
        private var DB2: myDB? = null
        private var instance: DBHelp? = null
        private val log = AnkoLogger("MYPOS")
        private var isOpen: Boolean = false

        @Synchronized
        fun getInstance(ctx: Context): DBHelp {
            if (instance == null) {
                instance = DBHelp(ctx.getApplicationContext())
                DB2 = myDB.getInstance(ctx)
                log.warn("DBHelp create instance")
            }
            return instance!!
        }
    }

    fun CreateDB() {
        log.warn("CreateDB")
        try {
            DB2!!.use { createTable("LESSONS", false, "ID" to INTEGER + PRIMARY_KEY, "NAME" to TEXT) }
            // DB2!!.use { insert("LESSONS","true", "NAME" to "kkkkk")}
            var ds: SQLiteDatabase = DB2!!.writableDatabase
            ds.insert("LESSONS", "NAME" to "1PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "2PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "3PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "4PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "5PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "6PRIMERA LLICO")

//var s1:String= ds.attachedDbs[0].first
//var s2:String= ds.attachedDbs[0].second
//log.warn("first = $s1  second=$s2")
//log.warn("CREATING AND INSERTING")
//log.warn("attached dbs size  = ${ds.attachedDbs.size}")
//log.warn("attached no se que = ${ds.attachedDbs[0].toString()}")


/*ds.createTable("PROBA",true,Pair("PEPE",TEXT),Pair("ID", INTEGER))
val nR:Long=ds.insert("PROBA","PEPE" to "ajsjsjs","ID" to "1")
ds.insert("PROBA","PEPE" to "bjsjsjs","ID" to "1")
ds.insert("PROBA","PEPE" to "cjsjsjs","ID" to "71")
ds.insert("PROBA","PEPE" to "djsjsjs","ID" to "1")*/


        } catch (e: Exception) {
// handler
            log.warn("CreateDB Exception   $e.message")
        } finally {
// optional finally block
        }

    }

    fun addLessonToDB(lesson: String): Klesson {
        log.warn("DBHelp addLessonToDB")
        //CreateDB()
        var nR: Long = 0
        var k: Klesson = Klesson("pepe", 10)

        try {
            var ds: SQLiteDatabase = DB2!!.writableDatabase
            nR = ds.insert("LESSONS", "NAME" to lesson)
            log.warn("Numero retornat per InsertLesson $nR")
            ds.close()
        } catch (e: Exception) {
// handler
            log.warn("Exception addLessonToDB  $e.message")
        } finally {
// optional finally block
        }

        k.idLeson = nR
        k.nameLeson = lesson

        return k
    }


    fun Test():List<Klesson> {
        //val l:Klesson
        log.warn { "TESTTTTTXXXXXXXXXXXXXXXXXXXXXXX" }
        var ds: SQLiteDatabase = DB2!!.writableDatabase
        val L2: List<Klesson>
        val rowParser = classParser<Klesson>()
        try {
            L2 = DB2!!.use {
                select("LESSONS", "NAME", "ID").exec { parseList(rowParser) }
            }
            log.warn("SIZE List2 ${L2.size}")
            for(item in L2) log.warn(item)
            return L2
        } catch (e: Exception) {
            log.warn("Test Exception reading table ${e.message}")
        } finally {
        }
        return  emptyList()
    }

    fun DeleteLeson(){
        var ds: SQLiteDatabase = DB2!!.writableDatabase
        try {
               ds.delete("LESSONS")

            }catch (e: Exception) {
            log.warn("Delete Exception reading table ${e.message}")
        } finally {
        }


    }


}







