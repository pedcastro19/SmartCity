package ipvc.estg.smartcity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import ipvc.estg.smartcity.api.EndPoints
import ipvc.estg.smartcity.api.ServiceBuilder
import ipvc.estg.smartcity.api.User
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val ButLogin = findViewById<Button>(R.id.login)

        if (userPref.isNullOrBlank() || passPref.isNullOrBlank()) {

            ButLogin.setOnClickListener {

                var password = findViewById<TextInputEditText>(R.id.passwordtext)
                var username = findViewById<TextInputEditText>(R.id.usernametext)

                val usernameStr = username.text.toString()
                val passwordStr = password.text.toString()

                if(usernameStr.isNullOrBlank() || passwordStr.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, getResources().getString(R.string.preenchercampos), Toast.LENGTH_SHORT).show()
                }
                else{
                    Login(usernameStr, passwordStr)
                }
            }
        }

        else {

            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    fun Login(user: String, pass: String) {


        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getUserByNome(user)

        val intent = Intent(this, TestActivity::class.java)

        call.enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.isSuccessful){
                    val c: User = response.body()!!

                    if(c.password.equals(pass)) {

                        val editor: SharedPreferences.Editor = preferences.edit()

                        editor.putString("USERNAME", user)
                        editor.putString("PASSWORD", pass)
                        editor.apply()

                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this@MainActivity, getResources().getString(R.string.passworderrada), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }




}