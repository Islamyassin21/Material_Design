package com.example.islam.materialdesign;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islam.materialdesign.Tap.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;
    private SlidingTabLayout mTaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.Drawer_fragment);
        drawerFragment.setUp(R.id.Drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mTaps = (SlidingTabLayout) findViewById(R.id.tab);
        mTaps.setDistributeEvenly(true);
        mTaps.setCustomTabView(R.layout.costume_tab_view, R.id.tabtext);
        mTaps.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTaps.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mTaps.setViewPager(mPager);

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        int[] icone = {R.drawable.button_action_red, R.drawable.ic_action_new, R.drawable.ic_action_calendar, R.drawable.ic_abstract};
        String[] tabs = getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Drawable drawable = getResources().getDrawable(icone[position]);
            drawable.setBounds(0, 0, 36, 36);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        @Override
        public int getCount() {
            return icone.length;
        }

    } // An official of the pager tabs from icon or title


    public static class MyFragment extends Fragment {

        public View layout = null;
        public Toolbar toolbar;

        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        private static final int TAB_ONE = 0;
        private static final int TAB_TWO = 1;
        private static final int TAB_THREE = 2;
        private static final int TAB_FOUR = 3;


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle context) {

            Bundle bundle = getArguments();
            int i = bundle.getInt("position"); //Number of Tab
            layout = inflater.inflate(R.layout.first_tab, container, false);

            return layout;
        }
    } // an official of the page in evry fragment


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "HOME", Toast.LENGTH_LONG).show();
                break;
            case R.id.first:
                Intent i = new Intent(this, SecondClass.class);
                startActivity(i);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}


// fdd48e41b90e08935f26c50956beff24
// https://api.themoviedb.org/3/movie/550?api_key=fdd48e41b90e08935f26c50956beff24