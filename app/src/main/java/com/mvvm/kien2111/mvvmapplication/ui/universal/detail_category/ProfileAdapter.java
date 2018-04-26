package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_category;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.mvvmapplication.databinding.ProfileItemBinding;
import com.mvvm.kien2111.mvvmapplication.model.FilterOption;
import com.mvvm.kien2111.mvvmapplication.model.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import timber.log.Timber;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class ProfileAdapter extends BaseAdapter<ProfileModel,ProfileItemBinding> implements Filterable{
    ArrayList<ProfileModel> filterList;
    FragmentBindingComponent dataBindingComponent;
    private final ProfileClickCallback callback;
    private static final String TAG = ProfileAdapter.class.getSimpleName();




    interface ProfileClickCallback{
        void onClick(ProfileModel profileModel, ImageView sharedImageView);
    }
    public ProfileAdapter(FragmentBindingComponent dataBindingComponent, ProfileClickCallback callback){
        this.callback = callback;
        this.dataBindingComponent = dataBindingComponent;
        this.filterList = new ArrayList<>();
        this.setHasStableIds(true);
    }
    @Override
    protected BaseViewHolder<ProfileItemBinding> instantiateViewHolder(ProfileItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected ProfileItemBinding createBinding(ViewGroup parent) {
        ProfileItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.profile_item,parent,false,dataBindingComponent);
        binding.getRoot().setOnClickListener(view->{
            if(callback!=null && binding.getProfile()!=null){
                callback.onClick(binding.getProfile(),binding.imageprofile);
            }
        });
        return binding;
    }

    @Override
    protected void bind(ProfileItemBinding mBinding, ProfileModel item) {
        mBinding.setProfile(item);
        ViewCompat.setTransitionName(mBinding.imageprofile,item.getIdprofile());
        mBinding.executePendingBindings();
    }

    @Override
    protected boolean areContentsTheSame(ProfileModel olditem, ProfileModel newitem) {
        return olditem.getName().equals(newitem.getName());
    }

    @Override
    protected boolean areItemsTheSame(ProfileModel olditem, ProfileModel newitem) {
        return (olditem.getName()!=null && newitem.getName()!=null) &&
                olditem.getName().equals(newitem.getName()) &&
                olditem.getRating()==newitem.getRating();
    }

    @Override
    public long getItemId(int position) {
        return UUID.fromString(getLstData().get(position).getIdprofile()).getLeastSignificantBits();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterinput = constraint.toString();
                filterList.clear();
                final FilterResults filterResults = new FilterResults();
                for(final ProfileModel profileModel : getLstData()){
                    if((profileModel.getCity()!=null && profileModel.getCity().getNamecity().equals(filterinput))
                            ||
                            (profileModel.getDistrict()!=null && profileModel.getDistrict().getNamedist().equals(filterinput))){
                        filterList.add(profileModel);
                    }
                }
                filterResults.values = filterList;
                filterResults.count = filterList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                setLstData(filterList);
                notifyDataSetChanged();
            }
        };
    }

}
