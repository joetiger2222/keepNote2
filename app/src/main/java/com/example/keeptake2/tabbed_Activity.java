package com.example.keeptake2;

import static com.example.keeptake2.WritingNoteFragment.*;

import android.app.ActionBar;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.example.keeptake2.ui.main.SectionsPagerAdapter;
import com.example.keeptake2.databinding.ActivityTabbedBinding;


public class tabbed_Activity extends AppCompatActivity {
    private ActivityTabbedBinding binding;
    static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTabbedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 1:
                    Toast.makeText(tabbed_Activity.this, "please note that this part is still in beta version 12/7/2022", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 1:
                        Toast.makeText(tabbed_Activity.this, "please note that this part is still in beta version 12/7/2022", Toast.LENGTH_SHORT).show();
                }
            }
        });
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

    }

    @Override
    public void onBackPressed() {
        if(grayLinkedList.isEmpty()) super.onBackPressed();
        else{
            chngAllPstnsWhite();
            hideDeleteBtn(deleteBtn);
            hidePinBtn(pinBtn);
            grayLinkedList.clear();
            willbeDeleted.clear();
        }
    }


}