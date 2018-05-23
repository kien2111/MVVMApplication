package com.mvvm.kien2111.mvvmapplication.rule;

import com.mvvm.kien2111.mvvmapplication.rule.upgradeprofilerule.UpgradeProfileRuleFactory;

import java.util.List;

/**
 * Created by WhoAmI on 04/05/2018.
 */

public final class AbstractFactoryRule {
    public enum ValidateRule{
        VALIDATE_UPGRADE_REQUEST,
        VALIDATE_REGISTER_REQUEST,
    }

    public synchronized static AbstractFactoryRuleInterface getFactory(ValidateRule option){
        switch (option){
            case VALIDATE_UPGRADE_REQUEST:
                return new UpgradeProfileRuleFactory();
            case VALIDATE_REGISTER_REQUEST:
                break;
            default:
                break;
        }
        return null;
    }

    public interface AbstractFactoryRuleInterface<T>{
        List<T> getListRule();
    }
}
