package com.mygdx.game.network.response;

import com.mygdx.game.utility.Toast;

public class MeepleIsPlacedMessage {
    public String message;
    public Toast toast;

    public MeepleIsPlacedMessage(){
    }

    public MeepleIsPlacedMessage(Toast toast){
        this.toast = toast;
    }

    public Toast getToast() {
        return toast;
    }

    public void setToast(Toast toast) {
        this.toast = toast;
    }
}