package com.example.islam.materialdesign;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private RecyclerView recyclerView;

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private MyAdapter adapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPrefrences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new MyAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "Short Listener " + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), "Long Listener " + position, Toast.LENGTH_LONG).show();
            }
        }));

        return layout;
    }

    public static List<information> getData() {

        List<information> data = new ArrayList<>();
        int[] icon = {R.drawable.button_action_red, R.drawable.button_action_red_touch, R.drawable.ic_abstract, R.drawable.ic_action_alphabets};
        String[] title = {"Islam", "Mohammed", "Yassin", "Taha"};

        for (int i = 0; i < 100; i++) {
            information current = new information();
            current.iconId = icon[i % icon.length];
            current.title = title[i % title.length];
            data.add(current);
        }

        return data;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
//        {
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//
//                if (!mUserLearnedDrawer) {
//                    mUserLearnedDrawer = true;
//                    saveToPrefrences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
//                }
//                getActivity().invalidateOptionsMenu();
//
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//
//                getActivity().invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                if (slideOffset < 0.6) {
//                    toolbar.setAlpha(1 - slideOffset);
//                }
//            }
//        };
//
//        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
//            mDrawerLayout.openDrawer(containerView);
//        }

        //mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                    super.onLongPress(e);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {

                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener {

        public void onClick(View view, int postion);

        public void onLongClick(View view, int position);
    }


    /**************************************
     * (I don't use even now)
     *********************************/

    public static void saveToPrefrences(Context context, String prefrencesName, String prefrencesValue) {

        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(prefrencesName, prefrencesValue);
        editor.apply();
    }

    public static String readFromPrefrences(Context context, String prefrencesName, String defultValue) {

        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(prefrencesName, defultValue);

    }
}
