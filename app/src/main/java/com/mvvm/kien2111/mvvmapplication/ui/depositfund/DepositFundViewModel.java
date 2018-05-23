package com.mvvm.kien2111.mvvmapplication.ui.depositfund;

import android.arch.lifecycle.MutableLiveData;

import com.mvvm.kien2111.mvvmapplication.base.BaseMessage;
import com.mvvm.kien2111.mvvmapplication.base.BaseViewModel;
import com.mvvm.kien2111.mvvmapplication.data.UserRepository;
import com.mvvm.kien2111.mvvmapplication.data.local.pref.PreferenceLiveData;
import com.mvvm.kien2111.mvvmapplication.data.remote.model.TopUpRequest;
import com.mvvm.kien2111.mvvmapplication.model.TableExchangeModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WhoAmI on 28/04/2018.
 */

public class DepositFundViewModel extends BaseViewModel {
    private final MutableLiveData<List<TableExchangeModel>> tableExchangeModelMutableLiveData = new MutableLiveData<>();
    private final UserRepository userRepository;
    private final PreferenceLiveData preferenceLiveData;
    @Inject
    public DepositFundViewModel(EventBus eventBus,
                                PreferenceLiveData preferenceLiveData,
                                UserRepository userRepository) {
        super(eventBus);
        this.userRepository = userRepository;
        this.preferenceLiveData = preferenceLiveData;
        this.preferenceLiveData.setUser(userRepository.getUserData().getUser());
        compositeDisposable.add(userRepository.fetchTableExchange()
                .subscribe(tableExchangeModelMutableLiveData::postValue));
    }

    public MutableLiveData<List<TableExchangeModel>> getTableExchangeModelMutableLiveData() {
        return tableExchangeModelMutableLiveData;
    }

    public PreferenceLiveData getPreferenceLiveData() {
        return preferenceLiveData;
    }

    public void topUpMoney(TopUpRequest topUpRequest){
        compositeDisposable.add(userRepository
                .topUpMoney(topUpRequest)
                .doOnComplete(() -> {
                    compositeDisposable.add(userRepository.syncLocalVersusPromoteData()
                            .subscribe());
                })
                .subscribe(() -> BaseMessage.success("Nạp tiền thành công"),
                        BaseMessage::error));
    }
}
