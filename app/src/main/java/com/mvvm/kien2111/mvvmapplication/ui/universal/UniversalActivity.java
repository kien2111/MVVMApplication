package com.mvvm.kien2111.mvvmapplication.ui.universal;

import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.binding.BindingAdapters;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RecentSearch;
import com.mvvm.kien2111.mvvmapplication.databinding.ActivityUniversalBinding;
import com.mvvm.kien2111.mvvmapplication.ui.SplashActivity;
import com.mvvm.kien2111.mvvmapplication.ui.depositfund.DepositFundActivity;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.ListAppointmentActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.NavigationController;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.SearchViewModel;
import com.mvvm.kien2111.mvvmapplication.ui.universal.search.searchresult.SearchActivity;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.UserProfileActivity;
import com.mvvm.kien2111.mvvmapplication.util.RxBinding;
import com.mvvm.kien2111.mvvmapplication.util.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by WhoAmI on 31/01/2018.
 */

public class UniversalActivity extends BaseActivity<UniversalViewModel,ActivityUniversalBinding> {

    @Inject
    NavigationController navigationController;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_universal;
    }

    @Override
    protected UniversalViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(UniversalViewModel.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
        if(message instanceof UniversalViewModel.CategoryFetchMessage){
            navigationController.navigateToDetailCategory(((UniversalViewModel.CategoryFetchMessage) message).category);
        }else if(message instanceof UniversalViewModel.ProfileFetchMessage){
            navigationController.navigateToDetailProfile(((UniversalViewModel.ProfileFetchMessage) message).profileModel);
        }
    }

    @Override
    protected int getBindVariable() {
        return BR.VMuniversal;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            navigationController.navigateToFeed();
        }
        if(getIntent().hasExtra(SearchActivity.KEY_PICK_SEARCH_ITEM)){
            handleSearchPick(getIntent());
        }
        setupToolbar();
        setupNavigationBottomBar();
        setUpUserData();
        setUpOnClick();
    }

    private void setUpOnClick() {
        RxBinding.fromViewRaplidClick(mActivityBinding.detailProfileBtn)
                .observeOn(AndroidSchedulers.mainThread())
                .throttleLast(4, TimeUnit.SECONDS)
                .subscribe(this::onClickViewDetailProfile);
        RxBinding.fromViewRaplidClick(mActivityBinding.depositFundBtn)
                .observeOn(AndroidSchedulers.mainThread())
                .throttleLast(4,TimeUnit.SECONDS)
                .subscribe(this::onClickViewDepositFund);
        RxBinding.fromViewRaplidClick(mActivityBinding.viewAppointment)
                .observeOn(AndroidSchedulers.mainThread())
                .throttleLast(4,TimeUnit.SECONDS)
                .subscribe(this::onClickViewAppointment);
        RxBinding.fromViewRaplidClick(mActivityBinding.logoutBtn)
                .observeOn(AndroidSchedulers.mainThread())
                .throttleLast(4,TimeUnit.SECONDS)
                .subscribe(this::onClickHandleLogout);
    }


    private void handleSearchPick(final Intent data) {
        RecentSearch recentSearch = data.getParcelableExtra(SearchActivity.KEY_PICK_SEARCH_ITEM);
        switch (recentSearch.getSearchType()){
            case CATEGORY:
                mViewModel.fetchCategoryWithId(recentSearch.getIdquery());
                break;
            case PROFILE:
                mViewModel.fetchProfileWithId(recentSearch.getIdquery());
                break;
        }

        //wait to fetch category forward to onEvent
    }

    private void setUpUserData() {
        mViewModel.getPreferenceLiveData().observe(this,user -> {
            if(user!=null){
                mActivityBinding.name.setText(user.getRealname());
                BindingAdapters.setImageUrl(mActivityBinding.imgavatar,
                        user.getAvatar(),
                        ContextCompat.getDrawable(this,R.drawable.defaultimage),
                        ContextCompat.getDrawable(this,R.drawable.errorimg));
                BindingAdapters.setImageUrl(mActivityBinding.logoCompany,
                        user.getLogo_company(),
                        ContextCompat.getDrawable(this,R.drawable.defaultimage),
                        ContextCompat.getDrawable(this,R.drawable.errorimg));
                mActivityBinding.email.setText(user.getEmail());
            }
        });
    }

    public void onClickViewAppointment(View v){
        Intent intent = new Intent(this, ListAppointmentActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setupToolbar() {
        setSupportActionBar(mActivityBinding.toolbar);
        drawerToggle = new ActionBarDrawerToggle(this,mActivityBinding.drawerlayout,mActivityBinding.toolbar,R.string.drawer_open,R.string.drawer_close);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();
        drawerToggle.setToolbarNavigationClickListener(v -> {
            onBackPressed();
        });
        mActivityBinding.drawerlayout.addDrawerListener(drawerToggle);
    }



    public void reSupportToolbar(){
        setSupportActionBar(mActivityBinding.toolbar);
        getSupportActionBar().show();
        mActivityBinding.bnve.setVisibility(View.VISIBLE);
    }

    public void hideBottomBar(){
        mActivityBinding.bnve.setVisibility(View.GONE);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void onClickHandleLogout(View v) {
        Account account = mViewModel.getCurrentAccout();
        mAccountManager.invalidateAuthToken(account.type,
                mAccountManager.peekAuthToken(account,AccountAuthenticator.AUTHTOKEN_TYPE_BEARER_LABEL));
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

    private void successRemoveAccount(){
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void failRemoveAccount(){
        showDialog("Error","Can't log out");
    }

    public BottomNavigationView getBottomBar(){
        return mActivityBinding.bnve;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        //Toast.makeText(this,"shit",Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }

    public Toolbar getToolBar(){
        return mActivityBinding.toolbar;
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void setupNavigationBottomBar() {
        mActivityBinding.bnve.setOnNavigationItemSelectedListener(menuItem->{
            switch (menuItem.getItemId()){
                case R.id.action_home:navigationController.navigateToFeed(); return true;
                case R.id.action_search:navigationController.navigateToSearch();return true;
                case R.id.action_setting:navigationController.navigateToUser();return true;
            }
            return false;
        });
    }

    public void onClickViewDetailProfile(View v){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void onClickViewDepositFund(View v){
        Intent intent = new Intent(this, DepositFundActivity.class);
        startActivity(intent);
    }

    public static class SubmitQueryMessage extends BaseMessage{
        public String query;
        public SubmitQueryMessage(String query){this.query = query;}
    }
}
