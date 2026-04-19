package me.cyber.model;

import me.cyber.gui.components.CustomFadeButton;
import me.cyber.gui.tictactoe.SignButton;

public class Slot {
    private int pos;
    private SignButton tButton;
    private Sign sign;

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
