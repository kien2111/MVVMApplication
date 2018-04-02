package com.mvvm.kien2111.mvvmapplication.ui.admin.user.fragment.alluser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvvm.kien2111.mvvmapplication.R;
import com.mvvm.kien2111.mvvmapplication.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by donki on 3/8/2018.
 */

public class AdapterListAllUser extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<User> listUser;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    public SparseBooleanArray itemStateArray = new SparseBooleanArray();


    // data is passed into the constructor
    public AdapterListAllUser(Context context, List<User> listUser) {
        this.mInflater = LayoutInflater.from(context);
        this.listUser = listUser;
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.child_amin_all_user, parent, false);
        if(viewType == 0)
        {
            View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.child_admin_header_listuser,parent,false);
            return  new HeaderViewHolder(itemview);
        }
        else if(viewType == 1)
        {
            View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.child_amin_all_user,parent,false);
            return new ViewHolder(itemview);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {

        if(holder instanceof HeaderViewHolder)
        {
        }
        else if(holder instanceof ViewHolder){
            ((ViewHolder) holder).tv_personname.setText(""+listUser.get(position-1).getUsername().toString());
            ((ViewHolder) holder).tv_email.setText(""+listUser.get(position-1).getId());
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return listUser.size()+1;
    }
    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return 0;
        return 1;
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return String.valueOf(listUser.get(id).getId());
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder
    {
        SearchView headerTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = (SearchView) itemView.findViewById(R.id.searchview);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        ImageView imageView;
        TextView tv_personname;
        TextView tv_email;
        CheckBox checkbox_user;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_personname = itemView.findViewById(R.id.tv_personname);
            tv_email =      itemView.findViewById(R.id.tv_email);
            imageView =     itemView.findViewById(R.id.imageView);
            checkbox_user = itemView.findViewById(R.id.checkbox_user);
            checkbox_user.setOnCheckedChangeListener(this);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(!itemStateArray.get(getAdapterPosition(),false))
            {
                itemStateArray.put(getAdapterPosition(),false);
            }
            else{
                itemStateArray.put(getAdapterPosition(),true);
            }
        }
    }

}