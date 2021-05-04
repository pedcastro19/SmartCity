package ipvc.estg.smartcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val button = findViewById<Button>(R.id.rollback)
        button.setOnClickListener{
            val intent = Intent(this, Notepad::class.java)
            startActivity(intent)
        }

    }
}