package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.alluser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.model.BlockUser;
import com.mvvm.kien2111.fastjob.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by donki on 3/8/2018.
 */

public class AdapterListAllUser extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<User> listUser;
    private List<User> mFilteredList;
    private List<BlockUser> item_list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private IUserClickItemList iUserClickItemList;
    public SparseBooleanArray itemStateArray = new SparseBooleanArray();


    // data is passed into the constructor
    public AdapterListAllUser(Context context, List<User> listUser,IUserClickItemList iUserClickItemList) {
        this.mInflater = LayoutInflater.from(context);
        this.listUser = listUser;
        this.mFilteredList = listUser;
        this.item_list = new ArrayList<>();
        this.iUserClickItemList= iUserClickItemList;
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.child_admin_header_listuser, parent, false);
            return new HeaderViewHolder(itemview);
        } else if (viewType == 1) {
            View itemview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.child_amin_all_user, parent, false);
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
            ((ViewHolder) holder).tv_personname.setText("" + mFilteredList.get(position - 1).getUserName().toString());
            ((ViewHolder) holder).tv_email.setText("" + mFilteredList.get(position - 1).getUserId());
            ((ViewHolder) holder).checkbox_user.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        BlockUser temp = new BlockUser();
                        temp.setStatus(0);
                        temp.setIduser(mFilteredList.get(position - 1).getUserId());
                        item_list.add(temp);
                    } else {
                        item_list.remove(mFilteredList.get(position - 1).getUserId());
                    }
                }
            });
            ((ViewHolder) holder).tab_edituser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iUserClickItemList.onClickItemList(mFilteredList.get(position-1));
                }
            });
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mFilteredList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        return 1;
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return String.valueOf(listUser.get(id).getUserId());
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    //filter data
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = listUser;
                } else {
                    List<User> filteredList = new ArrayList<>();
                    for (User user : listUser) {
                        if (user.getUserId().toLowerCase().contains(charString)
                                || user.getUserName().toLowerCase().contains(charString)
                                || user.getEmail().toLowerCase().contains(charString)) {
                            filteredList.add(user);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    //list user to block
    public List<BlockUser> getlistBlockUser() {
        return item_list;
    }

    //list user to unlock
    public List<BlockUser> getlistUser() {
        for (BlockUser user : item_list) {
            user.setStatus(1);
        }
        return item_list;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        SearchView headerTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = (SearchView) itemView.findViewById(R.id.searchview);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        ImageView imageView;
        TextView tv_personname, tv_email;
        CheckBox checkbox_user;
        LinearLayout tab_edituser;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_personname = itemView.findViewById(R.id.tv_personname);
            tv_email = itemView.findViewById(R.id.tv_email);
            imageView = itemView.findViewById(R.id.imageView);
            checkbox_user = itemView.findViewById(R.id.checkbox_user);
            checkbox_user.setOnCheckedChangeListener(this);
            tab_edituser = itemView.findViewById(R.id.tab_edituser);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!itemStateArray.get(getAdapterPosition(), false)) {
                itemStateArray.put(getAdapterPosition(), false);
            } else {
                itemStateArray.put(getAdapterPosition(), true);
            }
        }
    }
}