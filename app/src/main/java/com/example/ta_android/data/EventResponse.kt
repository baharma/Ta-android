package com.example.ta_android.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

class EventResponse(
    @field:SerializedName("error")
    val error : Boolean,

    @field:SerializedName("message")
    val message : String,

    @field:SerializedName("listEvent")
    val listEvent : List<ListEventResponse>
)
@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class ListEventResponse(
    @field:SerializedName("id")
    val id : String,
    @field:SerializedName("title_name")
    val title_name : String,
    @field:SerializedName("slug")
    val slug : String,
    @field:SerializedName("description")
    val imageUrl : String,
    @field:SerializedName("address_event")
    val address_event : String,
    @field:SerializedName("start_register")
    val start_register : Timestamp,
    @field:SerializedName("end_register")
    val end_register: Timestamp,
    @field:SerializedName("tlp")
    val tlp : String
) : Parcelable

