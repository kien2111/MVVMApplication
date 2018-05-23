package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionInflater;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.BuildConfig;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.RateRequest;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentDetailprofilebackupBinding;
import com.mvvm.kien2111.mvvmapplication.model.Profile;
import com.mvvm.kien2111.mvvmapplication.model.User;
import com.mvvm.kien2111.mvvmapplication.ui.universal.UniversalActivity;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.NavigationController;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.about.AboutFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.dialog.RateDialog;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate.ListRateFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.ViewPagerAdapter;

import javax.inject.Inject;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.util.GlideApp;
import com.mvvm.kien2111.mvvmapplication.util.PaletteBitmap;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailProfileFragment extends BaseFragment<DetailProfileViewModel,FragmentDetailprofilebackupBinding> implements View.OnClickListener{

    private static final String KEY_PROFILE_MODEL = "key profile model";
    @Inject
    ViewPagerAdapter viewPagerAdapter;

    User currentUser;

    @Inject
    NavigationController navigationController;

    public static DetailProfileFragment newInstance(ProfileModel profileModel){
        DetailProfileFragment fragment = new DetailProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PROFILE_MODEL,profileModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseMessage message){
        if(message instanceof DetailProfileViewModel.RateMessage){
            switch (((DetailProfileViewModel.RateMessage) message).status){
                case SUCCESS:
                    navigationController.refreshDetailProfile(getArguments().getParcelable(KEY_PROFILE_MODEL));
                    break;
                case ERROR:
                    ((BaseActivity)getActivity()).showErrorDialog("Error",message.getThrowable().getMessage());
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rateButton:

                break;
        }
    }

    private void showDialog(){
        RateDialog rateDialog = RateDialog.newInstance(getArguments().getParcelable(KEY_PROFILE_MODEL));
        rateDialog.setOnListenRate(new RateDialog.RateListener() {
            @Override
            public void onDoRate(float average_point,String content) {
                mViewModel.doRate(new RateRequest(average_point,
                        ((ProfileModel)getArguments().getParcelable(KEY_PROFILE_MODEL)).getIdprofile(),
                        currentUser.getUserId(),content));
                rateDialog.dismiss();
            }

            @Override
            public void onDismiss() {
                rateDialog.dismiss();
            }
        });
        rateDialog.show(getFragmentManager(),"rate_dialog_"+((ProfileModel)getArguments().getParcelable(KEY_PROFILE_MODEL)).getIdprofile());
    }

    @Override
    protected DetailProfileViewModel createViewModel() {
        return ViewModelProviders.of(this,viewModelFactory).get(DetailProfileViewModel.class);
    }

    public ProfileModel getCurrentProfile(){
        return getArguments().getParcelable(KEY_PROFILE_MODEL);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detailprofilebackup;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        setUpViewPagerFragment();
        setUpToolbar();
        setUpUserData();
        setUpUserCurrentUseApp();
        setOnClick();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_detail_profile_fragment,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.menu_rating:
                showDialog();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    private void setUpToolbar() {
        ((UniversalActivity)getActivity()).getSupportActionBar().hide();
        ((UniversalActivity)getActivity()).setSupportActionBar(mFragmentBinding.anothertoolbar);
        ((UniversalActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((UniversalActivity)getActivity()).getSupportActionBar().setTitle(((ProfileModel)getArguments().getParcelable(KEY_PROFILE_MODEL)).getName());
    }

    private void setUpUserCurrentUseApp() {
        mViewModel.getPreferenceLiveData().observe(this,user -> {
            if(user!=null){
                currentUser = user;
            }
        });
    }

    private void setOnClick() {
        //mFragmentBinding.rateButton.setOnClickListener(this);
    }

    private void setUpUserData() {
        mViewModel.getResourceUserLiveData().observe(this,userResource -> {
            if(userResource!=null){
                switch (userResource.getStatus()){
                    case SUCCESS:mFragmentBinding.setDetailprofile(userResource.getData());break;
                }
                mFragmentBinding.executePendingBindings();
            }
        });
        mViewModel.setUserId(((ProfileModel)getArguments().getParcelable(KEY_PROFILE_MODEL)).getIdprofile());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments().getParcelable(KEY_PROFILE_MODEL)==null){
            navigationController.navigateToFeed();
            return;
        }
        ProfileModel profileModel = getArguments().getParcelable(KEY_PROFILE_MODEL);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            mFragmentBinding.imagedetailprofile.setTransitionName(profileModel.getIdprofile());
        }
        GlideApp.with(this)
                .as(PaletteBitmap.class)
                .load(BuildConfig.IMG_URL+profileModel.getAvatar())
                .listener(new RequestListener<PaletteBitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<PaletteBitmap> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(PaletteBitmap resource, Object model, Target<PaletteBitmap> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .apply(new RequestOptions().fitCenter()
                        .error(R.drawable.errorimg)
                        .placeholder(R.drawable.defaultimage)
                        .dontAnimate())
                .into(new SimpleTarget<PaletteBitmap>() {
                    @Override
                    public void onResourceReady(@NonNull PaletteBitmap resource, @Nullable com.bumptech.glide.request.transition.Transition<? super PaletteBitmap> transition) {
                        mFragmentBinding.imagedetailprofile.setImageBitmap(resource.bitmap);
                        mFragmentBinding.imagedetailprofile.setBackgroundColor(resource.palette.getDarkMutedColor(ContextCompat.getColor(getContext(),android.R.color.white)));
                    }
                });
        ((UniversalActivity)getActivity()).hideBottomBar();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));

        }
        ProfileModel profileModel = getArguments().getParcelable(KEY_PROFILE_MODEL);
        viewPagerAdapter.addFragment(AboutFragment.newInstance(profileModel),"About");
        viewPagerAdapter.addFragment(ListRateFragment.newInstance(profileModel),"Rate");
    }

    private void setUpViewPagerFragment() {
        mFragmentBinding.viewPager.setAdapter(viewPagerAdapter);
        mFragmentBinding.tablayoutDetailprofile.setupWithViewPager(mFragmentBinding.viewPager);
    }

}
