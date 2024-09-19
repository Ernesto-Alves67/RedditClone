package com.example.redditclone.fragments.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.redditclone.databinding.FragmentNotificationsBinding
import com.google.android.material.tabs.TabLayoutMediator

class NotificationsFrag : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inicializando o binding com o layout do fragmento
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        setupUI()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        val fragmentTitles = listOf("Notificações", "Mensagens")
        val pagerAdapter = NotificationsPagerAdapter(requireActivity(), fragmentTitles)
        binding.notificationPager.adapter = pagerAdapter
        TabLayoutMediator(binding.notificationTabs, binding.notificationPager) {tab, position ->
            when (position) {
                0 -> tab.text = "Notificações"
                1 -> tab.text = "Mensagens"
            }
        }.attach()
    }
    private fun setListeners() {
        // Defina os listeners dos elementos de UI aqui, usando `binding` para acessar as views
        // Exemplo:
        // binding.someButton.setOnClickListener { /* ação aqui */ }
    }
}
