package com.mvvm.kien2111.fastjob.ui.upgrade.common;

import com.mvvm.kien2111.fastjob.exception.LastestRequestUpgradeProfileExistException;
import com.mvvm.kien2111.fastjob.exception.ValidationRequestUpgradeException;
import com.mvvm.kien2111.fastjob.model.Priority;
import com.mvvm.kien2111.fastjob.model.User;

/**
 * Created by WhoAmI on 09/05/2018.
 */

public abstract class RequestUpgradeHandleCurrentLogic {
    private final RequestUpgradeModel lastestRequestUpgradeModel;
    private final User currentUser;
    private final Priority levelwanttoupgrade;
    public RequestUpgradeHandleCurrentLogic(RequestUpgradeModel lastestRequestUpgradeModel,
                                            User currentUser,
                                            Priority levelwanttoupgrade){
        this.lastestRequestUpgradeModel = lastestRequestUpgradeModel;
        this.currentUser = currentUser;
        this.levelwanttoupgrade = levelwanttoupgrade;
    }

    public boolean isLastestRequestExist(){
        if(lastestRequestUpgradeModel!=null){
            return true;
        }
        return false;
    }

    public void validateBeforeRequest() throws ValidationRequestUpgradeException, LastestRequestUpgradeProfileExistException {
        if(currentUser.getProfile().getPriority()==levelwanttoupgrade){
            throw new ValidationRequestUpgradeException("Bạn không thể yêu cầu thăng cấp hồ sơ khi bạn đang ở trạng thái này");
        }
        if(isLastestRequestExist()){
            throw new LastestRequestUpgradeProfileExistException("Hãy hủy bỏ lần yêu cầu thăng cấp hồ sơ trước đó của bạn");
        }
        sendRequest();
    }

    protected abstract void sendRequest();
}
