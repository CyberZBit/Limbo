package me.cyber.model;

import me.cyber.gui.tictactoe.SignButton;

public class Slot {
    private final int pos;
    private final SignButton tButton;

    public Slot(SignButton tButton, int pos) {
        this.tButton = tButton;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public Sign getSign(){
       return tButton.getSign();
    }

    public SignButton getButton() {
        return tButton;
    }

    public void setSign(Sign sign){
        tButton.updateOwner(sign);
    }




}
