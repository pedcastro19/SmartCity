package ipvc.estg.smartcity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        try {
            this.supportActionBar!!.hide()
            }
        catch (e: NullPointerException) {}
        setContentView(R.layout.activity_main)

        val buttonnotepad = findViewById<Button>(R.id.notas)
            buttonnotepad.setOnClickListener{

                val intent = Intent(this, Notepad::class.java)
                startActivity(intent)
            }
        // Get input text
       // val inputText = outlinedTextField.editText?.text.toString()

        //outlinedTextField.editText?.doOnTextChanged { inputText, _, _, _ ->
            // Respond to input text change
        //}

        /*Commit Test*/
        /*New Stuff*/



    }
}