package com.myapps.noteapp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myapps.noteapp.Database.DatabaseContainer
import com.myapps.noteapp.Database.DatabaseHelper
import com.myapps.noteapp.R
import com.myapps.noteapp.adapter.RvNoteAdapter
import com.myapps.noteapp.databinding.ActivityMainBinding
import com.myapps.noteapp.model.NoteEntity
import java.util.*

class MainActivity : AppCompatActivity(), RvNoteAdapter.OnClickListener {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: ActivityMainBinding
    private var sort= DatabaseContainer.TABLE_TITLE
    private var column=2
    private lateinit var prefs:SharedPreferences
    private lateinit var settingsPrefs:SharedPreferences
    private  var favorite=-1
    private var archive=-1
    private lateinit var editor:SharedPreferences.Editor
    private var sortListOf="all"
    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var rvAdapter: RvNoteAdapter
    private var  screen=0
    private val list:MutableList<NoteEntity> = ArrayList()
    private var tempList:MutableList<NoteEntity> = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs=applicationContext.getSharedPreferences("myPref",Context.MODE_PRIVATE)
        settingsPrefs=applicationContext.getSharedPreferences("navDrawer",Context.MODE_PRIVATE)
        editor=prefs.edit()
        drawerLayout()
        if (prefs.contains("sort")){
            sort= prefs.getString("sort", DatabaseContainer.TABLE_TITLE).toString()
        }
        if (prefs.contains("column")){
            column=prefs.getInt("column",2)
        }
        initList(sort,column)
        viewVisibility("Create your first note !")
        addNote()
        dragDrop()





    }



    private fun drawerLayout(){
        toggle= ActionBarDrawerToggle( this,binding.myDrawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.myDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.itemIconTintList = null
        binding.navView.isLongClickable=true
        binding.navView.menu.getItem(3).title=settingsPrefs.getString("color1","Purple")
        binding.navView.menu.getItem(4).title=settingsPrefs.getString("color2","Green")
        binding.navView.menu.getItem(5).title=settingsPrefs.getString("color3","Blue")
        binding.navView.menu.getItem(6).title=settingsPrefs.getString("color4","Orange")
        binding.navView.menu.getItem(7).title=settingsPrefs.getString("color5","Red")
        binding.navView.menu.getItem(8).title=settingsPrefs.getString("color6","Gray")
        binding.navView.menu.getItem(9).title=settingsPrefs.getString("color7","Teal")
        editor.putString("color11", binding.navView.menu.getItem(3).title.toString() )
        editor.putString("color22", binding.navView.menu.getItem(4).title.toString() )
        editor.putString("color33", binding.navView.menu.getItem(5).title.toString() )
        editor.putString("color44", binding.navView.menu.getItem(6).title.toString() )
        editor.putString("color55", binding.navView.menu.getItem(7).title.toString() )
        editor.putString("color66", binding.navView.menu.getItem(8).title.toString() )
        editor.putString("color77", binding.navView.menu.getItem(9).title.toString() )
        editor.apply()

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.favoriteBtn ->{
                    sortListOf="favorite"
                    screen=1
                    it.isCheckable = true
                    supportActionBar?.title="Favorite"
                    readSortedList(sort,column,6,"favorite")
                    viewVisibility("Add your first note to favorite!")

                }
                R.id.archiveBtn ->{
                    sortListOf="archive"
                    screen=2
                    supportActionBar?.title="Archive"
                    it.isCheckable = true
                    readSortedList(sort,column,7,"archive")
                    viewVisibility("No notes found in archive")
                }
                R.id.exitBtn ->{
                    it.isCheckable = true
                    finishAffinity()
                }
                R.id.allNotes ->{
                    sortListOf="all"
                    screen=0
                    supportActionBar?.title="Notes"
                    it.isCheckable = true
                    initList(sort,column)
                    viewVisibility( "Create your first note !")
                }
                R.id.purple ->{
                    sortListOf="#9772FB"
                    it.isCheckable=true
                    supportActionBar?.title=it.title.toString()
                    sortListByColor(sort,column, color = "#9772FB")
                    viewVisibility( "Create your first note !")
                }
                R.id.blue ->{
                    sortListOf="#0096FF"
                    it.isCheckable=true
                    supportActionBar?.title=it.title.toString()
                    sortListByColor(sort,column, color = "#0096FF")
                    viewVisibility( "Create your first note !")
                }
                R.id.red ->{
                    sortListOf="#F87474"
                    it.isCheckable=true
                    supportActionBar?.title=it.title.toString()
                    sortListByColor(sort,column, color = "#F87474")
                    viewVisibility( "Create your first note !")
                }
                R.id.green ->{
                    sortListOf="#7DCE13"
                    it.isCheckable=true
                    supportActionBar?.title=it.title.toString()
                    sortListByColor(sort,column, color = "#7DCE13")
                    viewVisibility( "Create your first note !")
                }
                R.id.dark_green ->{
                    sortListOf="#9EB23B"
                    it.isCheckable=true
                    supportActionBar?.title=it.title.toString()
                    sortListByColor(sort,column, color = "#9EB23B")
                    viewVisibility( "Create your first note !")
                }
                R.id.orange ->{
                    sortListOf="#E8AA42"
                    it.isCheckable=true
                    supportActionBar?.title=it.title.toString()
                    sortListByColor(sort,column, color = "#E8AA42")
                    viewVisibility( "Create your first note !")
                }
                R.id.teal ->{
                    sortListOf="#76BA99"
                    it.isCheckable=true
                    supportActionBar?.title=it.title.toString()
                    sortListByColor(sort,column, color = "#76BA99")
                    viewVisibility( "Create your first note !")
                }
                R.id.settings ->{
                    startActivity(Intent(this, SettingsActivity::class.java))

                }

            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun prefsGet(){
        if (prefs.contains("sort")){
            sort= prefs.getString("sort", DatabaseContainer.TABLE_TITLE).toString()
        }
        if (prefs.contains("column")){
            column=prefs.getInt("column",2)
        }
    }
    private fun addNote(){
        binding.addNote.setOnClickListener{
            startActivity(Intent(this, NoteActivity::class.java))
            Intent(this, EditorActivity::class.java)
        }
    }
    private fun viewVisibility(text: String){
        if (list.size==0)
        {
            binding.createFirstNote.visibility=View.VISIBLE
            binding.createFirstNote.text=text
            binding.firstNote.visibility=View.VISIBLE
            binding.noteTitles.visibility=View.INVISIBLE
        }
        else{
            binding.createFirstNote.visibility=View.INVISIBLE
            binding.firstNote.visibility=View.INVISIBLE
            binding.noteTitles.visibility=View.VISIBLE
        }
    }
    private fun dragDrop(){
        val itemTouchHelper=ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP
                or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,0){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val position=viewHolder.adapterPosition
                val lastPosition=target.adapterPosition
                Collections.swap(list, position, lastPosition)
                Collections.swap(tempList, position, lastPosition)
                rvAdapter.notifyItemMoved(position, lastPosition)
                refreshDbHelper()

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {






            }

        })
        itemTouchHelper.attachToRecyclerView(binding.rvNotes)
    }

    private fun initList(sort :String,staggeredColumns:Int){
        dbHelper=DatabaseHelper(this)
        editor.putString("sort",sort)
        editor.putInt("column",staggeredColumns)
        editor.apply()
        val cursor=dbHelper.readData(sort)
        list.clear()
        if (cursor.count>0) {
            while (cursor.moveToNext()) {
                if (!cursor.getString(7).contains("archive")) {
                    val id = cursor.getString(0)
                    val title = cursor.getString(1)
                    val textNotes = cursor.getString(2)
                    val createdTime = cursor.getString(5)
                    val background = cursor.getString(4)
                    val favorite=cursor.getString(6)
                    val archive=cursor.getString(7)
                    val note =
                        NoteEntity(id, title, textNotes, BaseColumns._ID, background, createdTime,favorite,archive)
                    list.add(note)
                    tempList = arrayListOf()
                }
            }
        }
        tempList.clear()
        tempList.addAll(list)
        rvAdapter= RvNoteAdapter(this,tempList)
        binding.rvNotes.adapter=rvAdapter
        binding.rvNotes.layoutManager = StaggeredGridLayoutManager(
            staggeredColumns,StaggeredGridLayoutManager.VERTICAL)
        rvAdapter.setOnClickListener(this)
    }
    private fun sortListByColor(sort :String, staggeredColumns:Int, color:String){
        dbHelper=DatabaseHelper(this)
        editor.putString("sort",sort)
        editor.putInt("column",staggeredColumns)
        editor.apply()
        val cursor=dbHelper.readData(sort)
        list.clear()
        if (cursor.count>0) {
            while (cursor.moveToNext()) {
                if (cursor.getString(4).equals(color)&&!cursor.getString(7).equals("archive")) {
                    val id = cursor.getString(0)
                    val title = cursor.getString(1)
                    val textNotes = cursor.getString(2)
                    val createdTime = cursor.getString(5)
                    val background = cursor.getString(4)
                    val favorite=cursor.getString(6)
                    val archive=cursor.getString(7)
                    val note =
                        NoteEntity(id, title, textNotes, BaseColumns._ID, background, createdTime,favorite,archive)
                    list.add(note)
                    tempList = arrayListOf()
                }
            }
        }
        tempList.clear()
        tempList.addAll(list)
        rvAdapter= RvNoteAdapter(this,tempList)
        binding.rvNotes.adapter=rvAdapter
        binding.rvNotes.layoutManager = StaggeredGridLayoutManager(
            staggeredColumns,StaggeredGridLayoutManager.VERTICAL)
        rvAdapter.setOnClickListener(this)
    }
    private fun readSortedList(sort :String,staggeredColumns:Int, index:Int, item:String){
        list.clear()
        dbHelper=DatabaseHelper(this)
        editor.putString("sort",sort)
        editor.putInt("column",staggeredColumns)
        editor.apply()
        val cursor=dbHelper.readData(sort)
        if (cursor.count>0) {
            while( cursor.moveToNext()) {
                if (cursor.getString(index).contains(item)) {
                    val id = cursor.getString(0)
                    val title = cursor.getString(1)
                    val textNotes = cursor.getString(2)
                    val createdTime = cursor.getString(5)
                    val background = cursor.getString(4)
                    val favorite=cursor.getString(6)
                    val archive=cursor.getString(7)
                    val note = NoteEntity(
                        id,
                        title,
                        textNotes,
                        BaseColumns._ID,
                        background,
                        createdTime,
                        favorite,
                        archive

                    )
                    list.add(note)
                    tempList = arrayListOf()
                }
            }


        }
        tempList.clear()
        tempList.addAll(list)
        rvAdapter= RvNoteAdapter(this,tempList)
        binding.rvNotes.adapter=rvAdapter
        binding.rvNotes.layoutManager = StaggeredGridLayoutManager(
            staggeredColumns,StaggeredGridLayoutManager.VERTICAL)
        rvAdapter.setOnClickListener(this)


    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchItem= menu.findItem(R.id.search)
        val searchView=searchItem?.actionView as SearchView
        val byDate= menu.findItem(R.id.by_date)

        byDate.setOnMenuItemClickListener {
            sort = DatabaseContainer.TABLE_CREATE_TIME

            when (sortListOf) {
                "all" -> {
                    initList(sort, column)
                    prefsGet()
                }
                "archive" -> {
                    readSortedList(sort,column,7,"archive")
                }
                "favorite" -> {
                    readSortedList(sort,column,6,"favorite")
                }
                else -> {

                    sortListByColor(sort,column,sortListOf)
                }
            }
            viewVisibility("Create your first note !")
            Toast.makeText(this,"Notes sorted by created time",Toast.LENGTH_SHORT).show()
            return@setOnMenuItemClickListener true
        }
        val byTitle= menu.findItem(R.id.by_title)
        byTitle.setOnMenuItemClickListener {
            sort= DatabaseContainer.TABLE_TITLE
            when (sortListOf) {
                "all" -> {
                    initList(sort, column)
                }
                "archive" -> {
                    readSortedList(sort,column,7,"archive")
                }
                "favorite" -> {
                    readSortedList(sort,column,6,"favorite")
                }
                else -> {

                    sortListByColor(sort,column,sortListOf)
                }
            }
            prefsGet()
            viewVisibility("Create your first note !")
            Toast.makeText(this,"Notes sorted by title",Toast.LENGTH_SHORT).show()
            return@setOnMenuItemClickListener true
        }
        val byDragDrop=menu.findItem(R.id.drag_drop)
        byDragDrop.setOnMenuItemClickListener {
            sort= DatabaseContainer.TABLE_NUMBER
            when (sortListOf) {
                "all" -> {
                    initList(sort, column)
                }
                "archive" -> {
                    readSortedList(sort,column,7,"archive")
                }
                "favorite" -> {
                    readSortedList(sort,column,6,"favorite")
                }
                else -> {
                    sortListByColor(sort,column,sortListOf)
                }
            }
            prefsGet()
            viewVisibility("Create your first note !")
            Toast.makeText(this,"Notes sorted by Drag drop",Toast.LENGTH_SHORT).show()
            return@setOnMenuItemClickListener true
        }
        val oneColumn=menu.findItem(R.id.one_column)
        oneColumn.setOnMenuItemClickListener {
            column=1
            when (sortListOf) {
                "all" -> {
                    initList(sort, column)
                }
                "archive" -> {
                    readSortedList(sort,column,7,"archive")
                }
                "favorite" -> {
                    readSortedList(sort,column,6,"favorite")
                }
                else -> {
                    sortListByColor(sort,column,sortListOf)
                }
            }
            prefsGet()
            viewVisibility("Create your first note !")
            return@setOnMenuItemClickListener true
        }
        val twoColumn=menu.findItem(R.id.two_column)
        twoColumn.setOnMenuItemClickListener {
            column=2
            when (sortListOf) {
                "all" -> {
                    initList(sort, column)
                }
                "archive" -> {
                    readSortedList(sort,column,7,"archive")
                }
                "favorite" -> {
                    readSortedList(sort,column,6,"favorite")
                }
                else -> {
                    sortListByColor(sort,column,sortListOf)
                }
            }
            prefsGet()
            viewVisibility("Create your first note !")
            return@setOnMenuItemClickListener true
        }
        val threeColumn=menu.findItem(R.id.three_column)
        threeColumn.setOnMenuItemClickListener {
            column=3
            when (sortListOf) {
                "all" -> {
                    initList(sort, column)
                }
                "archive" -> {
                    readSortedList(sort,column,7,"archive")
                }
                "favorite" -> {
                    readSortedList(sort,column,6,"favorite")
                }
                else -> {
                    sortListByColor(sort,column,sortListOf)
                }
            }
            prefsGet()
            viewVisibility("Create your first note !")
            return@setOnMenuItemClickListener true
        }


        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempList.clear()
                val searchText= newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    list.forEach {
                        if (it.title.lowercase(Locale.getDefault()).contains(searchText)) {
                            tempList.add(it)
                        }

                    }

                    if (tempList.size==0) {
                        binding.noItemBack.visibility = View.VISIBLE
                        binding.fileNotFountTv.visibility = View.VISIBLE
                    }
                    else {
                        binding.noItemBack.visibility = View.INVISIBLE
                        binding.fileNotFountTv.visibility = View.INVISIBLE
                    }




                    rvAdapter.notifyDataSetChanged()






                    rvAdapter.notifyDataSetChanged()
                }
                else{
                    binding.noItemBack.visibility = View.INVISIBLE
                    binding.fileNotFountTv.visibility = View.INVISIBLE
                    tempList.addAll(list)
                    rvAdapter.notifyDataSetChanged()
                }



                return false
            }



        })
        return super.onCreateOptionsMenu(menu)
    }
    private fun changePopUpText(popupMenu:PopupMenu, position: Int){
        if (list[position].archive == "archive"){
            popupMenu.menu.findItem(R.id.archive).title="Unarchive"
            archive=1
        }
        else {
            popupMenu.menu.findItem(R.id.archive).title="Archive"
        }
        if (list[position].favorite == "favorite"){
            favorite=1
            popupMenu.menu.findItem(R.id.favorite).title="Unfavorite"
        }
        else {
            popupMenu.menu.findItem(R.id.favorite).title="Favorite"
        }
    }
    override fun OnClicked(position: Int, view: View) {

        val popupMenu = PopupMenu(applicationContext, view)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        changePopUpText(popupMenu,position)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->


            when (item!!.itemId) {
                R.id.delete_menu_button -> {

                    alertDialog("Delete","Do you really want to delete this note?",position)


                }
                R.id.share_menu_button -> {

                    (alertDialog("Share","Do you really want to share this note?",position))
                }
                R.id.edit -> {
                    (alertDialog("Edit","Do you really want to edit this note?",position))
                }
                R.id.favorite ->{
                    if (tempList[position].favorite=="null") {
                        (alertDialog(
                            "Favorite",
                            "Do you really want to add this note to favorite notes?",
                            position
                        ))

                    }
                    else{
                        (alertDialog(
                            "Unfavorite",
                            "Do you really want to remove this note to favorite notes?",
                            position

                        ))
                       }

                }
                R.id.archive -> {
                    if (tempList[position].archive=="null") {

                        (alertDialog(
                            "Archive",
                            "Do you really want to archive this note?",
                            position
                        ))

                    } else {
                        (alertDialog(
                            "Unarchive",
                            "Do you really want to unarchive this note?",
                            position
                        ))
                    }
                }
            }

            true
        }

        popupMenu.show()
    }
    fun refreshDbHelper(){
        for (i in 0 until tempList.size) {
            val noteEntity = list[i]
            val title=noteEntity.title
            val noteText=noteEntity.noteText
            val background=noteEntity.background
            val id=noteEntity.id
            val createTime=noteEntity.createTime
            val favorite=noteEntity.favorite
            val archive=noteEntity.archive
            val tableNumber="$i"
            dbHelper.writableDatabase
            dbHelper.updateIndex(NoteEntity(id,title,noteText,tableNumber,background,createTime,favorite,archive))


        }
    }
    private fun addToFavorite(action:String, position: Int){


        dbHelper.writableDatabase
        dbHelper.addFavorites(
            NoteEntity(
                list[position].id,
                list[position].title,
                list[position].noteText,
                list[position].tableNumber,
                list[position].background,
                list[position].createTime,
                action,
                list[position].archive
            )
        )
        if (action=="favorite") {
            initList(sort,column)

            viewVisibility("Create your first note !")
        }
        else {
            if (screen == 1) {
                readSortedList(sort, column, 6, "favorite")
                rvAdapter.notifyDataSetChanged()
                viewVisibility(" Add your first note to favorite!")
            } else {
                initList(sort, column)
                rvAdapter.notifyDataSetChanged()

            }
        }



    }
    private fun addToArchive(action: String, position: Int){

        dbHelper.writableDatabase
        dbHelper.addArchive(
            NoteEntity(
                list[position].id,
                list[position].title,
                list[position].noteText,
                list[position].tableNumber,
                list[position].background,
                list[position].createTime,
                list[position].favorite,
                action
            )
        )
        if (action=="archive") {
            initList(sort, column)
            viewVisibility("Create your first note !")
        }
        else{
            if (screen==2){
                readSortedList(sort, column, 7, "archive")
                viewVisibility("No notes found in archive !")
            }
            else
            {
                initList(sort, column)
                viewVisibility("Create your first note !")
            }

        }



    }
    private fun deleteData(position: Int){
        dbHelper.deleteData(list[position].id)
        list.removeAt(position)
        tempList.removeAt(position)
        rvAdapter.notifyItemRemoved(position)
        viewVisibility("Create your first note !")
    }
    private fun editData(position: Int){
        val intent=Intent(this, EditorActivity::class.java)
        intent.putExtra("edit","edit")
        intent.putExtra("title",list[position].title)
        intent.putExtra("text",list[position].noteText)
        intent.putExtra("time",list[position].createTime)
        intent.putExtra("background",list[position].background)
        intent.putExtra("id",list[position].id)
        startActivity(intent)
    }
    private fun alertDialog(title :String, text:String, position: Int):Boolean{
        val builder= MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        var temp=0
        builder.setTitle(title)
            .setMessage(text)
        builder.setPositiveButton("Yes"){ _, _ ->

            temp=1
            if (title.lowercase() == "delete"){
                deleteData(position)

            }
            else if (title.lowercase()==("edit")){
                editData(position)
            }
            else if (title=="Archive"&&tempList[position].archive=="null"&&tempList[position].favorite!="null"){
                addToFavorite("null",position)
                addToArchive("archive",position)

            }
            else if (title=="Archive"&&tempList[position].archive=="null"){

                addToArchive("archive",position)
            }
            else if (title=="Favorite"&&tempList[position].archive=="archive"){
                Toast.makeText(applicationContext,"Before adding to favorite unarchive note",Toast.LENGTH_LONG).show()

            }
            else  if (title=="Unarchive"&& tempList[position].archive!="null"){
                addToArchive("null",position)
            }
            else  if (title=="Favorite"&& tempList[position].favorite=="null" && tempList[position].archive=="null"){
                addToFavorite("favorite",position)
            }
            else  if (title=="Unfavorite"&& tempList[position].favorite!="null"){
                addToFavorite("null",position)
            }
            else if (title.lowercase()==("share")){

                val url =
                    "Title: ${list[position].title}\n\nNote text: ${list[position].noteText}"
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, url)
                sendIntent.type = "text/plain"
                startActivity(Intent.createChooser(sendIntent, "Preview with:"))
            }

        }
        builder.setNegativeButton("No"){_,_->

        }
        builder.create()
        val alertDialog=builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
        return temp!=0
    }








}






