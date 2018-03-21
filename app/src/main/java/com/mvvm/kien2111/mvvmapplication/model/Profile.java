package com.mvvm.kien2111.mvvmapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class Profile {
    @Expose
    @SerializedName("idprofile")
    private String idprofile;
    @Expose
    @SerializedName("career_objective")
    private String career_objective;

    public String getIdprofile() {
        return idprofile;
    }

    public void setIdprofile(String idprofile) {
        this.idprofile = idprofile;
    }

    public String getCareer_objective() {
        return career_objective;
    }

    public void setCareer_objective(String career_objective) {
        this.career_objective = career_objective;
    }

    public double getSalary_expected() {
        return salary_expected;
    }

    public void setSalary_expected(double salary_expected) {
        this.salary_expected = salary_expected;
    }

    public String getExperienced_description() {
        return experienced_description;
    }

    public void setExperienced_description(String experienced_description) {
        this.experienced_description = experienced_description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getAttachment_resume_url() {
        return attachment_resume_url;
    }

    public void setAttachment_resume_url(String attachment_resume_url) {
        this.attachment_resume_url = attachment_resume_url;
    }

    @Expose
    @SerializedName("salary_expected")
    private double salary_expected;
    @Expose
    @SerializedName("experienced_description")
    private String experienced_description;
    @Expose
    @SerializedName("level")
    private Priority priority;
    @Expose
    @SerializedName("attachment_resume_url")
    private String attachment_resume_url;

}
