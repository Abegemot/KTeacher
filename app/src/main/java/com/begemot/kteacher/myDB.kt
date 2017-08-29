/**
 * Created by Dad on 26-Aug-17.
 */
package com.begemot.kteacher

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.*
import org.jetbrains.anko.warn
import java.util.function.Predicate


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
        private lateinit var DB2: myDB
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

    fun CreateLessons() {

        log.warn("CreateLessons")
        try {
            DB2!!.use { createTable("LESSONS", true, "ID" to INTEGER + PRIMARY_KEY, "NAME" to TEXT) }
            // DB2!!.use { insert("LESSONS","true", "NAME" to "kkkkk")}
            var ds: SQLiteDatabase = DB2!!.writableDatabase
            ds.insert("LESSONS", "NAME" to "1PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "2PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "3PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "4PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "5PRIMERA LLICO")
            ds.insert("LESSONS", "NAME" to "6PRIMERA LLICO")
        } catch (e: Exception) {
// handler
            log.warn("CreateLessons Exception   $e.message")
        } finally {
// optional finally block
        }

    }

    fun addLessonToDB(lesson: String): KLesson {
        log.warn("DBHelp addLessonToDB")
        var nR: Long = 0
        var k: KLesson = KLesson("pepe", 10)

        try {
            var ds: SQLiteDatabase = DB2!!.writableDatabase
            nR = ds.insert("LESSONS", "NAME" to lesson)
            log.warn("Numero retornat per InsertLesson $nR")
            ds.close()
        } catch (e: Exception) {
// handler
            log.warn("Exception addLessonToDB  $e.message")
        } finally {
        }
        k.idLeson = nR
        k.nameLeson = lesson
        return k
    }


    fun LoadLessons():List<KLesson> {
        //val l:KLesson
        log.warn { "LoadLesons" }
        var ds: SQLiteDatabase = DB2!!.writableDatabase
        val L2: List<KLesson>
        val rowParser = classParser<KLesson>()
        try {
            L2 = DB2!!.use {
                select("LESSONS", "NAME", "ID").exec { parseList(rowParser) }
            }
            log.warn("SIZE Lessons: ${L2.size}")
            for(item in L2) log.warn(item)
            return L2
        } catch (e: Exception) {
            log.warn("LoadLessons Exception: ${e.message}")
        } finally {
        }
        return  emptyList()
    }

    fun DeleteLeson(){
        var ds: SQLiteDatabase = DB2!!.writableDatabase
        try {
               ds.delete("LESSONS")

            }catch (e: Exception) {
            log.warn("Delete Exception:  ${e.message}")
        } finally {
        }


    }

    fun DeleteKindOfExercises(){
        var ds: SQLiteDatabase = DB2!!.writableDatabase
        try {
            ds.delete("KINDOFEX")

        }catch (e: Exception) {
            log.warn("DeleteKindOfEx Exception:  ${e.message}")
        } finally {
        }


    }



    fun CreateKindOfExercises() {

        log.warn("CreateKindOfExcercises")
        try {
            DB2!!.use { createTable("KINDOFEX", true, "ID" to INTEGER + PRIMARY_KEY, "T1" to TEXT, "T2" to TEXT) }
            // DB2!!.use { insert("LESSONS","true", "NAME" to "kkkkk")}
            var ds: SQLiteDatabase = DB2!!.writableDatabase
            ds.insert("KINDOFEX", "T1" to "Select from a set of russian words to form a frase","T2" to "")
            ds.insert("KINDOFEX", "T1" to "Form par of words to find antonims" ,"T2" to "")
            ds.insert("KINDOFEX", "T1" to "who knows what","T2" to "")

        } catch (e: Exception) {
// handler
            log.warn("CreateKindOfExcercises   $e.message")
        } finally {
// optional finally block
        }

    }

    fun LoadKindOfExercises():List<KKindOfExercice> {
    //fun LoadKindOfExercises():List<String> {
        //val l:KLesson
        log.warn { "LoadKindOfExcercises" }
        var ds: SQLiteDatabase = DB2!!.writableDatabase
        val L2: List<KKindOfExercice>
//        val L2: List<String>
        val rowParser = classParser<KKindOfExercice>()
//        val rowParser = classParser<String>()
        try {
            log.warn("Entra")
            L2 = DB2!!.use {
                select("KINDOFEX", "ID", "T1","T2").exec { parseList(rowParser) }
                //select("KINDOFEX", "T1").exec { parseList(rowParser) }
            }

            log.warn("SIZE List KindOfLessons: ${L2.size}")
            for(item in L2) log.warn(item)
            return L2
        } catch (e: Exception) {
            log.warn("LoadKindOfExcercises Exception:  ${e.message}   ${e.toString()}  ${e.stackTrace.toString()}")
        } finally {
        }
        return  emptyList()
    }



    fun LoadExercisesOfALesson(lesonID: Long):List<KExercice> {
        log.warn { "LoadExcercisesOfALesson" }
        val L2: List<KExercice>
        val rowParser = classParser<KExercice>()
       // envelope() {
            try {
                log.warn("Entra")
                L2 = DB2.use {
                    select("KEXERCISE", "ID", "T1", "T2").exec { parseList(rowParser) }

                }
                log.warn("SIZE List OfExcecises Of Lesson:$lesonID =  ${L2.size}")
                for (item in L2) log.warn(item)
                return L2
            } catch (e: Exception) {
                log.warn("LoadExercicesOfALesson Exception:  ${e.message}   ${e.toString()}  ${e.stackTrace.toString()}")
            } finally {
            }
    //}
            return emptyList()

    }



    fun DeleteExerciseOfALeson(){

    }

    //fun CEA(lesonID:Long,exercise:KExercice)
    fun CEA(){
        val rowParser = classParser<KExercice>()
        log.warn("enter CEA")

        envelope() {
            log.warn("enter envelope")
            DB2.use { createTable("KEXERCISE", true, "ID" to INTEGER + PRIMARY_KEY, "KINDOF" to INTEGER, "T1" to TEXT, "T2" to TEXT) }
            var ds: SQLiteDatabase = DB2.writableDatabase
            ds.insert("KEXERCISE", "T1" to "Primer exercisi", "T2" to "")
            ds.insert("KEXERCISE", "T1" to "Segon  exercisi", "T2" to "")
            ds.insert("KEXERCISE", "T1" to "Tercer exercisi", "T2" to "")
            ds.insert("KEXERCISE", "T1" to "Cuaert exercisi", "T2" to "")
            ds.close()

            DB2.use { select("KEXECISE", "ID", "T111","T32").exec { parseList(rowParser) }}
        }
    }



    /*fun envelope(leter:(Unit) -> Unit){
        log.warn("TRULLYYYY   enter envelope")

        try {
            leter.invoke(Unit)
            log.warn("TRULLYYYY   exiting envelope")
        } catch (e: Exception) {
            log.warn("envelope exception:  ${e.message}   ${e.toString()}  ${e.stackTrace.toString()}")
        } finally {
        }

    }*/

    fun<T> envelope (leter:(Unit) -> List<T> ): List<T> {
        log.warn("TRULLYYYY   enter Lenvelope")

        try {
            return leter.invoke(Unit)
            log.warn("TRULLYYYY   exiting Lenvelope")
        } catch (e: Exception) {
            log.warn("envelope exception:  ${e.message}   ${e.toString()}  ${e.stackTrace.toString()}")
        } finally {
        }
        return emptyList()

    }



}


/*fun main(args: Array<String>) {
    runAFunc(::runLines)
}


fun runAFunc(predicate: (Int) -> (Int)) {
    val a = "five"
    if (a == "five") predicate.invoke(5) else predicate.invoke(3)

}

fun runLines (numbers: Int):Int {
    var i = numbers
    while (i > 0) {
        println("printed number is $i")
        i--
    }
}*/
//ENDOf classes


/*
fun runAFunc(predicate: (Int) -> (Unit)) {
    val a = "five"
    if (a == "five") predicate.invoke(5) else predicate.invoke(3)

}

class Lock(){
      fun lock(){}
      fun unlock(){}
}

fun <T> lock(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    }
    finally {
        lock.unlock()
    }
}

fun pepe(predicate:(Int)-> T){

}

fun <T> Abody()={}

fun g(){
    var p=Abody()
     lock
    pepe

}*/