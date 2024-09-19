package com.example.redditclone.fragments.notifications

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.redditclone.fragments.AboutFragment

class NotificationsPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragmentTitles: List<String>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragmentTitles.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (fragmentTitles[position]) {
            "Notificações" -> NotificationListFragment()
            "Mensagens" -> AboutFragment()
            else -> throw IllegalArgumentException("Unknown fragment title")
        }
    }
}
