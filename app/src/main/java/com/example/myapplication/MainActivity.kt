package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val morningSpending = mutableListOf<Float>()
    private val afternoonSpending = mutableListOf<Float>()
    private val expenseNotes = mutableListOf<String>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val morningSpendings = binding.edtMorningSpending.text.toString().toFloatOrNull()
            val afternoonSpendings = binding.edtAfternoonSpending.text.toString().toFloatOrNull()
            val note = binding.edtExpenseNotes.text.toString()

            if (morningSpendings == null || afternoonSpendings == null || note.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            } else {
                morningSpending.add(morningSpendings)
                afternoonSpending.add(afternoonSpendings)
                expenseNotes.add(note)
                Toast.makeText(this, "Data Saved!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnClear.setOnClickListener {
            binding.edtMorningSpending.text.clear()
            binding.edtAfternoonSpending.text.clear()
            binding.edtExpenseNotes.text.clear()
        }

        binding.btnViewDetails.setOnClickListener {
            val intent = Intent(this, DetailedViewScreen::class.java)
            intent.putExtra("morningSpendings", morningSpending.toFloatArray())
            intent.putExtra("afternoonSpendings", afternoonSpending.toFloatArray())
            intent.putExtra("notes", expenseNotes.toTypedArray())
            startActivity(intent)
        }
    }
}
