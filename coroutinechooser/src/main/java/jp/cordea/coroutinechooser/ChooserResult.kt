package jp.cordea.coroutinechooser

import android.content.Intent

sealed class ChooserResult {
    class Success(val data: Intent?) : ChooserResult()
    object Failure : ChooserResult()
}
