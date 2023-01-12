package com.rahulghag.conduit.common

import android.content.Context
import androidx.annotation.StringRes

sealed class UiMessage {
    data class DynamicMessage(val value: String) : UiMessage()
    class StringResource(
        @StringRes val resourceId: Int,
        vararg val formatArgs: Any
    ) : UiMessage()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicMessage -> value
            is StringResource -> context.getString(resourceId, *formatArgs)
        }
    }
}