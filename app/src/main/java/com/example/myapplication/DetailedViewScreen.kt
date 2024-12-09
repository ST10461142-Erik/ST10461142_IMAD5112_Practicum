package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityDetailedViewScreenBinding

class DetailedViewScreen : AppCompatActivity() {
    private lateinit var binding: ActivityDetailedViewScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using ViewBinding
        binding = ActivityDetailedViewScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from the intent
        val morningSpendings = intent.getFloatArrayExtra("morningSpendings") ?: floatArrayOf()
        val afternoonSpendings = intent.getFloatArrayExtra("afternoonSpendings") ?: floatArrayOf()
        val notes = intent.getStringArrayExtra("notes") ?: arrayOf()

        // Build details string
        val details = StringBuilder()
        for (i in morningSpendings.indices) {
            details.append("Day ${i + 1}:\n")
            details.append("Morning Spending: ${morningSpendings[i]} ZAR\n")
            details.append("Afternoon Spending: ${afternoonSpendings[i]} ZAR\n")
            details.append("Notes: ${notes[i]}\n\n")
        }
        binding.txtDetails.text = details.toString()

        // Calculate average spending for morning and afternoon
        binding.btnCalculateAverage.setOnClickListener {
            if (morningSpendings.isEmpty() || afternoonSpendings.isEmpty()) {
                Toast.makeText(this, "No data available!", Toast.LENGTH_SHORT).show()
            } else {
                val averageMorningSpending = morningSpendings.average()
                val averageAfternoonSpending = afternoonSpendings.average()
                binding.txtAverage.text = """
                    Average Morning Spending: ${"%.2f".format(averageMorningSpending)} ZAR
                    Average Afternoon Spending: ${"%.2f".format(averageAfternoonSpending)} ZAR
                """.trimIndent()
            }
        }

        // Calculate highest spending day
        binding.btnHighestSpending.setOnClickListener {
            if (morningSpendings.isEmpty() || afternoonSpendings.isEmpty()) {
                Toast.makeText(this, "No data available!", Toast.LENGTH_SHORT).show()
            } else {
                // Combine morning and afternoon spending into total daily spending
                val totalDailySpending = morningSpendings.zip(afternoonSpendings) { morning, afternoon ->
                    morning + afternoon
                }

                // Find the day with the highest spending
                val maxSpending = totalDailySpending.maxOrNull()
                val maxDayIndex = totalDailySpending.indexOf(maxSpending)

                // Prepare the display text
                val message = if (maxSpending != null) {
                    """
            Highest Spending Day:
            Day ${maxDayIndex + 1}
            Morning: ${"%.2f".format(morningSpendings[maxDayIndex])} ZAR
            Afternoon: ${"%.2f".format(afternoonSpendings[maxDayIndex])} ZAR
            Total: ${"%.2f".format(maxSpending)} ZAR
            """.trimIndent()
                } else {
                    "No spending data available."
                }

                // Show the highest spending day in txtDetails
                binding.txtHighest.text = message
            }
        }


        // Back to main screen
        binding.btnBackToMain.setOnClickListener {
            finish()
        }
    }
}
