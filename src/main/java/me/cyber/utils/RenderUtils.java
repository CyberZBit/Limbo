package me.cyber.utils;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.joml.Matrix3x2fStack;

public class RenderUtils {
    public static void fillRoundedRect(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        context.fill(x1 + 1, y1, x2 - 1, y2, color);       // Central body
        context.fill(x1, y1 + 1, x1 + 1, y2 - 1, color);   // Left edge
        context.fill(x2 - 1, y1 + 1, x2, y2 - 1, color);   // Right edge
    }


    public static void drawText(DrawContext context, TextRenderer textRenderer, Text text, int x, int y, float scale, String hexColor) {
        Matrix3x2fStack matrices = context.getMatrices();
        matrices.pushMatrix();
        matrices.scale(scale, scale);
        context.drawText(textRenderer, text, (int) (x / scale), (int) (y / scale), colorConvert(hexColor), true);
        matrices.popMatrix();
    }

    /**
     * Converts HEX colors to minecraft compatible colors.
     */
    public static int colorConvert(String hexcode){
        return (0xFF << 24) | Integer.parseInt(hexcode.replace("#", ""), 16);
    }
}
