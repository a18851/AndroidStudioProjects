package ipca.app.modeltest

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Date

data class Product(val description: String, val value: Double, val date: Date) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readDouble(),
        Date(parcel.readLong())
    ) {
    }

    override fun toString(): String {
        val formattedDate = SimpleDateFormat("dd/MM/yyyy").format(date)
        return "$description $value€ $formattedDate"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeDouble(value)
        parcel.writeLong(date.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
