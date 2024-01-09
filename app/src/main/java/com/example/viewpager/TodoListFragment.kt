package com.example.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpager.adapters.TodoListRecycleViewAdapter
import com.example.viewpager.models.todoList.TodoTaskDB
import com.example.viewpager.models.todoList.TodoTaskState

const val TODO_LIST_STATE = "state"
class TodoListFragment : Fragment() {
    private lateinit var status: TodoTaskState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val status = arguments?.getString(TODO_LIST_STATE)
        status?.let {
            this.status = TodoTaskState.valueOf(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.todoListRv)
        rv.adapter = TodoListRecycleViewAdapter(TodoTaskDB.tasks().filter { t -> t.status == this.status })
        rv.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(todoListState: TodoTaskState) =
            TodoListFragment().apply {
                arguments = Bundle().apply {
                    putString(TODO_LIST_STATE, todoListState.toString())
                }
            }
    }
}