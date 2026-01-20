package com.example.sqlite

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: ArrayAdapter<String>

    private val userList = ArrayList<String>()
    private val idList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        val etName = findViewById<EditText>(R.id.etName)
        val etAge = findViewById<EditText>(R.id.etAge)
        val spGender = findViewById<Spinner>(R.id.spGender)
        val btnInsert = findViewById<Button>(R.id.btnInsert)
        val listView = findViewById<ListView>(R.id.listView)

        // Spinner Gender
        val genders = arrayOf("Laki-laki", "Perempuan")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders)
        spGender.adapter = genderAdapter

        // ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userList)
        listView.adapter = adapter

        // INSERT
        btnInsert.setOnClickListener {
            val name = etName.text.toString()
            val ageText = etAge.text.toString()
            val gender = spGender.selectedItem.toString()

            if (name.isNotEmpty() && ageText.isNotEmpty()) {
                dbHelper.insertUser(name, ageText.toInt(), gender)
                etName.text.clear()
                etAge.text.clear()
                loadData()
            } else {
                Toast.makeText(this, "Isi semua data", Toast.LENGTH_SHORT).show()
            }
        }

        // UPDATE
        listView.setOnItemClickListener { _, _, position, _ ->
            showUpdateDialog(idList[position])
        }

        // DELETE
        listView.setOnItemLongClickListener { _, _, position, _ ->
            dbHelper.deleteUser(idList[position])
            loadData()
            Toast.makeText(this, "Data dihapus", Toast.LENGTH_SHORT).show()
            true
        }

        loadData()
    }

    private fun loadData() {
        userList.clear()
        idList.clear()

        val cursor = dbHelper.getAllUsers()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val age = cursor.getInt(2)
            val gender = cursor.getString(3)

            idList.add(id)
            userList.add("$name | $age tahun | $gender")
        }
        cursor.close()
        adapter.notifyDataSetChanged()
    }

    private fun showUpdateDialog(id: Int) {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 40, 40, 40)

        val etName = EditText(this)
        etName.hint = "Nama baru"

        val etAge = EditText(this)
        etAge.hint = "Umur baru"
        etAge.inputType = InputType.TYPE_CLASS_NUMBER

        val spGender = Spinner(this)
        val genders = arrayOf("Laki-laki", "Perempuan")
        spGender.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders)

        layout.addView(etName)
        layout.addView(etAge)
        layout.addView(spGender)

        AlertDialog.Builder(this)
            .setTitle("Update Data")
            .setView(layout)
            .setPositiveButton("Update") { _, _ ->
                if (etName.text.isNotEmpty() && etAge.text.isNotEmpty()) {
                    dbHelper.updateUser(
                        id,
                        etName.text.toString(),
                        etAge.text.toString().toInt(),
                        spGender.selectedItem.toString()
                    )
                    loadData()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
