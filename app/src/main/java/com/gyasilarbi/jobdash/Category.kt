package com.gyasilarbi.jobdash

import android.os.Parcel
import android.os.Parcelable

data class Category(
    val name: String?,
    val logoResourceId: Int,
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(logoResourceId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object  CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}

val getCategory = listOf(
    Category("Carpenter", R.drawable.cat1),
    Category("Electrician", R.drawable.cat2),
    Category("Plumber", R.drawable.cat3),
    Category("Carpenter", R.drawable.cat1),
    Category("Electrician", R.drawable.cat2),
    Category("Plumber", R.drawable.cat3),
    Category("Carpenter", R.drawable.cat1),
    Category("Electrician", R.drawable.cat2),
    Category("Plumber", R.drawable.cat3),
    Category("Carpenter", R.drawable.cat1),
    Category("Electrician", R.drawable.cat2),
    Category("Plumber", R.drawable.cat3),
    Category("Carpenter", R.drawable.cat1),
    Category("Electrician", R.drawable.cat2),
    Category("Plumber", R.drawable.cat3),
)