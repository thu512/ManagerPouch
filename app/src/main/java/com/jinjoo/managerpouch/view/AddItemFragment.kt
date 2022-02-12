package com.jinjoo.managerpouch.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jinjoo.managerpouch.R
import com.jinjoo.managerpouch.databinding.FragmentAddItemBinding
import com.jinjoo.managerpouch.util.autoCleared
import com.jinjoo.managerpouch.viewmodel.MainViewModel


class AddItemFragment : Fragment() {

    companion object {
        fun newInstance() = AddItemFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var binding by autoCleared<FragmentAddItemBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initButton()
    }

    private fun initButton() {
        binding.exit.setOnClickListener {

            val directions = AddItemFragmentDirections.actionAddItemToMain2()
            findNavController().navigate(directions)

        }
    }
}

