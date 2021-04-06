package ipvc.estg.smartcity

import android.os.Bundle
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


        // Get input text
       // val inputText = outlinedTextField.editText?.text.toString()

        //outlinedTextField.editText?.doOnTextChanged { inputText, _, _, _ ->
            // Respond to input text change
        //}

        /*Commit Test*/
        /*New Stuff*/



    }
}