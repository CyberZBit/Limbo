package me.cyber.gui.components;

import me.cyber.Limbo;
import me.cyber.gui.tictactoe.CustomClick;
import me.cyber.utils.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.input.AbstractInput;
import net.minecraft.text.Text;

//TODO: completely redo this button and make it more dynamic
public class LimboButton extends PressableWidget {
    private String color;
    private String hcolor;
    private float hoverProgress = 0.0f;
    private CustomClick listener;
    private boolean buttonWithText;
    private Text buttonText;
    private float textScale;

    public LimboButton(int x, int y, int w, int h, String color, String hcolor) {
        super(x, y, w, h, Text.literal(""));

        this.color = color.replace("#", "");
        this.hcolor = hcolor.replace("#", "");
        buttonWithText = false;

    }

    public LimboButton(int x, int y, int w, int h) {
        super(x, y, w, h, Text.literal(""));

        //default white
        this.color = "abd05f";
        this.hcolor = "c5ea78";
        buttonWithText = false;
    }

    public LimboButton(int x, int y, int w, int h, Text buttonText) {
        super(x, y, w, h, Text.literal(""));

        //default white
        this.color = "abd05f";
        this.hcolor = "c5ea78";
        buttonWithText = true;
        this.buttonText = buttonText;
        this.textScale = 1f;
    }

    public LimboButton(int x, int y, int w, int h, Text buttonText, int textScale) {
        super(x, y, w, h, Text.literal(""));

        //default white
        this.color = "abd05f";
        this.hcolor = "c5ea78";
        buttonWithText = true;
        this.buttonText = buttonText;
        this.textScale = textScale;
    }


    public LimboButton onClick(CustomClick click) {
        this.listener = click;
        return this; // Returning 'this' allows you to chain methods
    }

    @Override
    public void onPress(AbstractInput input) {
        if (listener != null) {
            listener.onClick(input);
        }
    }


    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public boolean isClickable() {
        return super.isClickable();
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
    }

    @Override
    public boolean isHovered() {
        return super.isHovered();
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int x1 = this.getX();
        int y1 = this.getY();
        int x2 = x1 + this.getWidth();
        int y2 = y1 + this.getHeight();

        if (buttonWithText) {
            RenderUtils.drawText(context, Limbo.mc.textRenderer, buttonText, x1, y1, ((textScale == 0) ? 1f : textScale), color);
        }

        int baseColor = (0xFF << 24) | Integer.parseInt(color, 16);
        int hoverColor = (0xFF << 24) | Integer.parseInt(hcolor, 16);

        float animationSpeed = 0.15f * deltaTicks;

        if (this.isHovered()) {
            hoverProgress = Math.min(1.0f, hoverProgress + animationSpeed);
        } else {
            hoverProgress = Math.max(0.0f, hoverProgress - animationSpeed);
        }

        int currentColor = RenderUtils.lerpColor(baseColor, hoverColor, hoverProgress);
        RenderUtils.fillRoundedRect(context, x1, y1, x2, y2, currentColor);


    }


}
