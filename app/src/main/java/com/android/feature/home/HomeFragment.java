package com.android.feature.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.MainActivity;
import com.android.R;
import com.android.databinding.FragmentHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolBarVsDrawer();
        initNavigationView();
    }

    // SETUP TOOLBAR & DRAWER.
    private void initToolBarVsDrawer(){
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            Toolbar toolbar = binding.Toolbar;
            activity.setSupportActionBar(toolbar);

            DrawerLayout drawerLayout = binding.DrawerLayout;
            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                    R.string.nav_open, R.string.nav_close);
            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }
    }

    // SETUP NAVIGATION VIEW.
    private void initNavigationView() {
        binding.NavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                NavController navController = Navigation.findNavController(binding.fragmentContainerView);
                int destinationId = Objects.requireNonNull(navController.getCurrentDestination()).getId();
                if (itemId == R.id.mHome && destinationId != R.id.ticketFragment) {
                    navController.navigate(R.id.action_profileFragment_to_ticketFragment);
                    binding.DrawerLayout.close();
                } else if (itemId == R.id.mProfile && destinationId != R.id.profileFragment) {
                    navController.navigate(R.id.action_ticketFragment_to_profileFragment);
                    binding.DrawerLayout.close();
                } else if (itemId == R.id.mLogout) {
                    View view = getView();
                    if (view != null) {
                        // HANDLE LOGOUT LOGIC & REDIRECT TO LOGIN PAGE.
                        Navigation.findNavController(view).popBackStack();
                    }
                }

                return true;
            }
        });
    }

}