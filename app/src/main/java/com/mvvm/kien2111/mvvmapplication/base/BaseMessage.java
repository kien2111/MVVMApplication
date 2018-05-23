package com.mvvm.kien2111.mvvmapplication.base;

/**
 * Created by WhoAmI on 17/03/2018.
 */

public class BaseMessage {

    public static BaseMessage success(String message){
        return new BaseMessage(State.SUCCESS,message);
    }
    public static BaseMessage error(String message){return new BaseMessage(State.FAIL,message);}
    public static BaseMessage error(String message,Throwable cause){return new BaseMessage(message,cause);}
    public static BaseMessage error(Throwable cause){return new BaseMessage(cause);}

    public BaseMessage(){}

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    private Throwable throwable;
    private String message;
    private State state;

    public State getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }

    private Throwable cause;

    public BaseMessage(State state,String message){
        this.message = message;
        this.state = state;
        if(state==State.FAIL){
            this.cause = new Throwable(message);
        }
    }
    public BaseMessage(String message){this.message = message;}
    public BaseMessage(Throwable cause){this.cause=cause;this.state = State.FAIL;this.message = cause.getMessage();}
    public BaseMessage(String message,Throwable cause){
        this.state = State.FAIL;
        if(message==null && cause==null){
            throw new IllegalArgumentException("No param supply");
        }else if(message!=null && cause !=null){
           this.message = message;
           this.cause = cause;
        }else{
            if(message!=null && cause ==null){
                this.cause = new Throwable(message);
                this.message = message;
            }else if(message==null && cause !=null){
                this.message = cause.getMessage();
                this.cause = cause;
            }
        }
    }

    public enum State{
        SUCCESS,FAIL
    }

}
