package rango1.vku.sqlite

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var edContact: EditText
    private lateinit var edAddress: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std: StudentModel? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener{ addStudent() }
        btnView.setOnClickListener{ getStudent() }
        btnUpdate.setOnClickListener{ updateStudent() }

        adapter?.setOnClickItems { Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
//        asd

            edName.setText(it.name)
            edEmail.setText(it.email)
            edContact.setText(it.contact)
            std = it
        }

    }
    private fun addStudent(){
    val name = edName.text.toString()
    val email =edEmail.text.toString()
    val contact = edContact.text.toString()
    val address = edAddress.text.toString()

        if (name.isEmpty() || email.isEmpty()){
            Toast.makeText(this,"Please Nhap Ten", Toast.LENGTH_SHORT).show()
        }else{
            val std = StudentModel(name = name, email = email, contact = contact, address = address)
            val status = sqliteHelper.insertStudent(std)
            if (status > 1){
                Toast.makeText(this, "Student Duoc Them", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudent()
            }else{
                Toast.makeText(this, "Khong the luu", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun getStudent(){
        val stdList = sqliteHelper.getAllStudent()
        Log.e("pppp", "${stdList.size}" )

        adapter?.addItems(stdList)
    }

    private fun updateStudent(){
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val contact = edContact.text.toString()
        val address = edAddress.text.toString()

//        Check record not change
        if (name == std?.name && email == std?.email && contact == std?.contact && address == std?.address){
            Toast.makeText(this, "Record not Change", Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null) return

        val std = StudentModel(id = std!!.id, name = name, email=email, contact = contact, address = address)
        val status = sqliteHelper .updateStudent(std)
        if (status > -1){
            clearEditText()
            getStudent()
        }else{
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearEditText() {
        edName.setText("")
        edEmail.setText("")
        edContact.setText("")
        edAddress.setText("")
        edName.requestFocus()

    }



    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView(){
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edMail)
        edContact = findViewById(R.id.edContact)
        edAddress = findViewById(R.id.edAddress)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}