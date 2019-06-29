package jp.cordea.coroutinechooser

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import kotlinx.coroutines.channels.Channel

class CoroutineChooser(
    private val intent: Intent,
    private val title: String,
    private val requestCode: Int
) {
    private val channel = Channel<ChooserResult>()

    suspend fun openChooser(activity: Activity): ChooserResult {
        val chooser = Intent.createChooser(intent, title)
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivityForResult(chooser, requestCode)
        }
        return channel.receive()
    }

    suspend fun openChooser(fragment: Fragment): ChooserResult {
        val chooser = Intent.createChooser(intent, title)
        if (intent.resolveActivity(fragment.requireActivity().packageManager) != null) {
            fragment.startActivityForResult(chooser, requestCode)
        }
        return channel.receive()
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (this.requestCode == requestCode) {
            channel.offer(
                if (resultCode == Activity.RESULT_OK) {
                    ChooserResult.Success(data)
                } else {
                    ChooserResult.Failure
                }
            )
        }
    }
}
