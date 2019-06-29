package jp.cordea.coroutinechooser

import android.content.Intent

private const val DEFAULT_REQUEST_CODE = 8273

@DslMarker
annotation class CoroutineChooserDsl

@CoroutineChooserDsl
class CoroutineChooserBuilder(private val intent: Intent) {
    var title: String = ""
    var requestCode: Int = DEFAULT_REQUEST_CODE

    fun build(): CoroutineChooser = CoroutineChooser(intent, title, requestCode)
}

fun createChooser(intent: Intent, action: CoroutineChooserBuilder.() -> Unit = { }) =
    CoroutineChooserBuilder(intent).apply { action() }.build()
