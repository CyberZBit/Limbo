package me.cyber.gui.tictactoe;

import me.cyber.gui.components.LimboButton;
import me.cyber.model.Sign;
import me.cyber.utils.IdentifierUtils;
import me.cyber.utils.RenderUtils;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

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

    public SignButton(int x, int y, int w, int h, String color, String hcolor) {
        super(x, y, w, h, color, hcolor);

        this.color = color.replace("#", "");
        this.hcolor = hcolor.replace("#", "");
        slotOwner = Sign.EMPTY;

    }

    /*
    public void onClick(CustomClick click) {
        this.listener = click;
    }

    /*
    @Override
    public void onPress(AbstractInput input) {
        if (listener != null) {
            listener.onClick(input);
        }
    }

     */

    //TODO: Use all utils methods and clean up.
    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

        //holy crap this is so poorly made
        int x1 = this.getX();
        int y1 = this.getY();
        int x2 = x1 + this.getWidth();
        int y2 = y1 + this.getHeight();

        int baseColor = (0xFF << 24) | Integer.parseInt(color, 16);
        int hoverColor = (0xFF << 24) | Integer.parseInt(hcolor, 16);

        float animationSpeed = 0.15f * deltaTicks;

        if (this.slotOwner.equals(Sign.EMPTY)) {
            if (this.isHovered()) {
                hoverProgress = Math.min(1.0f, hoverProgress + animationSpeed);
            } else {
                hoverProgress = Math.max(0.0f, hoverProgress - animationSpeed);
            }

            int currentColor = RenderUtils.lerpColor(baseColor, hoverColor, hoverProgress);


            RenderUtils.fillRoundedRect(context, x1, y1, x2, y2, currentColor);
        } else {
            int c = (0xFF << 24) | Integer.parseInt("e3d5ca", 16);
            RenderUtils.fillRoundedRect(context, x1, y1, x2, y2, c);
            if (this.slotOwner.equals(Sign.O)) {
                context.drawTexture(RenderPipelines.GUI_TEXTURED, O_TEXTURE, x1 + 10, y1 + 10, 0.0F, 0.0F, 60, 60, 60, 60);
                //RenderUtils.fillRoundedRect(context, x1, y1, x2, y2, c);

            } else if (this.slotOwner.equals(Sign.X)) {

                context.drawTexture(RenderPipelines.GUI_TEXTURED, X_TEXTURE, x1, y1, 0.0F, 0.0F, 80, 80, 80, 80);


            }

        }

        //int currentColor = RenderUtils.lerpColor(baseColor, hoverColor, hoverProgress);


    }

    public Sign getSign() {
        return slotOwner;
    }

    public void updateOwner(Sign sign) {
        this.slotOwner = sign;
    }


}
