package com.example.deviseconverter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    //pour log (test)
    val TAG="MainActivity"
    // GLOBALE VARIABLES AND INITIALISATIONS OF VIEWS (input et output)

    lateinit var To_menu : AutoCompleteTextView
    lateinit var From_menu : AutoCompleteTextView
    lateinit var convertB :Button
    lateinit var amountEdit :TextInputEditText
    lateinit var resultatEdit :TextInputEditText
    lateinit var toolbar: Toolbar


    // les valeurs de la liste
    private val POUND = "Pound"
    private val DOLLAR = "Dollar"
    private val EURO = "Euro"
    private val DZD = "DZD"

    val values = mapOf(DOLLAR to 1 , POUND to 0.78,EURO to 0.91 , DZD to 135.65   )

    override fun onCreate(savedInstanceState: Bundle?) {
        //standard onCreate
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// Recuperer a partire  des boutton (inputs) link to xml
        inisialzer_boutton()

// creer To_menu et From_menu :
        populate_ListDeroulante()
// toolbar
        toolbarr()

// calculs en cliquant :
        convertB.setOnClickListener {
            calculate_res()
        }


    }


    private fun inisialzer_boutton(){
        convertB = findViewById(R.id.convert_button)
        amountEdit = findViewById(R.id.resu)
        To_menu = findViewById(R.id.to_field)
        From_menu = findViewById(R.id.from_menu)
        resultatEdit = findViewById(R.id.dernier_resultat)
        toolbar = findViewById(R.id.tool)
    }



    private fun populate_ListDeroulante(){

        // declarer la liste
        val listCountry = listOf(POUND , DOLLAR ,EURO , DZD )
        //val adapter = ArrayAdapter(context(MainActivity), R.layout.textvolu-$(echantillon de meme type)$-
        // , LA-LIST)
        val adapter = ArrayAdapter(this, R.layout.menu, listCountry)
        //linker l'adapter au to et from menus
        To_menu.setAdapter(adapter)
        From_menu.setAdapter(adapter)

    }



private fun calculate_res(){

    //listener de button (l'evenmment principale de l'appli)

        //récupérer le contenu dans des variables
        if (amountEdit.text.toString().isNotEmpty()){
            val amount = amountEdit.text.toString().toDouble()

//récupérer les valeurs des input dans des variables à l'aide de la map
            val toMenuval = values[To_menu.text.toString()] ?: 1.0
            val fromMenuval = values[From_menu.text.toString()] ?: 1.0

//algorithme de conversion (simple opération de multiplication et division)
            val resultt = amount * ((toMenuval.toString().toDouble()) / (fromMenuval.toString().toDouble()))
            val formated_result = String.format("%.3f",resultt)
//afficher le résultat dans le log


//affecter le résultat calculé au champ de résultat
            resultatEdit.setText(formated_result.toString())}
        else amountEdit.setError("Amount field is required !")

    }
    private fun toolbarr()
    {
        //show the toolbar(inflation)
        toolbar.inflateMenu(R.menu.menus)

        toolbar.setOnMenuItemClickListener {
                menuItem ->
            when (menuItem.itemId) {

                R.id.shareact -> {

val messagee = " ${amountEdit.text.toString()} ${From_menu.text.toString()} est ${resultatEdit.text.toString()} ${To_menu.text.toString()}"

                    val shareint = Intent(Intent.ACTION_SEND) //send action
                    shareint.type = "text/plain"//type
                    shareint.putExtra(Intent.EXTRA_TEXT , messagee) //data

                    if ((shareint.resolveActivity(packageManager)) != null) {
                        startActivity(shareint)
                    }
                    else {Toast.makeText(this, "no app avaible " , Toast.LENGTH_SHORT).show()}


                }

                R.id.sett -> {
                    Toast.makeText(this, "settings" , Toast.LENGTH_SHORT).show()}
                R.id.cnt -> {
                    Toast.makeText(this, "contact us" , Toast.LENGTH_SHORT).show()}
            }
            true
        }
    }




}