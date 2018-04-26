package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.BuildConfig;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseActivity;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.RateRequest;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentDetailprofileBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.NavigationController;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.about.AboutFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.dialog.RateDialog;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate.ListRateFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.ViewPagerAdapter;

import javax.inject.Inject;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailProfileFragment extends BaseFragment<DetailProfileViewModel,FragmentDetailprofileBinding> implements View.OnClickListener{

    private static String KEY_PROFILE_MODEL = "key profile model";
    @Inject
    ViewPagerAdapter viewPagerAdapter;

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
                showDialog(v);
                break;
        }
    }

    private void showDialog(View view){
        RateDialog rateDialog = RateDialog.newInstance(getArguments().getParcelable(KEY_PROFILE_MODEL));
        rateDialog.setOnListenRate(new RateDialog.RateListener() {
            @Override
            public void onDoRate(float average_point) {
                mViewModel.doRate(new RateRequest(average_point,((ProfileModel)getArguments().getParcelable(KEY_PROFILE_MODEL)).getIdprofile()));
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
        return R.layout.fragment_detailprofile;
    }

    @Override
    protected int getBindVariable() {
        return BR.vm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setUpViewPagerFragment();
        setUpUserData();
        setOnClick();
        return view;
    }

    private void setOnClick() {
        mFragmentBinding.rateButton.setOnClickListener(this);
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
        Glide.with(this)
                .load(BuildConfig.IMG_URL+profileModel.getAvatar())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .apply(new RequestOptions().fitCenter()
                        .error(R.drawable.errorimg).placeholder(R.drawable.defaultimage).dontAnimate())
                .into(mFragmentBinding.imagedetailprofile);
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
