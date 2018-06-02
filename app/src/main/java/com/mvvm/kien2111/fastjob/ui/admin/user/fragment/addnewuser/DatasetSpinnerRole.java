package com.mvvm.kien2111.fastjob.ui.admin.user.fragment.addnewuser;

import com.mvvm.kien2111.fastjob.model.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donki on 4/18/2018.
 */

public class DatasetSpinnerRole {
    public List<Role> getDefault_lst() {
        return default_lst;
    }

    final List<Role> default_lst ;

    public DatasetSpinnerRole() {
        this.default_lst = new ArrayList<>();
        this.default_lst.add(new Role(Role.RoleMap.ADMIN.getType(),Role.RoleMap.ADMIN.getRolename(), Role.RoleStatus.ACTIVE));
        this.default_lst.add(new Role(Role.RoleMap.USER.getType(),Role.RoleMap.USER.getRolename(), Role.RoleStatus.ACTIVE));
    }
}
