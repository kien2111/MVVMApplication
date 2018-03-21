package com.mvvm.kien2111.mvvmapplication.retrofit.serialize;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mvvm.kien2111.mvvmapplication.model.Gender;

import java.io.IOException;

/**
 * Created by WhoAmI on 26/02/2018.
 */

public class EnumAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        if(rawType == Gender.class){
            return new EnumTypeAdapter<>();
        }
        return null;
    }
    public static class EnumTypeAdapter<T> extends TypeAdapter<T>{

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            if(value==null){
                out.nullValue();
                return;
            }
            Gender gender = (Gender)value;
            out.beginObject();
            out.name("gender");
            out.value(gender.getType());
            out.endObject();
        }

        @Override
        public T read(JsonReader in) throws IOException {
            return null;
        }
    }
}
