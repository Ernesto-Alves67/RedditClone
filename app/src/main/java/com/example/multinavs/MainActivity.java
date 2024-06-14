package com.example.multinavs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multinavs.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import fragments.*;
import fragments.chat.ChatFragment;
import fragments.community.CommunityFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    LinearLayout title_id;
    TextView dynamicTitle;
    RelativeLayout typesFeed;
    ImageView chev_ic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        chev_ic = findViewById(R.id.ic_chevron);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton menu_left = findViewById(R.id.menu_left);
        ImageButton profileButton = findViewById(R.id.profile_btn);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
        menu_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        typesFeed = findViewById(R.id.typesFedd);
        title_id = findViewById(R.id.title_id);
        title_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chev_ic.getRotation() == 90){
                    chev_ic.setRotation(270);
                    slideDown(typesFeed);
                }
                else{
                    chev_ic.setRotation(90);
                    slideUp(typesFeed);
                }

                Log.d("LinearL Title", "Tittulo clicado ");
            }
        });

/*        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();*/

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                TextView dynamicTitle = findViewById(R.id.dinamicTittle);

                String toolbarTitle = "";
                if (itemId == R.id.home) {
                    selectedFragment = new HomeFragment();
                    toolbarTitle = "reddit";
                    chev_ic.setVisibility(View.VISIBLE);
                }
                if (itemId == R.id.notification) {
                    selectedFragment = new SettingsFragment();
                    toolbarTitle = "Notifications";
                    chev_ic.setVisibility(View.GONE);
                }
                if (itemId == R.id.chat) {
                    selectedFragment = new ChatFragment();
                    toolbarTitle = "Chats";
                    chev_ic.setVisibility(View.GONE);
                }
                if (itemId == R.id.community) {
                    selectedFragment = new CommunityFragment();
                    toolbarTitle = "Communitys";
                    chev_ic.setVisibility(View.GONE);
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    dynamicTitle.setText(toolbarTitle);
                    adjustToolbarTitle();
                }
                return true;
            }
        });

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Log.d("MainActivity", "Selected item ID: " + itemId); // Log para depuração

        if (itemId == R.id.nav_Profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (itemId == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
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
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < toolbar.getChildCount(); i++) {
                    if (toolbar.getChildAt(i) instanceof TextView) {
                        TextView toolbarTitle = (TextView) toolbar.getChildAt(i);
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


/*    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }*/
}
