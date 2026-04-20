package me.cyber.gui.tictactoe;

import me.cyber.Limbo;
import me.cyber.model.Sign;
import me.cyber.model.Slot;
import me.cyber.model.TMode;
import me.cyber.model.TPlayer;
import me.cyber.utils.IdentifierUtils;
import me.cyber.utils.TSolver;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Board extends Screen {
    private final Identifier WIN_FULL_TEXTURE = IdentifierUtils.of("textures/gui/win.png");
    private final Identifier LOSE_TEXTURE = IdentifierUtils.of("textures/gui/game_over.png");
    private TMode currentGameMode;
    private final HashMap<Integer, Slot> board = new HashMap<>();
    private final int[][] buttonCords = {
            {100, 10}, {200, 10}, {300, 10},
            {100, 110}, {200, 110}, {300, 110},
            {100, 210}, {200, 210}, {300, 210}
    };
    private final int buttonHeight = 80;
    private final int buttonWidth = 80;
    private final String color1 = "fffcf2";
    private final String color2 = "ccc5b9";
    private final List<TMode> aiModes = List.of(TMode.EASY, TMode.HARD, TMode.IMPOSSIBLE);
    private Sign gameWinner;
    private TPlayer currentGameMover;


    private final int gridSize = 3;
    private final int gap = 20;
    private final int buttonSize = 80;

    // Screen parent;
    public Board(/*Screen parent*/TMode currentGameMode) {
        super(Text.literal("Board"));
        this.currentGameMode = currentGameMode;
        //  this.parent = parent;


        if(currentGameMode.equals(TMode.EASY) || currentGameMode.equals(TMode.HARD) || currentGameMode.equals(TMode.IMPOSSIBLE)){

            //The Human always always starts
            currentGameMover = TPlayer.HUMAN;
        }
    }

    @Override
    protected void init() {

        super.init();
        for (int i = 1; i <= 9; i++) {
            board.put(i, new Slot(new SignButton(buttonCords[i - 1][0], buttonCords[i - 1][1], buttonWidth, buttonHeight, color1, color2), i));
            System.out.println(i);
        }

        for (Slot button : board.values()) {
            int pos = button.getPos();
            button.getButton().onClick(e ->{

                if(TSolver.boardHasWin(board).equals(Sign.EMPTY)){
                    if(aiModes.contains(currentGameMode)){
                        if(currentGameMover.equals(TPlayer.HUMAN)){

                            if(button.getSign().equals(Sign.EMPTY)){
                                //if a player clicks the button
                                button.getButton().updateOwner(Sign.O);

                                currentGameMover = TPlayer.COMPUTER;
                            }
                            //if player made move then -> AI make move (Update the current player)

                            //once the move is made check for win.

                            //If board is full -> game done
                            Sign winner = TSolver.boardHasWin(board);

                        }

                        if(currentGameMover.equals(TPlayer.COMPUTER)){
                           // CompletableFuture.runAsync(() ->{
                                TSolver.aiTurn(board);
                                currentGameMover = TPlayer.HUMAN;
                            //}, CompletableFuture.delayedExecutor(500, TimeUnit.MILLISECONDS));
                        }
                    }
                }else{
                    gameWinner = TSolver.boardHasWin(board);
                }
            });


            this.addDrawableChild(button.getButton());


        }

    }

    int xi = -300;
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        //edede9
        int c1 = (0xFF << 24) | Integer.parseInt("edede9", 16);
        context.fill(90,5,390,300, c1);
        super.render(context, mouseX, mouseY, deltaTicks);


        //this means the player lost
//        if(gameWinner != null && gameWinner.equals(Sign.X)){
//
//
//            if(xi > 500){
//                xi = -300;
//            }
//            context.drawTexture(RenderPipelines.GUI_TEXTURED, LOSE_TEXTURE, 20, xi, 0.0F, 0.0F, 600, 300, 200, 300);
//            xi++;
//        }





    }

    @Override
    public void close() {
        board.clear();
        currentGameMover = TPlayer.NONE;
        super.close();
    }
}
