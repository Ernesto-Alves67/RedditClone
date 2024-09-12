package com.example.redditclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.redditclone.databinding.ActivityMainBinding;
import com.example.redditclone.fragments.AboutFragment;
import com.example.redditclone.fragments.HomeFragment;
import com.example.redditclone.fragments.home.FragmentAdapter;
import com.example.redditclone.fragments.home.TitlePagerAdapter;
import com.example.redditclone.fragments.notifications.NotificationsFragment;
import com.example.redditclone.fragments.ShareFragment;
import com.google.android.material.navigation.NavigationView;

import com.example.redditclone.fragments.chat.ChatFragment;
import com.example.redditclone.fragments.community.CommunityFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    Toolbar toolbar_;
    TextView dynamicTitle;
    ImageView chev_ic;
    boolean isPager2Scrolling = false;
    boolean isPager1Scrolling = false;
    TitlePagerAdapter titleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariables();
        setListeners();

        // Defina o HomeFragment como o fragmento inicial
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .commit();
            dynamicTitle = findViewById(R.id.dinamicTittle);
            dynamicTitle.setText("reddit");
            chev_ic.setVisibility(View.VISIBLE);
        }

    }
    public void setVariables(){
        toolbar_ = binding.toolbar;
        setSupportActionBar(toolbar_);
        chev_ic = binding.icChevron;
        drawerLayout = binding.drawerLayout;
        FragmentAdapter adapter = new FragmentAdapter(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setUserInputEnabled(true);
        binding.fragmentContainer.setVisibility(View.GONE);
        binding.dinamicTittle.setVisibility(View.GONE);
        titleAdapter = new TitlePagerAdapter();
        binding.titleViewPager.setAdapter(titleAdapter);
        binding.titleViewPager.setClipToPadding(false);  // Permite exibir parte do próximo item
        binding.titleViewPager.setPadding(50, 0, 50, 0);  // Ajuste conforme necessário
        //binding.titleViewPager.setPageMargin(20);  // Espaçamento entre os títulos



    }
    public void setListeners(){
        binding.navView.setNavigationItemSelectedListener(this);

        binding.profileBtn.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        binding.menuLeft.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.titleId.setOnClickListener(v -> {

            if(chev_ic.getRotation() == 90){
                chev_ic.setRotation(270);
                slideDown(binding.typesFedd);
            }
            else{
                chev_ic.setRotation(90);
                slideUp(binding.typesFedd);
            }

            Log.d("LinearL Title", "Tittulo clicado ");
        });

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            String toolbarTitle = "";
            if (itemId == R.id.home) {
                selectedFragment = new HomeFragment();
                toolbarTitle = "";
                chev_ic.setVisibility(View.VISIBLE);
                binding.dinamicTittle.setVisibility(View.GONE);
                binding.fragmentContainer.setVisibility(View.GONE);
                binding.viewPager.setVisibility(View.VISIBLE);
            }
            //binding.dinamicTittle.setVisibility(View.VISIBLE);
            if (itemId == R.id.notification) {
                selectedFragment = new NotificationsFragment();
                toolbarTitle = "Notifications";
                chev_ic.setVisibility(View.GONE);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                binding.viewPager.setVisibility(View.GONE);
            }
            if (itemId == R.id.chat) {
                selectedFragment = new ChatFragment();
                toolbarTitle = "Chats";
                chev_ic.setVisibility(View.GONE);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                binding.viewPager.setVisibility(View.GONE);
            }
            if (itemId == R.id.community) {
                selectedFragment = new CommunityFragment();
                toolbarTitle = "Communitys";
                chev_ic.setVisibility(View.GONE);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                binding.viewPager.setVisibility(View.GONE);
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                if (!toolbarTitle.isEmpty()) {
                    binding.dinamicTittle.setText(toolbarTitle);
                    adjustToolbarTitle();
                }else{binding.dinamicTittle.setVisibility(View.GONE);}

            }
            return true;
        });

//        binding.titleViewPager.setPageTransformer((page, position) -> {
//            float scaleFactor = 0.85f + (1 - Math.abs(position)) * 0.15f;
//            page.setScaleX(scaleFactor);
//            page.setScaleY(scaleFactor);
//            page.setTranslationX(-30 * position);  // Diminua ou aumente o valor para ajustar o espaço
//        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                binding.titleViewPager.setCurrentItem(position, true);
//            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                // Evita loop infinito
                if (!isPager2Scrolling) {
                    isPager1Scrolling = true;
                    binding.titleViewPager.scrollTo(positionOffsetPixels, 0);  // Sincroniza o scroll
                    binding.titleViewPager.setCurrentItem(position, true);
                    if(position == titleAdapter.getItemCount()){
                        Log.d("Item Pager", "Item: " + position);
                    }
                }
                isPager1Scrolling = false;
            }
        });
//        binding.titleViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//
//                // Evita loop infinito
//                if (!isPager1Scrolling) {
//                    isPager2Scrolling = true;
//                    binding.viewPager.scrollTo(position, positionOffsetPixels);  // Sincroniza o scroll
//                }
//                isPager2Scrolling = false;
//            }
//
////            @Override
////            public void onPageSelected(int position) {
////                super.onPageSelected(position);
////                // Sincroniza a página selecionada
////                binding.viewPager.setCurrentItem(position, false);
////            }
//        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Log.d("MainActivity", "Selected item ID: " + itemId); // Log para depuração

        if (itemId == R.id.nav_Profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (itemId == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationsFragment()).commit();
        } else if (itemId == R.id.nav_share) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShareFragment()).commit();
        } else if (itemId == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
        } else if (itemId == R.id.nav_logout) {
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
        } else {
            throw new IllegalStateException("Unexpected value: " + itemId);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void adjustToolbarTitle() {
        toolbar_.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < toolbar_.getChildCount(); i++) {
                    if (toolbar_.getChildAt(i) instanceof TextView) {
                        TextView toolbarTitle = (TextView) toolbar_.getChildAt(i);
                        Toolbar.LayoutParams params = (Toolbar.LayoutParams) toolbarTitle.getLayoutParams();
                        params.setMarginEnd(0); // Ajuste conforme necessário
                        toolbarTitle.setLayoutParams(params);
                        break;
                    }
                }
            }
        });
    }
    // Método para animar o deslizar para baixo (mostrar)
    private void slideDown(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);

        // Animação de desvanecimento
        view.animate()
                .alpha(1.0f)
                .setDuration(300)
                .setListener(null);

/*        // Animação de deslize
        view.setTranslationY(-view.getHeight());
        view.animate()
                .translationY(0)
                .setDuration(300)
                .setListener(null);*/
    }

    // Método para animar o deslizar para cima (esconder)
    private void slideUp(final View view) {

        // Animação de desvanecimento
        view.animate()
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                    }
                });


    }



}
