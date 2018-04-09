package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile;

import android.arch.lifecycle.ViewModelProviders;
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

import com.mvvm.kien2111.mvvmapplication.BR;
import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseFragment;
import com.mvvm.kien2111.mvvmapplication.databinding.FragmentDetailprofileBinding;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.NavigationController;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.about.AboutFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate.ListRateFragment;
import com.mvvm.kien2111.mvvmapplication.ui.universal.common.ViewPagerAdapter;

import javax.inject.Inject;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
/**
 * Created by WhoAmI on 17/03/2018.
 */

public class DetailProfileFragment extends BaseFragment<DetailProfileViewModel,FragmentDetailprofileBinding> {

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
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().getParcelable(KEY_PROFILE_MODEL)==null){
            navigationController.navigateToFeed();
        }else{
            viewPagerAdapter.addFragment(AboutFragment.newInstance(getArguments().getParcelable(KEY_PROFILE_MODEL)),"About");
            viewPagerAdapter.addFragment(ListRateFragment.newInstance(getArguments().getParcelable(KEY_PROFILE_MODEL)),"Rate");
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.image_profile_transform);
            setSharedElementEnterTransition(transition);
            setEnterTransition(new Fade());
            setExitTransition(new Fade());
            setSharedElementReturnTransition(transition);
        }

    }

    private void setUpViewPagerFragment() {
        mFragmentBinding.viewPager.setAdapter(viewPagerAdapter);
        mFragmentBinding.tablayoutDetailprofile.setupWithViewPager(mFragmentBinding.viewPager);
    }
}
