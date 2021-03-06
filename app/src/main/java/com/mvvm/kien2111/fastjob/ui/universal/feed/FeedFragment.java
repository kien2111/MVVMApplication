package com.mvvm.kien2111.fastjob.ui.universal.feed;

import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.fastjob.base.BaseFragment;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.binding.BindingAdapters;
import com.mvvm.kien2111.fastjob.databinding.FragmentFeedBinding;
import com.mvvm.kien2111.fastjob.ui.SplashActivity;
import com.mvvm.kien2111.fastjob.ui.depositfund.DepositFundActivity;
import com.mvvm.kien2111.fastjob.ui.listappointment.ListAppointmentActivity;
import com.mvvm.kien2111.fastjob.ui.universal.UniversalActivity;
import com.mvvm.kien2111.fastjob.ui.universal.common.ViewPagerAdapter;
import com.mvvm.kien2111.fastjob.ui.universal.feed.category.CategoryFragment;
import com.mvvm.kien2111.fastjob.ui.universal.feed.map.MapFragment;
import com.mvvm.kien2111.fastjob.ui.userprofile.UserProfileActivity;
import com.mvvm.kien2111.fastjob.util.RxBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.view.MotionEvent.ACTION_DOWN;

/**
 * Created by WhoAmI on 08/02/2018.
 */

