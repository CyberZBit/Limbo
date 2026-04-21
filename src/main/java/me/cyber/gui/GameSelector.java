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

        int centerx = this.width / 2;
        final int x1 = (centerx) - (200 / 2);
        final int y1 = 100;

        this.addDrawableChild(new LimboButton(x1, y1 + 40, 200, 35, Text.literal("Easy"), "#5e6b5a", 1.5f).onClick(e -> {
            Limbo.mc.setScreen(new Board(TMode.EASY));
        }));
        this.addDrawableChild(new LimboButton(x1, y1 + 80, 200, 35, Text.literal("Hard"), "#d9384b", 1.5f).onClick(e -> {
            Limbo.mc.setScreen(new Board(TMode.HARD));
        }));
        this.addDrawableChild(new LimboButton(x1, y1 + 120, 200, 35, Text.literal("Impossible"), "#8a4572", 1.5f).onClick(e -> {
            Limbo.mc.setScreen(new Board(TMode.IMPOSSIBLE));
        }));

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);


    }
}
