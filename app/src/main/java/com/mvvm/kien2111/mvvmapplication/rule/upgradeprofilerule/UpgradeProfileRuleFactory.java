package com.mvvm.kien2111.mvvmapplication.rule.upgradeprofilerule;

import com.mvvm.kien2111.mvvmapplication.interfaces.ValidateUpgradeRequest;
import com.mvvm.kien2111.mvvmapplication.rule.AbstractFactoryRule;

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
