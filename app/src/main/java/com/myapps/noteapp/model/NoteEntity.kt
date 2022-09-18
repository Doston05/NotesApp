package com.myapps.noteapp.model

data class NoteEntity(var id:String, var title:String,  var noteText:String,var tableNumber:String,var background :String, var createTime:String,var favorite:String,var archive:String) {
    constructor(): this ("","","","","","","","")
}