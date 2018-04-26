package com.mvvm.kien2111.mvvmapplication.ui.universal.detail_profile.rate;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.base.BaseAdapter;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewHolder;
import com.mvvm.kien2111.mvvmapplication.binding.FragmentBindingComponent;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.RateModel;
import com.mvvm.kien2111.mvvmapplication.databinding.RateItemBinding;

import java.util.UUID;

/**
 * Created by WhoAmI on 04/04/2018.
 */

public class RateAdapter extends BaseAdapter<RateModel,RateItemBinding> {
    private final android.databinding.DataBindingComponent dataBindingComponent;
    private final ClickRateCallBack clickRateCallBack;
    public RateAdapter(FragmentBindingComponent fragmentBindingComponent,ClickRateCallBack callBack){
        this.dataBindingComponent = fragmentBindingComponent;
        this.clickRateCallBack = callBack;
        this.setHasStableIds(true);
    }
    @Override
    protected BaseViewHolder<RateItemBinding> instantiateViewHolder(RateItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected RateItemBinding createBinding(ViewGroup parent) {
        RateItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.rate_item,parent,false,dataBindingComponent);
        return binding;
    }

    @Override
    protected void bind(RateItemBinding mBinding, RateModel item) {
        if(item!=null){
            mBinding.setRateitem(item);
            mBinding.getRoot().setOnClickListener((v)-> clickRateCallBack.clickRateItem(item));
            mBinding.executePendingBindings();

        }
    }

    public interface ClickRateCallBack{
        void clickRateItem(RateModel rateItem);
    }

    @Override
    protected boolean areContentsTheSame(RateModel olditem, RateModel newitem) {
        return false;
    }

    @Override
    protected boolean areItemsTheSame(RateModel olditem, RateModel newitem) {
        return false;
    }

    @Override
    public long getItemId(int position) {
        return UUID.fromString(getLstData().get(position).getUser().getUserId()).getLeastSignificantBits();
    }
}
