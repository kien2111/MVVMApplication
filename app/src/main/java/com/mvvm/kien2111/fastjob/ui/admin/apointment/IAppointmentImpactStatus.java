package com.mvvm.kien2111.fastjob.ui.admin.apointment;

import com.mvvm.kien2111.fastjob.model.ImpactApointment;

/**
 * Created by donki on 5/7/2018.
 */

public interface IAppointmentImpactStatus {
    void acceptAppointment(ImpactApointment impactApointment);
    void  skipAppointment(ImpactApointment impactApointment);
}
