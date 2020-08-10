package com.test.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClear(view: View){
        //total reset
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    //allow only one operator per equation
    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains(")") || value.contains("+")
                    || value.contains("-") || value.contains("*")
        }
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){//if was number and not dot
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onDigit(view: View){
        //Toast.makeText(this, "Button works", Toast.LENGTH_SHORT).show()
        lastNumeric = true
        tvInput.append((view as Button).text)//append = keep adding to view// get the text from the button text
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){//Check textview if we have any operators
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun removeZeroAfterDot(result:String) : String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2) //remove ".0"
        return value
    }

    fun onEqual(view: View){
        if (lastNumeric){
            var tvValue = tvInput.text.toString() //convert whatever is in textview into string
            var prefix = ""

            //check if its a minus number
            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)//skip the minus
                }
            }finally {
            }

            try{
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-") //if there is - split string into two
                    var one = splitValue[0]
                    var two = splitValue[1]

                    //if there is a minus with the number
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                } else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}