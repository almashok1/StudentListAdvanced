package kz.adamant.studentlistadvanced

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kz.adamant.studentlistadvanced.databinding.ItemStudentBinding

class StudentsListAdapter(private val onItemClicked: OnItemClicked): RecyclerView.Adapter<StudentsListAdapter.ViewHolder>() {
    private var items = emptyList<Student>()

    fun setItems(items: List<Student>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStudentBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: ItemStudentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(student: Student) {
            binding.name.text = student.name ?: "No Data"
            binding.surname.text = student.surname ?: "No Data"
            binding.grade.text = if (student.grade != null) student.grade.toString() else "No Grade"
            binding.btnDelete.setOnClickListener {
                this@StudentsListAdapter.onItemClicked.deleteItem(student)
            }
            binding.root.setOnClickListener {
                this@StudentsListAdapter.onItemClicked.navigateToInfoPage(student)
            }
            if (!student.image.isNullOrEmpty()) {
                val imageBytes = Base64.decode(student.image, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.image.setImageBitmap(decodedImage)
            } else {
                binding.image.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_person))
            }
        }
    }
}