package me.cyber.utils;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.joml.Matrix3x2fStack;

public class RenderUtils {


    // Helper method to smoothly blend the colors
    public static int lerpColor(int colorStart, int colorEnd, float progress) {
        int aStart = (colorStart >> 24) & 0xFF;
        int rStart = (colorStart >> 16) & 0xFF;
        int gStart = (colorStart >> 8) & 0xFF;
        int bStart = colorStart & 0xFF;

        int aEnd = (colorEnd >> 24) & 0xFF;
        int rEnd = (colorEnd >> 16) & 0xFF;
        int gEnd = (colorEnd >> 8) & 0xFF;
        int bEnd = colorEnd & 0xFF;

        int a = (int) (aStart + (aEnd - aStart) * progress);
        int r = (int) (rStart + (rEnd - rStart) * progress);
        int g = (int) (gStart + (gEnd - gStart) * progress);
        int b = (int) (bStart + (bEnd - bStart) * progress);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }


    public static void fillRoundedRect(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        context.fill(x1 + 1, y1, x2 - 1, y2, color);       // Central body
        context.fill(x1, y1 + 1, x1 + 1, y2 - 1, color);   // Left edge
        context.fill(x2 - 1, y1 + 1, x2, y2 - 1, color);   // Right edge
    }


    public static void drawText(DrawContext context, TextRenderer textRenderer, Text text, int x, int y, float scale, String hexColor) {
        Matrix3x2fStack matrices = context.getMatrices();
        int c1 = (0xFF << 24) | Integer.parseInt(hexColor.replace("#", ""), 16);

        matrices.pushMatrix();
        matrices.scale(scale, scale);

        context.drawText(textRenderer, text, (int) (x / scale), (int) (y / scale), c1, true);

        matrices.popMatrix();
    }
}
