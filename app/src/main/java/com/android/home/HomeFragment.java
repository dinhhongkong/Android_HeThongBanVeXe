package com.android.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.MainActivity;
import com.android.R;
import com.android.databinding.FragmentHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentHomeBinding mFragmentHomeBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mFragmentHomeBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_home, container, false);
        HomeModelView homeModelView = new HomeModelView();
        mFragmentHomeBinding.setHomeModelView(homeModelView);

        return mFragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // SETUP TOOLBAR & DRAWER.
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            Toolbar toolbar = mFragmentHomeBinding.Toolbar;
            activity.setSupportActionBar(toolbar);

            DrawerLayout drawerLayout = mFragmentHomeBinding.DrawerLayout;
            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                    R.string.nav_open, R.string.nav_close);
            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }
        // SETUP NAVIGATION VIEW.
        mFragmentHomeBinding.NavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        NavController navController = Navigation.findNavController(mFragmentHomeBinding.fragmentContainerView);
        int destinationId = Objects.requireNonNull(navController.getCurrentDestination()).getId();
        if (itemId == R.id.mHome && destinationId != R.id.bookTicketFragment) {
            navController.navigate(R.id.action_profileFragment_to_bookTicketFragment);
            mFragmentHomeBinding.DrawerLayout.close();
        } else if (itemId == R.id.mProfile && destinationId != R.id.profileFragment) {
            navController.navigate(R.id.action_bookTicketFragment_to_profileFragment);
            mFragmentHomeBinding.DrawerLayout.close();
        } else if (itemId == R.id.mLogout) {
            View view = getView();
            if (view != null) {
                // HANDLE LOGOUT LOGIC & REDIRECT TO LOGIN PAGE.
                Navigation.findNavController(view).popBackStack();
            }
        }

        return true;
    }
}