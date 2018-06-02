package com.mvvm.kien2111.fastjob.ui.admin.apointment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.mvvm.kien2111.fastjob.BuildConfig;
import com.mvvm.kien2111.fastjob.R;
import com.mvvm.kien2111.fastjob.model.AdminAppointment;
import com.mvvm.kien2111.fastjob.model.ImpactApointment;
import com.mvvm.kien2111.fastjob.util.GlideApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by donki on 5/5/2018.
 */

public class AdminApointmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<AdminAppointment> listAppointment;
    private List<AdminAppointment> mFilteredList;
    private LayoutInflater mInflater;
    public ImpactApointment impactApointment;
    private IAppointmentImpactStatus iAppointmentImpactStatus;
    public Button btnaccept,btnskip;
    private Context context;
    private AdminAppointment adminAppointment;

    public AdminApointmentAdapter(Context context, List<AdminAppointment> listAppointment,IAppointmentImpactStatus iAppointmentImpactStatus ) {
        this.mInflater = LayoutInflater.from(context);
        this.listAppointment = listAppointment;
        this.mFilteredList= listAppointment;
        this.iAppointmentImpactStatus=iAppointmentImpactStatus;
        this.context=context;
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
                    .inflate(R.layout.child_admin_list_appointment, parent, false);
            return new ViewHolder(itemview);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).headerTitle.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
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
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            ((ViewHolder) holder).tv_name_employer.setText(mFilteredList.get(position-1).getUser_create_appointment().getUserName());
            ((ViewHolder) holder).tv_name_employee.setText(mFilteredList.get(position-1).getUser_receive_appointment().getUserName());
            ((ViewHolder) holder).tv_date_appointment.setText(format.format(listAppointment.get(position-1).getUpdated_at()));

            GlideApp.with(context)
                    .load(BuildConfig.IMG_URL +mFilteredList.get(position-1)
                    .getUser_create_appointment().getAvatar())
                    .into(((ViewHolder) holder).avt_employee);
            GlideApp.with(context)
                    .load(BuildConfig.IMG_URL +mFilteredList.get(position-1)
                            .getUser_receive_appointment().getAvatar())
                    .into(((ViewHolder) holder).avt_employer);

            ((ViewHolder) holder).btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    impactApointment = new ImpactApointment(mFilteredList.get(position-1).getIdappointment(),3);
                    iAppointmentImpactStatus.acceptAppointment(impactApointment);
                    btnaccept= ((ViewHolder) holder).btn_accept;
                    btnskip= ((ViewHolder) holder).btn_skip;

                    adminAppointment= mFilteredList.get(position-1);
                }
            });
            ((ViewHolder) holder).btn_skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    impactApointment = new ImpactApointment(listAppointment.get(position-1).getIdappointment(),2);
                    iAppointmentImpactStatus.skipAppointment(impactApointment);
                    btnaccept= ((ViewHolder) holder).btn_accept;
                    btnskip= ((ViewHolder) holder).btn_skip;

                    adminAppointment= mFilteredList.get(position-1);
                }
            });
        }
    }
    public AdminAppointment getItemChange(){
        return adminAppointment;
    }

    public void hireButton(){
    }
    //call accept and skip appointment
    public  ImpactApointment getImpactAppointment(){
            return impactApointment;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mFilteredList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        return 1;
    }
    //filter data
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = listAppointment;
                } else {
                    List<AdminAppointment> filteredList = new ArrayList<>();
                    for (AdminAppointment appointment : listAppointment) {
                        if (appointment.getUser_create_appointment().getUserName().toLowerCase().contains(charString)||
                                appointment.getUser_receive_appointment().getUserName().toLowerCase().contains(charString)){
                            filteredList.add(appointment);
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
                mFilteredList = (List<AdminAppointment>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        SearchView headerTitle;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = (SearchView) itemView.findViewById(R.id.searchview);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name_employee,tv_date_appointment,
                tv_address_appointment,tv_name_employer,
                tv_money_appointment,tv_sentemplyee,tv_sentemplyer;
        CircleImageView avt_employee,avt_employer;
        Button btn_accept,btn_skip;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_employee = itemView.findViewById(R.id.tv_name_employee);
            tv_date_appointment = itemView.findViewById(R.id.tv_date_appointment);
            tv_address_appointment = itemView.findViewById(R.id.tv_address_appointment);
            tv_money_appointment = itemView.findViewById(R.id.tv_money_appointment);
            tv_sentemplyee = itemView.findViewById(R.id.tv_sentemplyee);
            tv_name_employer = itemView.findViewById(R.id.tv_name_employer);
            tv_sentemplyer = itemView.findViewById(R.id.tv_sentemplyer);

            avt_employee = itemView.findViewById(R.id.avt_employee);
            avt_employer = itemView.findViewById(R.id.avt_employer);
            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_skip = itemView.findViewById(R.id.btn_skip);
        }
    }
}
