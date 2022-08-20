package com.model

import java.text.SimpleDateFormat
import java.util.*

data class Messages(
    var id: String? = null,
    var content: String? = null,
    // change string to long
    var dateTime: Long? = null,
    var senderName: String? = null,
    var senderId: String? = null,
    var roomID: String? = null
) {
    fun formatDateTime(): String {
        val date = Date(dateTime!!)
        // simple date format
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return simpleDateFormat.format(date)
    }
    companion object {
        val COLLECTION_NAME = "messages"
    }
}
