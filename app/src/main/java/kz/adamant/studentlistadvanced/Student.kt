package kz.adamant.studentlistadvanced

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Student(
    val id: Int,
    var name: String? = null,
    var surname: String? = null,
    var grade: Int? = null,
    var image: String? = null
) : Parcelable, Comparable<Student> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Student
        return name == that.name && surname == that.surname
    }

    override fun hashCode(): Int {
        return Objects.hash(name, surname)
    }

    override fun compareTo(other: Student): Int {
        return id.compareTo(other.id)
    }
}
