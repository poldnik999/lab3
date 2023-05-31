package com.example.lab3

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    //Переменные для расчета
    private var result = ""
    private var number: String = ""
    private var numbers: Array<Double> = arrayOf(0.0, 0.0)

    //Значение знака математической операции и точка
    private var delimiter : String = ""
    private var point : String = "."

    //Вывод текста и кнопка =
    private var resultText: TextView? = null
    private var result_btn: Button? = null

    //Математические знаки
    private var clear:   Button? = null
    private var point_btn: Button? = null
    private var changeSigh: Button? = null
    private var division: Button? = null
    private var multiplicate: Button? = null
    private var minus: Button? = null
    private var plus: Button? = null
    private var percent: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clear = findViewById(R.id.button16)
        resultText = findViewById(R.id.textView)
        result_btn = findViewById(R.id.button23)
        point_btn = findViewById(R.id.button15)
        changeSigh = findViewById(R.id.button17)

        division = findViewById(R.id.division)
        multiplicate = findViewById(R.id.multiplicate)
        plus = findViewById(R.id.plus)
        minus = findViewById(R.id.minus)
        percent = findViewById(R.id.present)

        clear?.setOnClickListener{
            resultText?.text = ""
            resultText?.hint = "0"
            number = ""
            setDefaultParam()
        }
        result_btn?.setOnClickListener{
            if(delimiter != ""){
                resultText?.hint = ""
                val num: Array<Double> = setArray(resultText?.text.toString(),delimiter)
                if(num.count() == 2){
                    resultText?.text = ""
                    when (delimiter) {
                        "/" -> {
                            if(num[1] == 0.0)   resultText?.hint = "Nan"
                            else                result = (num[0] / num[1]).toString()
                        }
                        "*" -> result = (num[0] * num[1]).toString()
                        "-" -> result = (num[0] - num[1]).toString()
                        "+" -> result = (num[0] + num[1]).toString()
                    }

                    if(resultText?.hint != "Nan") resultText?.text = normalFormat(result.toDouble()).toString()
                    number = resultText?.text.toString()
                    setDefaultParam()

                }
            }

        }

        //Назначение математического знака
        division?.setOnClickListener{       delimiter = setDelimiter("/") }
        multiplicate?.setOnClickListener{   delimiter = setDelimiter("*") }
        plus?.setOnClickListener{           delimiter = setDelimiter("+") }
        minus?.setOnClickListener{          delimiter = setDelimiter("-") }

        point_btn?.setOnClickListener{          point = setPoint(point) }
        changeSigh?.setOnClickListener{
            //Вот тут осталось доделать
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            if(delimiter == ""){
                if("-" !in resultText?.text.toString()) resultText?.text = "-"+ resultText?.text.toString()
                else                                    resultText?.text = resultText?.text.toString().replace("-","")
            }
            else{
                if(delimiter + "-" !in resultText?.text.toString() && delimiter != "-")
                    resultText?.text = resultText?.text.toString().replace(delimiter,delimiter+"-")
                else
                    resultText?.text = resultText?.text.toString().replace(delimiter+"-",delimiter)
                    //resultText?.text = resultText?.text.toString().padStart(firstNumberLength, '-')
            }
        }
    }
    fun numberClick(view: View){
        val butt : Button = findViewById(view.id)
        number += butt.text
        resultText?.text = resultText?.text.toString() + number.last()
    }

    private fun setDefaultParam(){

        delimiter = ""
        numbers = arrayOf(0.0, 0.0)
        result = ""
    }
    private fun setArray(x : String, split: String): Array<Double> {
            if(x[0] == '-'){
                var parts = x.substring(1).split(split)
                numbers[0] = -parts[0].toDouble()
                numbers[1] = parts[1].toDouble()
            }
            else{
                var parts = x.split(split)
                numbers[0] = parts[0].toDouble()
                numbers[1] = parts[1].toDouble()
            }
            return numbers
    }
    private fun setPoint(point: String): String{
        if (point !in number){
            resultText?.text = resultText?.text.toString() + point
            number += point
        }
        return point
    }
    private fun setDelimiter(delimiter: String): String{
        if (resultText?.text.toString() != "" && delimiter !in resultText?.text.toString().substring(1)){
            resultText?.text = resultText?.text.toString() + delimiter
            number = ""
        }
        return delimiter
    }
    private fun normalFormat(x : Double): Any {
        return if(x == x.roundToInt().toDouble())   x.roundToInt()
        else x
    }




}