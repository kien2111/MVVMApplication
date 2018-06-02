package com.mvvm.kien2111.fastjob.ui.admin.upgradeacount;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.model.AccountUpgrade;
import com.mvvm.kien2111.fastjob.model.UpgradeAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by donki on 5/14/2018.
 */

public class UpgradeAccountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<UpgradeAccount> listUpgradeAccount;
    private List<UpgradeAccount> mfilteUpgradeAccount;
    private LayoutInflater mInflater;
    private List<AccountUpgrade> listAcountUngrade;

    public UpgradeAccountAdapter(Context context, List<UpgradeAccount> listUpgradeAccount){
        this.mInflater = LayoutInflater.from(context);
        this.listUpgradeAccount= listUpgradeAccount;
        this.mfilteUpgradeAccount= listUpgradeAccount;
        listAcountUngrade= new ArrayList<>();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.child_admin_header_listuser, parent, false);
            return new HeaderViewHolder(itemview);
        } else if (viewType == 1) {
            View itemview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.child_admin_upgrade_account, parent, false);
            return new ViewHolder(itemview);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).headerTitle.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    getFilter().filter(newText);
                    return false;
                }
            });
        } else if (holder instanceof ViewHolder) {
            SimpleDateFormat dff= new SimpleDateFormat("dd/MM/yyyy");
            ((ViewHolder) holder).tv_name.setText(mfilteUpgradeAccount.get(position-1).getUsername());
            ((ViewHolder) holder).tv_name_company.setText(mfilteUpgradeAccount.get(position-1).getEmail());
            ((ViewHolder) holder).tv_name_createat.setText(dff.format(mfilteUpgradeAccount.get(position-1).getCreated_at()));

            ((ViewHolder) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AccountUpgrade temp = new AccountUpgrade(mfilteUpgradeAccount.get(position - 1).getIdrequest_update_profile());
                        listAcountUngrade.add(temp);
                    } else {
                        for(AccountUpgrade accountUpgrade:listAcountUngrade){
                            if(mfilteUpgradeAccount.get(position-1).getIdrequest_update_profile()== accountUpgrade.getIdrequest_update_profile())
                                listAcountUngrade.remove(accountUpgrade);
                        }

                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mfilteUpgradeAccount.size() + 1;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mfilteUpgradeAccount = listUpgradeAccount;
                } else {
                    List<UpgradeAccount> filteredList = new ArrayList<>();
                    for (UpgradeAccount upgradeAccount : listUpgradeAccount) {
                       /* if (upgradeAccount.getIdpakage().toLowerCase().contains(charString)) {
                            filteredList.add(upgradeAccount);
                        }*/
                    }
                    mfilteUpgradeAccount = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mfilteUpgradeAccount;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mfilteUpgradeAccount = (List<UpgradeAccount>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //get data upgrade to activitu
    public  List<AccountUpgrade> getDataUpgrade(){
        return listAcountUngrade;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private CheckBox checkBox;
        private TextView tv_name_company,tv_name,tv_name_createat;


        public ViewHolder(View itemView) {
            super(itemView);
            checkBox= itemView.findViewById(R.id.checkbox);
            tv_name_company= itemView.findViewById(R.id.tv_name_company);
            tv_name= itemView.findViewById(R.id.tv_name);
            tv_name_createat= itemView.findViewById(R.id.tv_name_createat);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
           // if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            /*if (!itemStateArray.get(getAdapterPosition(), false)) {
                itemStateArray.put(getAdapterPosition(), false);
            } else {
                itemStateArray.put(getAdapterPosition(), true);
            }*/
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        SearchView headerTitle;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = (SearchView) itemView.findViewById(R.id.searchview);
        }
    }
}