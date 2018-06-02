package com.mvvm.kien2111.fastjob.model;


/**
 * Created by WhoAmI on 21/01/2018.
 */

/*public class Credential  {



    private ObservableField<String> username,password,token;

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getToken() {
        return token.get();
    }

    public void setToken(String token) {
        this.token.set(token);
    }



    public static class Builder{
        private ObservableField<String> username,password,token;

        public Credential build(){
            return new Credential(this);
        }

        public Builder setToken(String token){
            this.token.set(token);
            return this;
        }
        public Builder setPassword(String password){
            this.password.set(password);
            return this;
        }
        public Builder setUsername(String username){
            this.username.set(username);
            return this;
        }
    }
    private Credential(Builder builder){
        this.username = builder.username;
        this.password = builder.password;
        this.token = builder.token;
    }
}*/
public class Credential  {



    private String username;
    private String password;
    private String token;

    public String getUsername() {
        return username==null?"":username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password==null?"":password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token==null?"":token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class Builder{
        private String username,password,token;

        public Credential build(){
            return new Credential(this);
        }

        public Builder setToken(String token){
            this.token = token;
            return this;
        }
        public Builder setPassword(String password){
            this.password = password;
            return this;
        }
        public Builder setUsername(String username){
            this.username= username;
            return this;
        }
    }
    private Credential(Builder builder){
        this.username = builder.username;
        this.password = builder.password;
        this.token = builder.token;
    }
}
