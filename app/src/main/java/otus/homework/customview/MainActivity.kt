package otus.homework.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import otus.homework.customview.databinding.ActivityMainBinding
import otus.homework.customview.utils.inputStreamToString

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val json = resources.openRawResource(R.raw.payload).inputStreamToString()
        val listPayload = Gson().fromJson(json, Array<Payload>::class.java)
        binding.diagram.setValues(listPayload.map { it.amount})

    }
}