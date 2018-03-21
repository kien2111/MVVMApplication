package com.mvvm.kien2111.mvvmapplication.retrofit.serialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mvvm.kien2111.mvvmapplication.model.Gender;

import java.lang.reflect.Type;

/**
 * Created by WhoAmI on 19/03/2018.
 */

public class EnumTypeDeserialize implements JsonDeserializer<Gender> {

    @Override
    public Gender deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        int type = json.getAsInt();
        return Gender.mapGender(type);
    }
}
