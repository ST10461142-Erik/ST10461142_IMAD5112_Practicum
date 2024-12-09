package com.example.myapplication

//list of imports
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    // Arrays to store running data
    private val morningSpending = mutableListOf<Float>()
    private val afternoonSpending = mutableListOf<Float>()
    private val ExpenseNotes = mutableListOf<String>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up event listeners using binding
        binding.btnSubmit.setOnClickListener {
            val morningSpendings = binding.edtMorningSpending.text.toString().toFloatOrNull()
            val afternoonSpendings = binding.edtAfternoonSpending.text.toString().toFloatOrNull()
            val note = binding.edtExpenseNotes.text.toString()

            if (morningSpendings == null || afternoonSpendings == null || note.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            } else {
                morningSpending.add(morningSpendings)
                afternoonSpending.add(afternoonSpendings)
                ExpenseNotes.add(note)
                Toast.makeText(this, "Data Saved!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnClear.setOnClickListener {
            binding.edtMorningSpending.text.clear()
            binding.edtAfternoonSpending.text.clear()
            binding.edtExpenseNotes.text.clear()
        }

        binding.btnCalculateAverage.setOnClickListener {
            if (morningSpending.isEmpty() || afternoonSpending.isEmpty()) {
                Toast.makeText(this, "No data available!", Toast.LENGTH_SHORT).show()
            } else {
                val totalDistance = morningSpending.sum() + afternoonSpending.sum()
                val averageDistance = totalDistance / (morningSpending.size + afternoonSpending.size)
                binding.txtAverage.text = "Average Distance: ${"%.2f".format(averageDistance)} km"
            }
        }

        binding.btnViewDetails.setOnClickListener {
            val intent = Intent(this, DetailedViewScreen::class.java)
            intent.putExtra("morningRuns", morningSpending.toFloatArray())
            intent.putExtra("afternoonRuns", afternoonSpending.toFloatArray())
            intent.putExtra("notes", ExpenseNotes.toTypedArray())
            startActivity(intent)
        }
    }
}