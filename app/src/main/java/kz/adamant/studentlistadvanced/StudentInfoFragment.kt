package kz.adamant.studentlistadvanced

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kz.adamant.studentlistadvanced.databinding.FragmentStudentInfoBinding

class StudentInfoFragment : Fragment(R.layout.fragment_student_info) {
    private lateinit var binding: FragmentStudentInfoBinding
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStudentInfoBinding.bind(view)
        navController = findNavController()

        val student = requireArguments().getParcelable<Student>(STUDENT)
        student?.let {
            binding.tvId.text = it.id.toString()
            binding.tvName.text = it.name ?: "No Data"
            binding.tvSurname.text = it.surname ?: ""
            binding.tvGrade.text = if (it.grade != null) it.grade.toString() else "No Grade"
        }
    }
}