package com.example.loancalc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.SeekBar

//private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var etLoanAmount: EditText
    private lateinit var etSeekbarInterest: SeekBar
    private lateinit var etDownpayment: EditText
    private lateinit var etPeriodMonths: EditText
    private lateinit var btnCalculateButton: Button
    private lateinit var tvShowInterest: TextView
    private lateinit var tvDisplayMonthlyPayment: TextView
    private lateinit var tvDisplayTotalRePaid: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etLoanAmount= findViewById(R.id.etLoanAmount)
        etSeekbarInterest = findViewById(R.id.seekbarInterest)
        etDownpayment = findViewById(R.id.etDownpayment)
        etPeriodMonths = findViewById(R.id.edPeriodMonths)
        btnCalculateButton = findViewById(R.id.btnCalculateButton)
        tvShowInterest = findViewById(R.id.tvShowInterest)
        tvDisplayMonthlyPayment = findViewById(R.id.tvDisplayMonthlyPayment)
        tvDisplayTotalRePaid = findViewById(R.id.tvDisplayTotalRePaid)
        etSeekbarInterest.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean){
                tvShowInterest.text = "$progress%"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        btnCalculateButton.setOnClickListener{
            calculate(it)
        }

    }
    private fun calculate(view: View) {

        val loanAmt = etLoanAmount.text.toString().toDoubleOrNull() ?: 0.0
        val interestRate = etSeekbarInterest.progress.toDouble() / 1200
        val periodMonths = etPeriodMonths.text.toString().toIntOrNull() ?: 0
        val downPayment = etDownpayment.text.toString().toDoubleOrNull() ?: 0.0

        var monthlyInstallment = 0.0

        if(loanAmt > 0 && periodMonths > 0 && downPayment >= 0) {
            monthlyInstallment = ((loanAmt-downPayment) * interestRate * Math.pow((1 + interestRate), periodMonths.toDouble())) / (Math.pow((1 + interestRate), periodMonths.toDouble()) - 1)
            tvDisplayMonthlyPayment.text = monthlyInstallment.toString()
            tvDisplayTotalRePaid.text = (monthlyInstallment*periodMonths).toString()
        }
        else if(loanAmt > 0 && periodMonths > 0) {
            monthlyInstallment = ((loanAmt-downPayment) * interestRate * Math.pow((1 + interestRate), periodMonths.toDouble())) / (Math.pow((1 + interestRate), periodMonths.toDouble()) - 1)
            tvDisplayMonthlyPayment.text = monthlyInstallment.toString()
            tvDisplayTotalRePaid.text = (monthlyInstallment*periodMonths).toString()
        }
    }
}
