package com.mvvm.kien2111.fastjob.ui.universal.detail_category;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseAdapter;
import com.mvvm.kien2111.fastjob.base.BaseViewHolder;
import com.mvvm.kien2111.fastjob.binding.FragmentBindingComponent;
import com.mvvm.kien2111.fastjob.data.local.db.entity.ProfileModel;
import com.mvvm.kien2111.fastjob.databinding.ProfileItemBinding;
import com.mvvm.kien2111.fastjob.util.ImageUtil;

import java.util.ArrayList;
import java.util.UUID;

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
        void onClickBooking(ProfileModel profileModel);
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
        ((GradientDrawable)binding.txtRatePoint.getBackground()).setColor(ImageUtil.randomColorBackground());
        binding.getRoot().setOnClickListener(view->{
            if(callback!=null && binding.getProfile()!=null){
                callback.onClick(binding.getProfile(),binding.imageprofile);
            }
        });
        binding.bookingButton.setOnClickListener(v->{
            if(callback!=null)callback.onClickBooking(binding.getProfile());
        });

        return binding;
    }

    @Override
    protected void bind(ProfileItemBinding mBinding, ProfileModel item) {
        mBinding.setProfile(item);
        if(mBinding.txtRatePoint!=null){

        }
        ViewCompat.setTransitionName(mBinding.imageprofile,item.getIdprofile());
        mBinding.executePendingBindings();
    }

    @Override
    protected boolean areContentsTheSame(ProfileModel olditem, ProfileModel newitem) {
        return olditem==newitem;
    }

    @Override
    protected boolean areItemsTheSame(ProfileModel olditem, ProfileModel newitem) {
        return olditem.equals(newitem);
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
                filterResult(filterinput);
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

    private void filterResult(String input) {
        /*
        "FilterMessage{" +
                    "distid='" + distid + '\'' +"/"+
                    ", cityid='" + cityid + '\'' +"/"+
                    ", fromSalary=" + fromSalary +"/"+
                    ", toSalary=" + toSalary +"/"+
                    ", priority=" + priority +
                    '}';
        * */
        String[] arrinp = input.split("/");
        for(final ProfileModel profileModel : getLstData()){
            if(
                    (profileModel.getDistrict()!=null && profileModel.getDistrict().getDistid().equals(getValueFromString(arrinp[0])))||
                    (profileModel.getCity()!=null && profileModel.getCity().getCityid().equals(getValueFromString(arrinp[1]))) ||
                            (
                                    (profileModel.getSalary_expected_from()<=Double.valueOf(getValueFromString(arrinp[2]))) &&
                                    (profileModel.getSalary_expected_to()>=Double.valueOf(getValueFromString(arrinp[3])))
                            ) ||
                    (profileModel.getPriority().getType()== Integer.valueOf(getValueFromString(arrinp[4])))
                    ){
                filterList.add(profileModel);
            }
        }
    }
    private String getValueFromString(String str){
        return str.substring(str.indexOf("=")+1);
    }

}
