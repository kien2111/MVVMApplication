package com.mvvm.kien2111.fastjob.ui.depositfund;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.base.BaseAdapter;
import com.mvvm.kien2111.fastjob.base.BaseViewHolder;
import com.mvvm.kien2111.fastjob.databinding.DepositfundItemBinding;
import com.mvvm.kien2111.fastjob.model.TableExchangeModel;

import java.text.DecimalFormat;

/**
 * Created by WhoAmI on 28/04/2018.
 */

public class TableExchangeAdapter extends BaseAdapter<TableExchangeModel,DepositfundItemBinding>{

    private final DecimalFormat decimalFormat;
    public TableExchangeAdapter(DecimalFormat decimalFormat){
        this.decimalFormat = decimalFormat;
    }


    @Override
    protected BaseViewHolder<DepositfundItemBinding> instantiateViewHolder(DepositfundItemBinding mBinding) {
        return new BaseViewHolder<>(mBinding);
    }

    @Override
    protected DepositfundItemBinding createBinding(ViewGroup parent) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.depositfund_item,parent,false);
    }

    @Override
    protected void bind(DepositfundItemBinding mBinding, TableExchangeModel item) {
        mBinding.amountOfCoint.setText(String.format("%s đ",decimalFormat.format(item.getAmount_of_coin())));
        mBinding.nominalValue.setText(String.format("%s đ",decimalFormat.format(item.getNominal_value())));
        mBinding.executePendingBindings();
    }

    @Override
    protected boolean areContentsTheSame(TableExchangeModel olditem, TableExchangeModel newitem) {
        return false;
    }

    @Override
    protected boolean areItemsTheSame(TableExchangeModel olditem, TableExchangeModel newitem) {
        return false;
    }
}
