package kz.adamant.studentlistadvanced

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kz.adamant.studentlistadvanced.databinding.FragmentStudentsListBinding


class StudentsListFragment : Fragment(R.layout.fragment_students_list), OnItemClicked {
    private lateinit var binding: FragmentStudentsListBinding
    private lateinit var adapter: StudentsListAdapter
    private lateinit var studentsDB: StudentsDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studentsDB = StudentsDB.getInstance()
        adapter = StudentsListAdapter(this)
        for (i in 0..10) {
            studentsDB.addStudent(Student(id = studentsDB.getId(), name = "John $i", image = SAMPLE_BASE64))
            studentsDB.incrementId()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStudentsListBinding.bind(view)

        adapter.setItems(studentsDB.getStudents())

        binding.run {
            addButton.setOnClickListener(::addStudent)
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    private fun addStudent(view: View?) {
        val name = binding.textInput.text.toString()
        val student = if (name.trim().isEmpty())
            Student(id = studentsDB.getId())
        else
            Student(id = studentsDB.getId(), name = name)
        Log.d("asd", "addStudent: $student")
        if (studentsDB.hasStudent(student)) {
            Toast.makeText(context, "Duplicate Student!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Student has successfully added!", Toast.LENGTH_SHORT).show()
            studentsDB.incrementId()
            studentsDB.addStudent(student)
            updateList()
        }
    }

    private fun updateList() {
        adapter.setItems(studentsDB.getStudents())
    }

    override fun navigateToInfoPage(student: Student) {
        findNavController().navigate(
            R.id.action_studentsListFragment_to_studentInfoFragment, bundleOf(
                Pair(
                    STUDENT, student
                )
            )
        )
    }

    override fun deleteItem(student: Student) {
        studentsDB.deleteStudent(student)
        adapter.setItems(studentsDB.getStudents())
    }

    override fun onDestroy() {
        super.onDestroy()
        studentsDB.destroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.restore) {
            val success = studentsDB.restoreFromTrash()
            return if (success) {
                adapter.setItems(studentsDB.getStudents())
                Snackbar.make(binding.root, "Successfully restored", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.teal_700))
                    .show()
                true
            } else {
                Snackbar.make(binding.root, "No item to restore", Snackbar.LENGTH_SHORT)
                    .show()
                false
            }
        }
        return super.onOptionsItemSelected(item)
    }
}