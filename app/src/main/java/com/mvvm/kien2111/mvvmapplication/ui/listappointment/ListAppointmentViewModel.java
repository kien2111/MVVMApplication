package com.mvvm.kien2111.mvvmapplication.ui.listappointment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.AppointmentRepository;
import com.mvvm.kien2111.mvvmapplication.model.BaseNextPageHandler;
import com.mvvm.kien2111.mvvmapplication.model.LoadMoreState;
import com.mvvm.kien2111.mvvmapplication.model.Resource;
import com.mvvm.kien2111.mvvmapplication.ui.listappointment.common.AppointmentModel;
import com.mvvm.kien2111.mvvmapplication.util.AbsentLiveData;
import com.mvvm.kien2111.mvvmapplication.util.MyLiveDataReactiveStream;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 07/04/2018.
 */

public class ListAppointmentViewModel extends BaseViewModel {
    private final MutableLiveData<PickOption> roleOptionMutableLiveData = new MutableLiveData<>();

    @Inject
    public ListAppointmentViewModel(EventBus eventBus) {
        super(eventBus);

    }

    public MutableLiveData<PickOption> getRoleOptionMutableLiveData() {
        return roleOptionMutableLiveData;
    }

    public void pickOption(PickOption roleOption){
        if(roleOption!=null && roleOption!= roleOptionMutableLiveData.getValue()){
            roleOptionMutableLiveData.setValue(roleOption);
        }
    }
    public static class PickOption{
        Option option;
        String iduser;

        public PickOption(Option option, String iduser) {
            this.option = option;
            this.iduser = iduser;
        }

        public Option getOption() {
            return option;
        }

        public void setOption(Option option) {
            this.option = option;
        }

        public String getIduser() {
            return iduser;
        }

        public void setIduser(String iduser) {
            this.iduser = iduser;
        }

        public enum Option{
            FREELANCER(1,"freelancer"),EMPLOYER(2,"employer");
            private final int type;
            private final String name;
            Option(int type,String name){
                this.type = type;
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public static Option mapOptionName(String name){
                for(Option option:values()){
                    if(name.equals(option.getName())){
                        return option;
                    }
                }
                return FREELANCER;
            }
            public static Option mapOption(int type){
                for(Option option:values()){
                    if(type == option.getType()){
                        return option;
                    }
                }
                return FREELANCER;
            }

            public int getType() {
                return type;
            }
        }
    }

}
