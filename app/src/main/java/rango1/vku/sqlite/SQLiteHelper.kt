package rango1.vku.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.lang.Exception

class SQLiteHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
     {

    companion object{
        private const val DATABASE_NAME = "student.db"
        private const val DATABASE_VERSION = 1
        private const val TBL_STUDENT = "tbl_student"
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val CONTACT = "contact"
        private const val ADDRESS = "address"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblStudent = ("CREATE TABLE " + TBL_STUDENT + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + EMAIL + " TEXT,"
                + CONTACT + " TEXT,"
                + ADDRESS + " TEXT" + ")")
        db?.execSQL(createTblStudent)
    }


         override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
                onCreate(db)
    }

     fun insertStudent(std: StudentModel) : Long{
         val db = this.writableDatabase

         val contentValue = ContentValues()
         contentValue.put(ID, std.id)
         contentValue.put(NAME, std.name)
         contentValue.put(EMAIL, std.email)
         contentValue.put(CONTACT, std.contact)
         contentValue.put(ADDRESS, std.address)

         val success = db.insert(TBL_STUDENT, null, contentValue)
         db.close()
         return success

     }
     @SuppressLint("Range", "Recycle")
     fun getAllStudent(): ArrayList<StudentModel>{
         val stdList:ArrayList<StudentModel> =  ArrayList()
         val selectQuery = "SELECT * FROM $TBL_STUDENT"
         val db= this.readableDatabase

         val cursor: Cursor?

         try {
             cursor = db.rawQuery(selectQuery, null)
         }
         catch (
             e: Exception
         ){
             e.printStackTrace()
             db.execSQL(selectQuery)
             return ArrayList()
         }
         var id: Int
         var name: String
         var email: String
         var contact: String
         var address: String

         if (cursor.moveToFirst()){
             do {
                 id = cursor.getInt(cursor.getColumnIndex("id"))
                 name = cursor.getString(cursor.getColumnIndex("name"))
                 email = cursor.getString(cursor.getColumnIndex("email"))
                 contact = cursor.getString(cursor.getColumnIndex("contact"))
                 address = cursor.getString(cursor.getColumnIndex("address"))

                 val std = StudentModel(id = id, name = name, email= email, contact = contact, address = address)
                 stdList.add(std)
             }while (cursor.moveToNext())

         }
         return stdList
     }

     fun updateStudent(std: StudentModel): Int{
         val db = this.writableDatabase

         val contentValues = ContentValues()
         contentValues.put(ID, std.id)
         contentValues.put(NAME, std.name)
         contentValues.put(EMAIL, std.email)
         contentValues.put(CONTACT, std.contact)
         contentValues.put(ADDRESS, std.address)

         val success = db.update(TBL_STUDENT, contentValues, "id=" + std.id, null)
         db.close()
         return success
     }

}