package ipvc.estg.smartcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var preferences: SharedPreferences

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
        preferences = getSharedPreferences("SharedLogin", Context.MODE_PRIVATE);
        val userPref = preferences.getString("USERNAME", null)
        val passPref = preferences.getString("PASSWORD", null)



    }
}