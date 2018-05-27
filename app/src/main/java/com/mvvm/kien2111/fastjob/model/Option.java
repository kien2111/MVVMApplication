package com.mvvm.kien2111.fastjob.model;

/**
 * Created by WhoAmI on 07/05/2018.
 */

public enum Option{
    FREELANCER(1,"freelancer"),EMPLOYER(2,"employer");
    private final int type;
    private final String name;
    Option(int type,String name){
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Option mapOptionName(String name){
        for(Option option:values()){
            if(name.equals(option.getName())){
                return option;
            }
        }
        return FREELANCER;
    }
    public static Option mapOption(int type){
        for(Option option:values()){
            if(type == option.getType()){
                return option;
            }
        }
        return FREELANCER;
    }

    public int getType() {
        return type;
    }
}