package com.mvvm.kien2111.mvvmapplication.retrofit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mvvm.kien2111.mvvmapplication.model.Approve_Publish;
import com.mvvm.kien2111.mvvmapplication.model.Gender;
import com.mvvm.kien2111.mvvmapplication.model.Priority;
import com.mvvm.kien2111.mvvmapplication.model.Role;
import com.mvvm.kien2111.mvvmapplication.model.User;

import java.io.IOException;

/**
 * Created by WhoAmI on 30/03/2018.
 */

public class EnumTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawtype = (Class<T>)type.getRawType();
        if(!rawtype.isEnum())
            return null;
        if(Role.RoleStatus.class == rawtype){
            return (TypeAdapter<T>) new RoleTypeAdapter();
        }
        if(Gender.class == rawtype){
            return (TypeAdapter<T>) new GenderTypeAdapter();
        }
        if(Priority.class == rawtype){
            return (TypeAdapter<T>)new PriorityTypeAdapter();
        }
        if(Approve_Publish.class == rawtype){
            return (TypeAdapter<T>)new Approve_PublishTypeAdapter();
        }
        return null;
    }

    static class GenderTypeAdapter extends TypeAdapter<Gender>{
        @Override
        public void write(JsonWriter out, Gender value) throws IOException {
            out.value(value.getType());
        }
        @Override
        public Gender read(JsonReader in) throws IOException {
            return Gender.mapGender(in.nextInt());
        }
    }
    static class  RoleTypeAdapter extends TypeAdapter<Role.RoleStatus>{
        @Override
        public void write(JsonWriter out, Role.RoleStatus value) throws IOException {
            out.value(value.getType());
        }
        @Override
        public Role.RoleStatus read(JsonReader in) throws IOException {
            return Role.RoleStatus.mapRole(in.nextInt());
        }
    }
    static class Approve_PublishTypeAdapter extends  TypeAdapter<Approve_Publish>{
        @Override
        public void write(JsonWriter out, Approve_Publish value) throws IOException {
            out.value(value.getType());
        }
        @Override
        public Approve_Publish read(JsonReader in) throws IOException {
            return Approve_Publish.mapApprovePublish(in.nextInt());
        }
    }
    static class PriorityTypeAdapter extends TypeAdapter<Priority>{
        @Override
        public void write(JsonWriter out, Priority value) throws IOException {
            out.value(value.getType());
        }

        @Override
        public Priority read(JsonReader in) throws IOException {
            return Priority.mapPriority(in.nextInt());
        }
    }
}
