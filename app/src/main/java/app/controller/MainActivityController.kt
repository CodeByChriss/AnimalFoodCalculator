package app.controller

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.animalfoodcalculator.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

class MainActivityController : AppCompatActivity() {

    lateinit var cvDog : CardView
    lateinit var cvCat : CardView
    lateinit var tvWeight : TextView
    lateinit var rsWeight : RangeSlider
    lateinit var tvAge : TextView
    lateinit var fabDecrementAge : FloatingActionButton
    lateinit var fabIncrementAge : FloatingActionButton
    lateinit var tvResult : TextView

    var animalSelected : String? = null
    var ageSelected : Int? = null
    var weightSelected : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide() // esconder la barra superior que muestra el nombre de al app
        setContentView(R.layout.activity_calculo_comida_animales)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        cvDog = findViewById<CardView>(R.id.cvDog)
        cvCat = findViewById<CardView>(R.id.cvCat)
        tvWeight = findViewById<TextView>(R.id.tvWeight)
        rsWeight = findViewById(R.id.rsWeight)
        tvAge = findViewById<TextView>(R.id.tvAge)
        fabDecrementAge = findViewById<FloatingActionButton>(R.id.fabDecrementAge)
        fabIncrementAge = findViewById<FloatingActionButton>(R.id.fabIncrementAge)
        tvResult = findViewById<TextView>(R.id.tvResult)
    }

    private fun initListeners() {
        cvDog.setOnClickListener {
            animalSelected = "dog"
            var colorSeleccion = ContextCompat.getColor(this, R.color.app_comida_mascotas_cadview_selected)
            cvDog.setBackgroundColor(colorSeleccion)
            var colorNoSeleccion = ContextCompat.getColor(this, R.color.app_comida_mascotas_cadview)
            cvCat.setBackgroundColor(colorNoSeleccion)
            calculateResult()
        }
        cvCat.setOnClickListener {
            animalSelected = "cat"
            var colorSeleccion = ContextCompat.getColor(this, R.color.app_comida_mascotas_cadview_selected)
            cvCat.setBackgroundColor(colorSeleccion)
            var colorNoSeleccion = ContextCompat.getColor(this, R.color.app_comida_mascotas_cadview)
            cvDog.setBackgroundColor(colorNoSeleccion)
            calculateResult()
        }
        rsWeight.addOnChangeListener { _ , value , _ ->
            var resultWeight = value.toInt()
            weightSelected = resultWeight
            tvWeight.text = "$resultWeight Kg"
            calculateResult()
        }
        fabDecrementAge.setOnClickListener {
            if(ageSelected == null){
                ageSelected = 0
            }else if(ageSelected!! > 0){
                ageSelected = ageSelected!! - 1
            }
            tvAge.text = "$ageSelected "+getString(R.string.year)
            calculateResult()
        }
        fabIncrementAge.setOnClickListener {
            if(ageSelected == null){
                ageSelected = 1
            }else if(ageSelected!! < 70){
                ageSelected = ageSelected!! + 1
            }
            tvAge.text = "$ageSelected "+getString(R.string.year)
            calculateResult()
        }
    }

    private fun calculateWeightFood(): Float{
        var percentAge : Double = 0.0
        var percentWeight : Double = 0.0
        if(animalSelected.equals("dog")){
            percentWeight = ((weightSelected!!)+70).toDouble()
            percentAge = when{
                ageSelected!! <= 1 -> 1.20
                ageSelected!! > 8 -> 0.85
                else -> 1.0
            }
        }else{
            percentWeight = ((weightSelected!!)+50).toDouble()
            percentAge = when{
                ageSelected!! <= 1 -> 1.15
                ageSelected!! > 10 -> 0.90
                else -> 1.0
            }
        }
        var result : Float = (percentWeight*percentAge).toFloat()
        return result
    }

    private fun calculateResult(){
        if(animalSelected != null && ageSelected != null && weightSelected != null){
            val resultado = calculateWeightFood()
            tvResult.text = "$resultado"
        }
    }
}