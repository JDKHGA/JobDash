package com.gyasilarbi.jobdash

import android.os.Parcel
import android.os.Parcelable

data class Category(
    val name: String?,
    val logoResourceId: Int,
    val description: String?,
    val price: String?,
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(logoResourceId)
        parcel.writeString(description)
        parcel.writeString(price)
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
    Category("Carpenter", R.drawable.cat1, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Electrician", R.drawable.cat2, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Plumber", R.drawable.cat3, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Carpenter", R.drawable.cat1, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Electrician", R.drawable.cat2, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Plumber", R.drawable.cat3, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Carpenter", R.drawable.cat1, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Electrician", R.drawable.cat2, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Plumber", R.drawable.cat3, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Carpenter", R.drawable.cat1, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Electrician", R.drawable.cat2, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Plumber", R.drawable.cat3, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Carpenter", R.drawable.cat1, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Electrician", R.drawable.cat2, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
    Category("Plumber", R.drawable.cat3, "The Electrician position involves installing, maintaining, and repairing electrical systems and equipment in various settings.", "$20.00"),
)