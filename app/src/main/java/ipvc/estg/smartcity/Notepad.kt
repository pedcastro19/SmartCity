package ipvc.estg.smartcity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Notepad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notepad)
        try {this.supportActionBar!!.hide()}
        catch (e: NullPointerException) {}
        setContentView(R.layout.activity_main)




    }
}