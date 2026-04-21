package me.cyber.gui.tictactoe;

import me.cyber.gui.components.LimboButton;
import me.cyber.model.Sign;
import me.cyber.utils.IdentifierUtils;
import me.cyber.utils.RenderUtils;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

/**
 * This class is used for the button inside the Tic-tac-toe board.
 */
public class SignButton extends LimboButton {
    private final Identifier X_TEXTURE = IdentifierUtils.of("textures/gui/cross-svgrepo-comt3.png");
    private final Identifier O_TEXTURE = IdentifierUtils.of("textures/gui/circle-svgrepo-com.png");
    private CustomClick listener;
    private float hoverProgress = 0.0f;
    private final String color;
    private String hcolor;
    private Sign slotOwner;
    private boolean isWinningButton;

    public SignButton(int x, int y, int w, int h, String color, String hcolor) {
        super(x, y, w, h, color, hcolor);

        this.color = color.replace("#", "");
        this.hcolor = hcolor.replace("#", "");
        slotOwner = Sign.EMPTY;
        this.isWinningButton = false;

    }


    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

        //holy crap this is so poorly made
        int x1 = this.getX();
        int y1 = this.getY();
        int x2 = x1 + this.getWidth();
        int y2 = y1 + this.getHeight();
        float animationSpeed = 0.15f * deltaTicks;

        if (getSign().equals(Sign.EMPTY)) {

            if (this.isHovered()) {
                hoverProgress = Math.min(1.0f, hoverProgress + animationSpeed);
            } else {
                hoverProgress = Math.max(0.0f, hoverProgress - animationSpeed);
            }

            int currentColor = ColorHelper.lerp(hoverProgress, RenderUtils.colorConvert(color), RenderUtils.colorConvert(hcolor));
            RenderUtils.fillRoundedRect(context, x1, y1, x2, y2, currentColor);

        } else {

            if(!isWinningButton){
                RenderUtils.fillRoundedRect(context, x1, y1, x2, y2, RenderUtils.colorConvert("e3d5ca"));
            }else{
                RenderUtils.fillRoundedRect(context, x1, y1, x2, y2, RenderUtils.colorConvert("#4f6d3a"));
            }

            if (getSign().equals(Sign.O)) {
                context.drawTexture(RenderPipelines.GUI_TEXTURED, O_TEXTURE, x1 , y1, 0.0F, 0.0F, 50, 50, 50, 50);
            } else if (getSign().equals(Sign.X)) {
                context.drawTexture(RenderPipelines.GUI_TEXTURED, X_TEXTURE, x1, y1, 0.0F, 0.0F, 50, 50, 50, 50);
            }

        }
    }

    public Sign getSign() {
        return slotOwner;
    }

    public void updateOwner(Sign sign) {
        this.slotOwner = sign;
    }


    public boolean isWinningButton() {
        return isWinningButton;
    }

    public void setWinningButton(boolean winningButton) {
        isWinningButton = winningButton;
    }
}
