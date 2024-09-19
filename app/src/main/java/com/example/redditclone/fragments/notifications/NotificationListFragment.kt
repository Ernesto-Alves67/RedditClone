package com.example.redditclone.fragments.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.redditclone.databinding.FragmentNotificationListBinding


/**
 * A simple [Fragment] subclass.
 * Use the [NotificationListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationListFragment : Fragment() {

    private var _binding: FragmentNotificationListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inicializando o binding com o layout do fragmento
        _binding = FragmentNotificationListBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        // Defina os listeners dos elementos de UI aqui, usando `binding` para acessar as views
        // Exemplo:
        // binding.someButton.setOnClickListener { /* ação aqui */ }
    }
}