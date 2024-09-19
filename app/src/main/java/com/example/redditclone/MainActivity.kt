package com.example.redditclone

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.redditclone.databinding.ActivityMainBinding
import com.example.redditclone.fragments.AboutFragment
import com.example.redditclone.fragments.chat.ChatFragment
import com.example.redditclone.fragments.community.CommunityFragment
import com.example.redditclone.fragments.HomeFragment
import com.example.redditclone.fragments.ShareFragment
import com.example.redditclone.fragments.home.FragmentAdapter
import com.example.redditclone.fragments.home.TitlePagerAdapter
import com.example.redditclone.fragments.notifications.NotificationsFrag
import com.example.redditclone.fragments.profile.ProfileFragment
import com.example.redditclone.fragments.settings.SettingsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var titleAdapter: TitlePagerAdapter
    private lateinit var activeFragment: Fragment
    private lateinit var homeFragment: HomeFragment
    private lateinit var chatFragment: ChatFragment
    private lateinit var communityFragment: CommunityFragment
    private lateinit var notificationsFrag: NotificationsFrag
    private var isPager2Scrolling = false
    private var isPager1Scrolling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setVariables()
        setListeners()

        // Defina o HomeFragment como o fragmento inicial
        if (savedInstanceState == null) {
            //homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit()
            binding.dinamicTittle.text = "reddit"
            binding.icChevron.visibility = View.VISIBLE
            activeFragment = homeFragment
        }
    }

    private fun setVariables() {
        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout
        val adapter = FragmentAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = true
        binding.fragmentContainer.visibility = View.GONE
        binding.dinamicTittle.visibility = View.GONE
        titleAdapter = TitlePagerAdapter()
        binding.titleViewPager.adapter = titleAdapter
        binding.titleViewPager.clipToPadding = false // Permite exibir parte do próximo item
        binding.titleViewPager.setPadding(50, 0, 50, 0) // Ajuste conforme necessário

        homeFragment = HomeFragment()
        chatFragment = ChatFragment()
        communityFragment = CommunityFragment()
        notificationsFrag = NotificationsFrag()
    }

    private fun setListeners() {
        //binding.navView.setNavigationItemSelectedListener(this)

        binding.profileBtn.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        binding.menuLeft.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.titleId.setOnClickListener {
            if (binding.icChevron.rotation == 90f) {
                binding.icChevron.rotation = 270f
                slideDown(binding.typesFedd)
            } else {
                binding.icChevron.rotation = 90f
                slideUp(binding.typesFedd)
            }
            Log.d("LinearL Title", "Tittulo clicado")
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val itemId = item.itemId
            if (itemId != activeFragment.id) {
                val selectedFragment: Fragment?
                var toolbarTitle = ""
                val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

                when (itemId) {
                    R.id.home -> {
                        selectedFragment = homeFragment
                        toolbarTitle = ""
                        binding.icChevron.visibility = View.VISIBLE
                        binding.titleViewPager.visibility = View.VISIBLE
                        binding.dinamicTittle.visibility = View.GONE
                        binding.fragmentContainer.visibility = View.GONE
                        binding.viewPager.visibility = View.VISIBLE
                    }
                    R.id.notification -> {
                        selectedFragment = notificationsFrag
                        toolbarTitle = "Notifications"
                        binding.icChevron.visibility = View.GONE
                        binding.dinamicTittle.visibility = View.VISIBLE
                        binding.titleViewPager.visibility = View.GONE
                        binding.fragmentContainer.visibility = View.VISIBLE
                        binding.viewPager.visibility = View.GONE
                    }
                    R.id.chat -> {
                        selectedFragment = chatFragment
                        toolbarTitle = "Chats"
                        binding.icChevron.visibility = View.GONE
                        binding.dinamicTittle.visibility = View.VISIBLE
                        binding.titleViewPager.visibility = View.GONE
                        binding.fragmentContainer.visibility = View.VISIBLE
                        binding.viewPager.visibility = View.GONE
                    }
                    R.id.community -> {
                        selectedFragment = communityFragment
                        toolbarTitle = "Community"
                        binding.icChevron.visibility = View.GONE
                        binding.dinamicTittle.visibility = View.VISIBLE
                        binding.titleViewPager.visibility = View.GONE
                        binding.fragmentContainer.visibility = View.VISIBLE
                        binding.viewPager.visibility = View.GONE
                    }
                    else -> selectedFragment = null
                }

                selectedFragment?.let {
                    if (!it.isAdded) {
                        transaction.add(R.id.fragment_container, it)
                    }
                    supportFragmentManager.fragments.forEach { fragment ->
                        if (fragment != it) {
                            transaction.hide(fragment)
                        } else {
                            activeFragment.onPause()
                            transaction.show(it)
                        }
                    }
                    activeFragment = it
                    transaction.commit()
                    binding.dinamicTittle.text = toolbarTitle
                }
            }
            true
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (!isPager2Scrolling) {
                    isPager1Scrolling = true
                    binding.titleViewPager.scrollTo(positionOffsetPixels, 0)
                    binding.titleViewPager.setCurrentItem(position, true)
                }
                isPager1Scrolling = false
            }
        })

    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        val itemId = item.itemId
//        Log.d("MainActivity", "Selected item ID: $itemId")
//
//        when (itemId) {
//            R.id.nav_Profile -> {
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).commit()
//            }
//            R.id.nav_settings -> {
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SettingsFragment()).commit()
//            }
//            R.id.nav_share -> {
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ShareFragment()).commit()
//            }
//            R.id.nav_about -> {
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AboutFragment()).commit()
//            }
//            else -> throw IllegalStateException("Unexpected value: $itemId")
//        }
//
//        drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun slideDown(view: View) {
        view.visibility = View.VISIBLE
        view.alpha = 0.0f
        view.animate()
            .alpha(1.0f)
            .setDuration(300)
            .setListener(null)
    }

    private fun slideUp(view: View) {
        view.animate()
            .alpha(0.0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                }
            })
    }
}
