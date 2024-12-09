package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityDetailedViewScreenBinding

class DetailedViewScreen : AppCompatActivity() {
    private lateinit var binding: ActivityDetailedViewScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using ViewBinding
        binding = ActivityDetailedViewScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hello world

        // Retrieve data from the intent
        val morningSpendings = intent.getFloatArrayExtra("morningSpendings") ?: floatArrayOf()
        val afternoonSpendings = intent.getFloatArrayExtra("afternoonSpendings") ?: floatArrayOf()
        val notes = intent.getStringArrayExtra("notes") ?: arrayOf()

        // Build details string
        val details = StringBuilder()
        for (i in morningSpendings.indices) {
            details.append("Day ${i + 1}:\n")
            details.append("Morning Spendings: ${morningSpendings[i]} ZAR\n")
            details.append("Afternoon Spendings: ${afternoonSpendings[i]} ZAR\n")
            details.append("Notes: ${notes[i]}\n\n")
        }

        // Update the TextView and set button listener
        binding.txtDetails.text = details.toString()

        binding.btnCalculateAverage.setOnClickListener {
            if (morningSpending.isEmpty() || afternoonSpending.isEmpty()) {
                Toast.makeText(this, "No data available!", Toast.LENGTH_SHORT).show()
            } else {
                val totalDistance = morningSpending.sum() + afternoonSpending.sum()
                val averageDistance = totalDistance / (morningSpending.size + afternoonSpending.size)
                binding.txtAverage.text = "Average Distance: ${"%.2f".format(averageDistance)} km"
            }
        }

        binding.btnBackToMain.setOnClickListener {
            finish()
        }
    }
}