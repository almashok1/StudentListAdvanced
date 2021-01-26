package kz.adamant.studentlistadvanced

interface OnItemClicked {
    fun navigateToInfoPage(student: Student)
    fun deleteItem(student: Student)
}