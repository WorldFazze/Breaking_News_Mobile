package com.example.breakingnews.domain.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList


data class NewsItem(
    val title: String?,
    val link: String?,
    val keywords: ArrayList<String>?,
    val creator: ArrayList<String>?,
    val video_url: String?,
    val description: String?,
    val content: String?,
    val pubDate: String?,
    val image_url: String?,
    val source_id: String?,
    val category: ArrayList<String>?,
    val country: ArrayList<String>?,
    val language: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(link)
        parcel.writeStringList(keywords)
        parcel.writeStringList(creator)
        parcel.writeString(video_url)
        parcel.writeString(description)
        parcel.writeString(content)
        parcel.writeString(pubDate)
        parcel.writeString(image_url)
        parcel.writeString(source_id)
        parcel.writeStringList(category)
        parcel.writeStringList(country)
        parcel.writeString(language)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsItem> {
        override fun createFromParcel(parcel: Parcel): NewsItem {
            return NewsItem(parcel)
        }

        override fun newArray(size: Int): Array<NewsItem?> {
            return arrayOfNulls(size)
        }
    }
}