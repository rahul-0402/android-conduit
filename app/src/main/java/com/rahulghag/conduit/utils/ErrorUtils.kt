package com.rahulghag.conduit.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rahulghag.conduit.R
import okhttp3.ResponseBody
import org.json.JSONObject

object ErrorUtils {
//     fun parseErrorResponse(errorBody: ResponseBody?): UiMessage {
//         val errorMessages = mutableListOf<String>()

//         errorBody?.let {
//             val errorResponse: JSONObject =
//                 JSONObject(errorBody.string()).getJSONObject("errors")
//             val keys: Iterator<String> = errorResponse.keys()
//             while (keys.hasNext()) {
//                 val key = keys.next()
//                 val errorJsonArray = errorResponse.getJSONArray(key)
//                 val typeToken = object : TypeToken<List<String>>() {}.type
//                 val errors =
//                     Gson().fromJson<List<String>>(errorJsonArray.toString(), typeToken)
//                 errors.forEach { value ->
//                     errorMessages.add("${key.capitalized()} $value")
//                 }
//             }
//         }
//         return if (errorMessages.isNotEmpty()) {
//             UiMessage.DynamicMessage(errorMessages.joinToString("\n"))
//         } else {
//             UiMessage.StringResource(R.string.error_something_went_wrong)
//         }
//     }
    
    fun parseErrorResponse(errorBody: ResponseBody?): UiMessage {
        return errorBody?.let {
            try {
                val errorResponse = JSONObject(it.string()).getJSONObject("errors")
                val errorMessages = errorResponse.keys().asSequence()
                    .map { key ->
                        val errorJsonArray = errorResponse.getJSONArray(key)
                        Gson().fromJson<List<String>>(errorJsonArray.toString()).map { value -> "${key.capitalize()} $value" }
                    }
                    .flatten()
                    .toList()
                if (errorMessages.isNotEmpty()) {
                    UiMessage.DynamicMessage(errorMessages.joinToString("\n"))
                } else {
                    UiMessage.StringResource(R.string.error_something_went_wrong)
                }
            } catch (e: JSONException) {
                UiMessage.StringResource(R.string.error_something_went_wrong)
            }
        } ?: UiMessage.StringResource(R.string.error_something_went_wrong)
    }
}
