package me.cyber.gui.components;

import me.cyber.Limbo;
import me.cyber.utils.RenderUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.input.AbstractInput;
import net.minecraft.text.Text;
import org.joml.Matrix3x2f;

public class CustomFadeButton extends PressableWidget {
    private String color;
    private String hcolor;
    private float hoverProgress = 0.0f;

    public CustomFadeButton(int x, int y, int w, int h, String color, String hcolor) {
        super(x, y, w, h, Text.literal(""));

        this.color = color.replace("#", "");
        this.hcolor = hcolor.replace("#", "");

    }

    @Override
    public void onPress(AbstractInput input) {

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
