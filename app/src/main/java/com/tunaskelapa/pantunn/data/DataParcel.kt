package com.tunaskelapa.pantunn.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataParcel (
    val genre: String?,
    val bait1: String?,
    val bait2: String?,
    val bait3: String?,
    val bait4: String?
) : Parcelable