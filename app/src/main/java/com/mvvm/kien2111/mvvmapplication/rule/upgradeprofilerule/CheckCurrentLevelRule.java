package com.mvvm.kien2111.mvvmapplication.rule.upgradeprofilerule;

import com.mvvm.kien2111.mvvmapplication.interfaces.ValidateUpgradeRequest;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.model.Profile;

/**
 * Created by WhoAmI on 04/05/2018.
 */

public class CheckCurrentLevelRule implements ValidateUpgradeRequest<Profile,Priority>{
    @Override
    public void execute(Profile dataNeedToValidate) {

    }

    @Override
    public void execute(Profile dataneedToValid, Priority requestUserMake) throws Exception {
        if(dataneedToValid.getPriority()==requestUserMake){
            throw new Exception("Bạn không thể yêu cầu thăng cấp hồ sơ khi bạn đang ở trạng thái này");
        }
    }
}
