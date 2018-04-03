package com.mvvm.kien2111.mvvmapplication.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mvvm.kien2111.mvvmapplication.data.local.db.entity.Category;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class Profile {
    @NonNull
    @Expose
    @SerializedName("idprofile")
    private String idprofile;
    @Expose
    @SerializedName("career_objective")
    private String career_objective;

    @Expose
    @SerializedName("salary_expected_to")
    private double salary_expected_to;

    @Expose
    @SerializedName("salary_expected_from")
    private double salary_expected_from;

    @Expose
    @SerializedName("experienced_description")
    private String experienced_description;
    @Expose
    @SerializedName("level")
    private Priority priority;

    @Expose
    @SerializedName("attachment_resume_url")
    private String attachment_resume_url;

    @Expose
    @SerializedName("category")
    private Category category;

    @Expose
    @SerializedName("approve_publish")
    private Approve_Publish approve_publish;

    @Expose
    @SerializedName("summary")
    private String summary;

    @NonNull
    public String getIdprofile() {
        return idprofile;
    }

    public void setIdprofile(@NonNull String idprofile) {
        this.idprofile = idprofile;
    }

    public String getCareer_objective() {
        return career_objective;
    }

    public void setCareer_objective(String career_objective) {
        this.career_objective = career_objective;
    }

    public double getSalary_expected_to() {
        return salary_expected_to;
    }

    public void setSalary_expected_to(double salary_expected_to) {
        this.salary_expected_to = salary_expected_to;
    }

    public double getSalary_expected_from() {
        return salary_expected_from;
    }

    public void setSalary_expected_from(double salary_expected_from) {
        this.salary_expected_from = salary_expected_from;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Approve_Publish getApprove_publish() {
        return approve_publish;
    }

    public void setApprove_publish(Approve_Publish approve_publish) {
        this.approve_publish = approve_publish;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
