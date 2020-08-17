package fullpagedeveloper.com.livedatademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        /**
         * Menggunakan @lambda {it}
         */
        viewModel.seconds().observe(this@MainActivity, Observer {
            text_number.text = it.toString()
            Log.d("TAG", " $it")
        })

        /**
         * Menggunakan @lambda {finish ->}
         */
        viewModel.finished.observe(this, Observer { finish ->
            if (finish) {
                Toast.makeText(this, "Finished!", Toast.LENGTH_SHORT).show()
            }
        })

        button_Start.setOnClickListener {
            if (editTextInputText.text.isEmpty() || editTextInputText.length() < 4){
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.timeValue.value = editTextInputText.text.toString().toLong()
                viewModel.startTimer()
            }
        }

        button_Stop.setOnClickListener {
            text_number.text = "0"
            viewModel.stopTimer()
        }
    }
}