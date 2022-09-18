package com.myapps.noteapp.Database

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.preference.PreferenceManager
import android.provider.BaseColumns
import com.myapps.noteapp.model.NoteEntity

const val DATABASE_NAME="note"
const val DATABASE_VERSION=1
private lateinit var sharedPreferences: SharedPreferences
lateinit var editor: SharedPreferences.Editor
class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {
    private var temp=0
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery=
            "CREATE TABLE ${DatabaseContainer.TABLE_NAME}(${BaseColumns._ID} INTEGER "+
                    " PRIMARY KEY AUTOINCREMENT  , ${DatabaseContainer.TABLE_TITLE} TEXT, "+
                    "${DatabaseContainer.TABLE_NOTE_TEXT} TEXT, "+
                    "${DatabaseContainer.TABLE_NUMBER} TEXT, "+
                    "${DatabaseContainer.TABLE_BACKGROUND_COLOR} TEXT, "+
                    "${DatabaseContainer.TABLE_CREATE_TIME} TEXT, "+
                    "${DatabaseContainer.TABLE_FAVORITES} TEXT, "+
                    "${DatabaseContainer.TABLE_ARCHIVE} TEXT)"
        db?.execSQL(createTableQuery)


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${DatabaseContainer.TABLE_NAME}")
    }
    fun insertData(context: Context,noteEntity: NoteEntity):Boolean{
        val db=this.writableDatabase
        val contentValues=ContentValues()
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
        editor= sharedPreferences.edit()
        temp=sharedPreferences.getInt("index",0)
        contentValues.put(DatabaseContainer.TABLE_TITLE, noteEntity.title)
        contentValues.put(DatabaseContainer.TABLE_NOTE_TEXT,noteEntity.noteText)
        contentValues.put(DatabaseContainer.TABLE_NUMBER,temp)
        contentValues.put(DatabaseContainer.TABLE_CREATE_TIME,noteEntity.createTime)
        contentValues.put(DatabaseContainer.TABLE_BACKGROUND_COLOR,noteEntity.background)
        contentValues.put(DatabaseContainer.TABLE_FAVORITES,"null")
        contentValues.put(DatabaseContainer.TABLE_ARCHIVE,"null")
        val insert=db.insert(DatabaseContainer.TABLE_NAME,null,contentValues)
        db.close()
        if (insert!=-1L){

            temp++
            editor.putInt("index",temp)
            editor.apply()

        }
        return insert != -1L




    }
    fun readData(name:String):Cursor{
        val db=this.readableDatabase
        return db.rawQuery("SELECT * FROM ${DatabaseContainer.TABLE_NAME} ORDER BY $name ASC",null)
    }
    fun deleteData(id: String):Boolean{
        val db=this.writableDatabase
        val delete:Int =db.delete(DatabaseContainer.TABLE_NAME,"${BaseColumns._ID}= ?", arrayOf(id))
        db.close()
        return delete!=-1
    }
    fun updateData(noteEntity: NoteEntity):Boolean{
        val db=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(DatabaseContainer.TABLE_TITLE,noteEntity.title)
        contentValues.put(DatabaseContainer.TABLE_NOTE_TEXT,noteEntity.noteText)
        contentValues.put(DatabaseContainer.TABLE_NUMBER,noteEntity.tableNumber)
        contentValues.put(DatabaseContainer.TABLE_BACKGROUND_COLOR,noteEntity.background)
        contentValues.put(DatabaseContainer.TABLE_CREATE_TIME,noteEntity.createTime)
            val result=db.update(DatabaseContainer.TABLE_NAME,contentValues, "${ BaseColumns._ID }=?", arrayOf(noteEntity.id))
    return result!=-1
    }
    fun updateIndex(noteEntity: NoteEntity):Boolean{
        val db=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(DatabaseContainer.TABLE_NUMBER,noteEntity.tableNumber)
        val result: Int =db.update(DatabaseContainer.TABLE_NAME,contentValues, "${ BaseColumns._ID }=?", arrayOf(noteEntity.id))
        return result!=-1
    }
    fun addFavorites(noteEntity: NoteEntity):Boolean{
        val db=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(DatabaseContainer.TABLE_FAVORITES,noteEntity.favorite)
        val result=db.update(DatabaseContainer.TABLE_NAME,contentValues, "${ BaseColumns._ID }=?", arrayOf(noteEntity.id))
        return result!=-1
    }
    fun addArchive(noteEntity: NoteEntity):Boolean{
        val db=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(DatabaseContainer.TABLE_ARCHIVE,noteEntity.archive)
        val result=db.update(DatabaseContainer.TABLE_NAME,contentValues, "${ BaseColumns._ID }=?", arrayOf(noteEntity.id))
        return result!=-1
    }



}