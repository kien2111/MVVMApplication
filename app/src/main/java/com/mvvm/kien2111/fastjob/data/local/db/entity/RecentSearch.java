package com.mvvm.kien2111.fastjob.data.local.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.mvvm.kien2111.fastjob.data.local.db.converter.AppConverter;

/**
 * Created by WhoAmI on 05/05/2018.
 */
@Entity(primaryKeys = {"idquery"},tableName = "recentsearch")
@TypeConverters(AppConverter.class)
public class RecentSearch implements Parcelable{

    public static final Creator<RecentSearch> CREATOR = new Creator<RecentSearch>() {
        @Override
        public RecentSearch createFromParcel(Parcel in) {
            return new RecentSearch(in);
        }

        @Override
        public RecentSearch[] newArray(int size) {
            return new RecentSearch[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(searchType, flags);
        dest.writeString(idquery);
        dest.writeString(name);
        dest.writeString(imagePath);
        dest.writeLong(created_at);
    }

    private SearchType searchType;

    @NonNull
    private String idquery;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image_path")
    private String imagePath;

    @ColumnInfo(name = "created_at")
    private long created_at;

    protected RecentSearch(Parcel in) {
        searchType = in.readParcelable(SearchType.class.getClassLoader());
        idquery = in.readString();
        name = in.readString();
        imagePath = in.readString();
        created_at = in.readLong();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public String getIdquery() {
        return idquery;
    }

    public void setIdquery(String idquery) {
        this.idquery = idquery;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Ignore
    public RecentSearch(Builder builder){
        this.idquery = builder.idquery;
        this.name = builder.name;
        this.searchType = builder.searchType;
        this.imagePath = builder.imagePath;
        this.created_at = builder.created_at;
    }

    @Override
    public String toString() {
        return "RecentSearch{" +
                "searchType=" + searchType +
                ", idquery='" + idquery + '\'' +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", created_at=" + created_at +
                '}';
    }

    public RecentSearch(){}

    public static class Builder{
        private SearchType searchType;

        @NonNull
        private String idquery;

        private String name;

        @ColumnInfo(name = "image_path")
        private String imagePath;

        @ColumnInfo(name = "created_at")
        private long created_at;

        public RecentSearch build(){
            return new RecentSearch(this);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSearchType(SearchType searchType) {
            this.searchType = searchType;
            return this;
        }

        public Builder setIdquery(@NonNull String idquery) {
            this.idquery = idquery;
            return this;
        }

        public Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder setCreated_at(long created_at) {
            this.created_at = created_at;
            return this;
        }
    }

    public enum SearchType implements Parcelable{
        OTHER(0),CATEGORY(1),PROFILE(2);

        private int type;
        SearchType(int type){
            this.type = type;
        }

        SearchType(Parcel in) {
            type = in.readInt();
        }

        public static final Creator<SearchType> CREATOR = new Creator<SearchType>() {
            @Override
            public SearchType createFromParcel(Parcel in) {
                return SearchType.values()[in.readInt()];
            }

            @Override
            public SearchType[] newArray(int size) {
                return new SearchType[size];
            }
        };

        public int getType() {
            return type;
        }

        public static SearchType mapSearchType(int type){
            for(SearchType s : values()){
                if(type==s.getType()){
                    return s;
                }
            }
            return SearchType.OTHER;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(type);
        }
    }
}
