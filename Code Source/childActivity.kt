package fr.sportcooks.giovanni_carre.master1.univangers.td3exercice4version3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup

class childActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)
    }
    fun on_btn_ajouter(view: View) {
        val et_tache = findViewById<EditText>(R.id.et_tache).text.toString()
        val radioGroup = findViewById<RadioGroup>(R.id.rg_prio)
        val selectedRadioButtonId: Int = radioGroup.checkedRadioButtonId
        var selectText : String = ""
        var selectedIndex: Int = -1
        if (selectedRadioButtonId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            selectText= selectedRadioButton.text.toString()
            selectedIndex = radioGroup.indexOfChild(selectedRadioButton)+1
        }

        val returnIntent = Intent()
        returnIntent.putExtra("Champ_Un",et_tache)
        returnIntent.putExtra("Champ_Deux",selectedIndex)

        setResult(RESULT_OK,returnIntent)

        finish()


    }
}