package main.java.com.mygdx.game.network.response;

public class ErrorMessage {
    public String message;
    public ErrorNumber errorNumber;

    public ErrorMessage(){
    }

    public ErrorMessage(String message){
        this.message = message;
        this.errorNumber = ErrorNumber.OTHER;
    }

    public ErrorMessage(String message,ErrorNumber errorNumber){
        this.message = message;
        this.errorNumber = errorNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorNumber getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(ErrorNumber errorNumber) {
        this.errorNumber = errorNumber;
    }
}