public class FeedFragment extends BaseFragment<FeedViewModel,FragmentFeedBinding> {
    @Inject
    ViewPagerAdapter adapter;
    private int mTabClicked = -1;
    private float x;
    private float y;
    private static final int FIRST_COLOR = Color.parseColor("#263238");
    private static final int SECOND_COLOR = Color.parseColor("#ffffff");
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected FeedViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(FeedViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

    @Override
    protected int getBindVariable() {
        return BR.VMfeed;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setUpTabLayout();
        setUpToolbar();
        setOnClick();
        setUpUserData();
        return view;
    }

    private void setUpUserData() {
        mViewModel.getPreferenceLiveData().observe(this,user -> {
            if(user!=null){
                mFragmentBinding.name.setText(user.getRealname());
                BindingAdapters.setImageUrl(mFragmentBinding.imgavatar,
                        user.getAvatar(),
                        ContextCompat.getDrawable(this.getActivity(),R.drawable.defaultimage),
                        ContextCompat.getDrawable(this.getActivity(),R.drawable.errorimg));
                BindingAdapters.setImageUrl(mFragmentBinding.logoCompany,
                        user.getLogo_company(),
                        ContextCompat.getDrawable(this.getActivity(),R.drawable.defaultimage),
                        ContextCompat.getDrawable(this.getActivity(),R.drawable.errorimg));
                mFragmentBinding.email.setText(user.getEmail());
            }
        });
    }

    private void setupTouchEvents() {
        mFragmentBinding.tabLayout.getTabAt(0).setCustomView(new CustomTab(this.getContext(), 0));
        mFragmentBinding.tabLayout.getTabAt(0).getCustomView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == ACTION_DOWN
                        && mFragmentBinding.viewPager.getCurrentItem() != 0) {
                    mTabClicked = 0;
                    x = motionEvent.getRawX();
                    y = motionEvent.getRawY();
                }
                return false;
            }
        });

        mFragmentBinding.tabLayout.getTabAt(1).setCustomView(new CustomTab(this.getContext(), 1));
        mFragmentBinding.tabLayout.getTabAt(1).getCustomView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == ACTION_DOWN
                        && mFragmentBinding.viewPager.getCurrentItem() != 1) {
                    mTabClicked = 1;
                    x = motionEvent.getRawX();
                    y = motionEvent.getRawY();
                }
                return false;
            }
        });

        mFragmentBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mTabClicked != -1) {
                    mTabClicked = -1;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageSelected(int position) {
                if (position == mTabClicked) {
                    ((CustomTab) mFragmentBinding.tabLayout.getTabAt(position).getCustomView()).setIsRevealing(true);
                    reveal(position);
                } else if (position == 0
                        && !((CustomTab) mFragmentBinding.tabLayout.getTabAt(0).getCustomView()).isIsRevealing()) {
                    mFragmentBinding.background.setBackgroundColor(FIRST_COLOR);
                    ((TextView)mFragmentBinding.tabLayout.getTabAt(0).getCustomView().findViewById(R.id.title)).setTextColor(Color.WHITE);
                    ((TextView)mFragmentBinding.tabLayout.getTabAt(1).getCustomView().findViewById(R.id.title)).setTextColor(Color.WHITE);
                    mFragmentBinding.toolbar.setTitleTextColor(Color.WHITE);
                    drawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
                    mFragmentBinding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
                    ((UniversalActivity)getActivity()).bottomBarColorMapInActive();
                } else if (position == 1
                        && !((CustomTab) mFragmentBinding.tabLayout.getTabAt(1).getCustomView()).isIsRevealing()) {
                    mFragmentBinding.background.setBackgroundColor(SECOND_COLOR);
                    ((TextView)mFragmentBinding.tabLayout.getTabAt(0).getCustomView().findViewById(R.id.title)).setTextColor(Color.BLACK);
                    ((TextView)mFragmentBinding.tabLayout.getTabAt(1).getCustomView().findViewById(R.id.title)).setTextColor(Color.BLACK);
                    mFragmentBinding.toolbar.setTitleTextColor(Color.BLACK);
                    drawerToggle.getDrawerArrowDrawable().setColor(Color.BLACK);
                    mFragmentBinding.tabLayout.setSelectedTabIndicatorColor(Color.BLACK);
                    ((UniversalActivity)getActivity()).bottomBarColorMapActive();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void reveal(final int tabPosition) {
        mFragmentBinding.reveal.setVisibility(View.VISIBLE);
        int cx = mFragmentBinding.reveal.getWidth();
        int cy = mFragmentBinding.reveal.getHeight();

        float finalRadius = Math.max(cx, cy) * 1.2f;
        Animator reveal = ViewAnimationUtils
                .createCircularReveal(mFragmentBinding.reveal, (int) x, (int) y, 0f, finalRadius);

        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                if (tabPosition == 0) {
                    mFragmentBinding.background.setBackgroundColor(FIRST_COLOR);
                    ((TextView)mFragmentBinding.tabLayout.getTabAt(0).getCustomView().findViewById(R.id.title)).setTextColor(Color.WHITE);
                    ((TextView)mFragmentBinding.tabLayout.getTabAt(1).getCustomView().findViewById(R.id.title)).setTextColor(Color.WHITE);
                    ((CustomTab) mFragmentBinding.tabLayout.getTabAt(0).getCustomView()).setIsRevealing(false);
                    mFragmentBinding.toolbar.setTitleTextColor(Color.WHITE);
                    drawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
                    mFragmentBinding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
                    ((UniversalActivity)getActivity()).bottomBarColorMapInActive();
                } else {
                    mFragmentBinding.background.setBackgroundColor(SECOND_COLOR);
                    ((TextView)mFragmentBinding.tabLayout.getTabAt(0).getCustomView().findViewById(R.id.title)).setTextColor(Color.BLACK);
                    ((TextView)mFragmentBinding.tabLayout.getTabAt(1).getCustomView().findViewById(R.id.title)).setTextColor(Color.BLACK);
                    ((CustomTab) mFragmentBinding.tabLayout.getTabAt(1).getCustomView()).setIsRevealing(false);
                    mFragmentBinding.toolbar.setTitleTextColor(Color.BLACK);
                    drawerToggle.getDrawerArrowDrawable().setColor(Color.BLACK);
                    mFragmentBinding.tabLayout.setSelectedTabIndicatorColor(Color.BLACK);
                    ((UniversalActivity)getActivity()).bottomBarColorMapActive();
                }
                mFragmentBinding.reveal.setVisibility(View.INVISIBLE);
                mTabClicked = -1;
            }
        });

        if (tabPosition == 0) {
            mFragmentBinding.reveal.setBackgroundColor(FIRST_COLOR);
            ((TextView)mFragmentBinding.tabLayout.getTabAt(0).getCustomView().findViewById(R.id.title)).setTextColor(Color.WHITE);
            ((TextView)mFragmentBinding.tabLayout.getTabAt(1).getCustomView().findViewById(R.id.title)).setTextColor(Color.WHITE);
            mFragmentBinding.toolbar.setTitleTextColor(Color.WHITE);
            drawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
            mFragmentBinding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
            ((UniversalActivity)getActivity()).bottomBarColorMapInActive();
        } else {
            mFragmentBinding.reveal.setBackgroundColor(SECOND_COLOR);
            ((TextView)mFragmentBinding.tabLayout.getTabAt(0).getCustomView().findViewById(R.id.title)).setTextColor(Color.BLACK);
            ((TextView)mFragmentBinding.tabLayout.getTabAt(1).getCustomView().findViewById(R.id.title)).setTextColor(Color.BLACK);
            mFragmentBinding.toolbar.setTitleTextColor(Color.BLACK);
            drawerToggle.getDrawerArrowDrawable().setColor(Color.BLACK);
            mFragmentBinding.tabLayout.setSelectedTabIndicatorColor(Color.BLACK);
            ((UniversalActivity)getActivity()).bottomBarColorMapActive();
        }
        reveal.start();
    }


    public void onClickViewAppointment(View v){
        Intent intent = new Intent(this.getActivity(), ListAppointmentActivity.class);
        startActivity(intent);
    }

    public void onClickHandleLogout(View v) {
        Account account = mViewModel.getCurrentAccout();
        mAccountManager.invalidateAuthToken(account.type,
                mAccountManager.peekAuthToken(account, AccountAuthenticator.AUTHTOKEN_TYPE_BEARER_LABEL));
        mAccountManager.clearPassword(account);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if(mAccountManager.removeAccountExplicitly(account)){
                successRemoveAccount();
            }else{
                failRemoveAccount();
            }
        }else{
            mAccountManager.removeAccount(account,future -> {
                try {
                    if(future.getResult()){
                        //success remove account
                        successRemoveAccount();
                    }else{
                        //fail to remove account
                        failRemoveAccount();
                    }
                } catch (OperationCanceledException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AuthenticatorException e) {
                    e.printStackTrace();
                }
            },null);
        }
    }

    private void setupToolbarDimens() {
        int h = getStatusBarHeight() + getActionBarSIze();
        Log.d("log", "setupToolbarDimens: " + h);
        /*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(0, getStatusBarHeight(), 0, 0);

        mFragmentBinding.toolbar.setLayoutParams(params);*/


        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                getStatusBarHeight() + getActionBarSIze() * 2
        );
        mFragmentBinding.background.setLayoutParams(params2);
        mFragmentBinding.reveal.setLayoutParams(params2);
    }



    public int getActionBarSIze() {
        int result = 0;
        TypedValue tv = new TypedValue();
        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            result = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        //Toast.makeText(this,"shit",Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private void successRemoveAccount(){
        Intent intent = new Intent(this.getActivity(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void failRemoveAccount(){
        ((UniversalActivity)getActivity()).showDialog("Error","Can't log out");
    }

    private void setOnClick() {
        RxBinding.fromViewRaplidClick(mFragmentBinding.detailProfileBtn)
                .observeOn(AndroidSchedulers.mainThread())
                .throttleLast(4, TimeUnit.SECONDS)
                .subscribe(this::onClickViewDetailProfile);
        RxBinding.fromViewRaplidClick(mFragmentBinding.depositFundBtn)
                .observeOn(AndroidSchedulers.mainThread())
                .throttleLast(4,TimeUnit.SECONDS)
                .subscribe(this::onClickViewDepositFund);
        RxBinding.fromViewRaplidClick(mFragmentBinding.viewAppointment)
                .observeOn(AndroidSchedulers.mainThread())
                .throttleLast(4,TimeUnit.SECONDS)
                .subscribe(this::onClickViewAppointment);
        RxBinding.fromViewRaplidClick(mFragmentBinding.logoutBtn)
                .observeOn(AndroidSchedulers.mainThread())
                .throttleLast(4,TimeUnit.SECONDS)
                .subscribe(this::onClickHandleLogout);
    }

    public void onClickViewDetailProfile(View v){
        Intent intent = new Intent(this.getActivity(),UserProfileActivity.class);
        startActivity(intent);
    }

    public void onClickViewDepositFund(View v){
        Intent intent = new Intent(this.getActivity(), DepositFundActivity.class);
        startActivity(intent);
    }

    private void setUpToolbar(){
       UniversalActivity activity = (UniversalActivity)getActivity();
        activity.setSupportActionBar(mFragmentBinding.toolbar);
        drawerToggle = new ActionBarDrawerToggle(activity,mFragmentBinding.drawerlayout,mFragmentBinding.toolbar,R.string.drawer_open,R.string.drawer_close);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();
        mFragmentBinding.drawerlayout.addDrawerListener(drawerToggle);
        setupToolbarDimens();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter.addFragment(new CategoryFragment(),"Categories");
        adapter.addFragment(new MapFragment(),"Map");
    }


    private void setUpTabLayout(){
        mFragmentBinding.viewPager.setAdapter(adapter);
        mFragmentBinding.tabLayout.setupWithViewPager(mFragmentBinding.viewPager);
        setupTouchEvents();
    }

}
