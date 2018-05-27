package com.mvvm.kien2111.fastjob.ui.universal;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.appinvite.FirebaseAppInvite;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.mvvm.kien2111.fastjob.BR;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseActivity;
import com.mvvm.kien2111.fastjob.base.BaseMessage;
import com.mvvm.kien2111.fastjob.data.local.db.entity.RecentSearch;
import com.mvvm.kien2111.fastjob.databinding.ActivityUniversalBinding;
import com.mvvm.kien2111.fastjob.ui.universal.common.NavigationController;
import com.mvvm.kien2111.fastjob.ui.universal.search.searchresult.SearchActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 31/01/2018.
 */

public class UniversalActivity extends BaseActivity<UniversalViewModel,ActivityUniversalBinding> implements GoogleApiClient.OnConnectionFailedListener{

    @Inject
    NavigationController navigationController;



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
        setupNavigationBottomBar();
        setUpListenFireBase();
    }

    private void setUpListenFireBase() {
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData data) {
                        if (data == null) {
                            Log.d(UniversalActivity.class.getSimpleName(), "getInvitation: no data");
                            return;
                        }

                        // Get the deep link
                        Uri deepLink = data.getLink();

                        // Extract invite
                        FirebaseAppInvite invite = FirebaseAppInvite.getInvitation(data);
                        if (invite != null) {
                            String invitationId = invite.getInvitationId();
                        }

                        // Handle the deep link
                        // [START_EXCLUDE]
                        Log.d(UniversalActivity.class.getSimpleName(), "deepLink:" + deepLink);
                        if (deepLink != null) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setPackage(getPackageName());
                            intent.setData(deepLink);

                            startActivity(intent);
                        }
                        // [END_EXCLUDE]
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(UniversalActivity.class.getSimpleName(), "getDynamicLink:onFailure", e);
                    }
                });
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(UniversalActivity.class.getSimpleName(), "onConnectionFailed:" + connectionResult);
        showMessage(getString(R.string.google_play_services_error));
    }

    private void showMessage(String msg) {
        ViewGroup container = findViewById(R.id.root);
        Snackbar.make(container, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void hideBottomBar(){
        mActivityBinding.bnve.setVisibility(View.GONE);
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



    public static class SubmitQueryMessage extends BaseMessage{
        public String query;
        public SubmitQueryMessage(String query){this.query = query;}
    }
}
