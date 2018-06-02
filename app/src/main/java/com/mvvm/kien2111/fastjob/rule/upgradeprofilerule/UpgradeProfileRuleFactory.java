package com.mvvm.kien2111.fastjob.rule.upgradeprofilerule;

import com.mvvm.kien2111.fastjob.interfaces.ValidateUpgradeRequest;
import com.mvvm.kien2111.fastjob.rule.AbstractFactoryRule;

import java.util.Arrays;
import java.util.List;

/**
 * Created by WhoAmI on 04/05/2018.
 */

public final class UpgradeProfileRuleFactory
        implements AbstractFactoryRule.AbstractFactoryRuleInterface<ValidateUpgradeRequest>{
    @Override
    public List<ValidateUpgradeRequest> getListRule() {
         return Arrays.asList(new CheckCurrentLevelRule());
    }
}
