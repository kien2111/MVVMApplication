package com.mvvm.kien2111.mvvmapplication.base;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class BaseMessage {
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public BaseMessage(){}

    public BaseMessage(Throwable throwable){this.throwable=throwable;}

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    private Throwable throwable;
    private String errorMessage;

    public Throwable getCause() {
        return cause;
    }

    private Throwable cause;

    public BaseMessage(String errorMessage){
        this.errorMessage = errorMessage;
        this.cause = new Throwable(errorMessage);
    }
    public BaseMessage(Throwable cause){this.cause=cause;this.errorMessage = cause.getMessage();}
    public BaseMessage(String errorMessage,Throwable cause){
        if(errorMessage==null && cause==null){
            throw new IllegalArgumentException("No param supply");
        }else if(errorMessage!=null && cause !=null){
           this.errorMessage = errorMessage;
           this.cause = cause;
        }else{
            if(errorMessage!=null && cause ==null){
                this.cause = new Throwable(errorMessage);
                this.errorMessage = errorMessage;
            }else if(errorMessage==null && cause !=null){
                this.errorMessage = cause.getMessage();
                this.cause = cause;
            }
        }

    }

}
