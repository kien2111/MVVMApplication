package com.mvvm.kien2111.mvvmapplication.ui.universal.user;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.appinvite.FirebaseAppInvite;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.authenticate.AccountAuthenticator;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.dagger.Injectable;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentUserBinding;
import com.mvvm.kien2111.mvvmapplication.ui.SplashActivity;
import com.mvvm.kien2111.mvvmapplication.ui.depositfund.DepositFundActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.userprofile.UserProfileActivity;
import com.mvvm.kien2111.mvvmapplication.util.AnimationUtil;

import java.io.IOException;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * Created by WhoAmI on 07/02/2018.
 */

public class UserFragment extends BaseFragment<UserViewModel,FragmentUserBinding> implements
        View.OnClickListener{
    private static final int REQUEST_INVITE = 0;
    private static final String TAG = UserFragment.class.getSimpleName();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected int getBindVariable() {
        return BR.VMuser;
    }

    @Override
    protected UserViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setUpUserData();
        setOnClick();
        setOnScroll();
        setUpAccountBalance();
        return view;
    }

    private void setUpAccountBalance() {
        mViewModel.getPreferenceLiveData().observe(this,user -> {
            if(user!=null){
                new Handler().postDelayed(()->{
                    mFragmentBinding.setUser(user);
                    mFragmentBinding.executePendingBindings();
                },300);

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setOnScroll() {
        mFragmentBinding.scrollViewSetting.setOnTouchListener(new View.OnTouchListener() {
            private ViewTreeObserver observer;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (observer == null) {
                    observer = mFragmentBinding.scrollViewSetting.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                } else if (!observer.isAlive()) {
                    observer.removeOnScrollChangedListener(onScrollChangedListener);
                    observer = mFragmentBinding.scrollViewSetting.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                }
                return false;
            }
        });
    }

    private void setOnClick() {
        mFragmentBinding.profiledetailbutton.setOnClickListener(this);
        mFragmentBinding.btnInviteFriend.setOnClickListener(this);
        mFragmentBinding.topupBtn.setOnClickListener(this);
        mFragmentBinding.logoutBtn.setOnClickListener(this);
        mFragmentBinding.expandMoreBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profiledetailbutton:
                forwardToProfileDetail();
                break;
            case R.id.topupBtn:
                navigateToDepositMoney();
                break;
            case R.id.logoutBtn:
                logoutHandle();
                break;
            case R.id.expandMoreBtn:
                expandOtherContainer();
                break;
            case R.id.btn_inviteFriend:
                inviteFriend();
        }
    }

    private void inviteFriend() {
        /*Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setDeepLink(Uri.parse(buildDynamicLink()))
                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);*/

        Intent intent = new Intent();
        String msg = "visit my awesome website: " + buildDynamicLink();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.setType("text/plain");
        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Timber.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
                Timber.e("send errror");
            }
        }

    }

    private String buildDynamicLink(/*String link, String description, String titleSocial, String source*/) {
        //more info at https://firebase.google.com/docs/dynamic-links/create-manually

        /*String path = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setDynamicLinkDomain("uqcf9.app.goo.gl")
                .setLink(Uri.parse("https://fastjob.com/welcome"))
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build()) //com.melardev.tutorialsfirebase
                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setTitle("Share this App").setDescription("blabla").build())
                .setGoogleAnalyticsParameters(new DynamicLink.GoogleAnalyticsParameters.Builder().setSource("AndroidApp").build())
                .buildDynamicLink().getUri().toString();*/

        return "https://uqcf9.app.goo.gl/?" +
                "link=" + /*link*/
                "https://fastjob.com/welcome" +
                "&apn=" + /*getPackageName()*/
                "com.mvvm.kien2111.mvvmapplication" +
                "&st=" + /*titleSocial*/
                "Share+this+App" +
                "&sd=" + /*description*/
                "test" +
                "&utm_source=" + /*source*/
                "AndroidApp";
    }

    final ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = () -> {
        if (mFragmentBinding.scrollViewSetting.getScrollY() == 0) {
            collapseOtherContainer();

        }
    };

    public void collapseOtherContainer(){
        AnimationUtil.collapse(mFragmentBinding.collapseContainer);
        mFragmentBinding.expandMoreBtn.setVisibility(View.VISIBLE);
    }

    public void expandOtherContainer(){
        AnimationUtil.expand(mFragmentBinding.collapseContainer);
        mFragmentBinding.expandMoreBtn.setVisibility(View.GONE);
    }

    private void logoutHandle() {
        Account account = mViewModel.getCurrentAccount();
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
        Intent intent = new Intent(getActivity().getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void failRemoveAccount(){
        ((BaseActivity)getActivity()).showDialog("Error","Can't log out");
    }

    private void forwardToProfileDetail() {
        Intent intent = new Intent(this.getActivity(), UserProfileActivity.class);
        startActivity(intent);
    }


    private void setUpUserData() {
        /*final Account[] account = new Account[1];
        mAccountManager.getAuthTokenByFeatures(AccountAuthenticator.ACCOUNT_TYPE,
                AccountAuthenticator.DEFAULT_AUTHTOKEN_TYPE_BEARER, null, null, null, null,future -> {
                    try {
                        account[0] = new Account(future.getResult().getString(AccountManager.KEY_ACCOUNT_NAME),future.getResult().getString(AccountManager.KEY_ACCOUNT_TYPE));
                        mAccountManager.getUserData(account[0],AccountAuthenticator.KEY_USER_DATA);
                    } catch (OperationCanceledException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (AuthenticatorException e) {
                        e.printStackTrace();
                    }
                },null);*/
    }

    private void navigateToInviteFriend(){

    }

    private void navigateToDepositMoney(){
        Intent intent = new Intent(getActivity(), DepositFundActivity.class);
        startActivity(intent);
    }


}
