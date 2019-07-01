package jp.cordea.coroutinechooser.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.cordea.coroutinechooser.ChooserResult
import jp.cordea.coroutinechooser.createChooser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val chooser = createChooser(
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "hello")
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chooserButton.setOnClickListener {
            launch {
                when (val result = chooser.openChooser(this@MainActivity)) {
                    is ChooserResult.Success -> Timber.i("${result.data}")
                    ChooserResult.Failure -> Timber.i("failure")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        chooser.handleActivityResult(requestCode, resultCode, data)
    }
}
