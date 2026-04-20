package me.cyber.gui;

import me.cyber.Limbo;
import me.cyber.gui.components.LimboButton;
import me.cyber.gui.tictactoe.Board;
import me.cyber.model.TMode;
import me.cyber.utils.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class GameSelector extends Screen {


    public GameSelector() {
        super(Text.literal("d"));
    }

    @Override
    protected void init() {
        super.init();

        int screenWidth = this.width;
        int screenHeight = this.height;


        int rectWidth = 500;
        int rectHeight = 300;

        int x1 = (screenWidth / 2) - (rectWidth / 2);
        int y1 = (screenHeight / 2) - (rectHeight / 2);



        this.addDrawableChild(new LimboButton(x1+150, y1+100,200, 35).onClick(e ->{
            Limbo.mc.setScreen(new Board(TMode.EASY));
        }));
        this.addDrawableChild(new LimboButton(x1+150, y1+150,200, 35).onClick(e ->{
            Limbo.mc.setScreen(new Board(TMode.HARD));
        }));
        this.addDrawableChild(new LimboButton(x1+150, y1+200,200, 35).onClick(e ->{
            Limbo.mc.setScreen(new Board(TMode.IMPOSSIBLE));
        }));

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {


        // Using this.width and this.height ensures it scales with the user's GUI scale settings
        int screenWidth = this.width;
        int screenHeight = this.height;

        // Define the size of your rectangle
        int rectWidth = 500;
        int rectHeight = 300;

        // Calculate coordinates to keep it perfectly centered
        final int x1 = (screenWidth / 2) - (rectWidth / 2);
        final int y1 = (screenHeight / 2) - (rectHeight / 2);
        int x2 = x1 + rectWidth;
        int y2 = y1 + rectHeight;

        int color = 0xFFFFFFFF;

        RenderUtils.fillRoundedRect(context, x1, y1, x2, y2, color);
        super.render(context, mouseX, mouseY, deltaTicks);


        RenderUtils.drawText(context, textRenderer, Text.literal("Easy"), x1+225, y1+110, 2, "BFE86A");
        RenderUtils.drawText(context, textRenderer, Text.literal("Hard"), x1+225, y1+160, 2, "#D2042D");
        RenderUtils.drawText(context, textRenderer, Text.literal("Impossible"), x1+200, y1+210, 2, "#702963");
    }
}
