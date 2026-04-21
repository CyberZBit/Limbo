package me.cyber.gui.tictactoe;

import me.cyber.Limbo;
import me.cyber.gui.components.LimboButton;
import me.cyber.model.Sign;
import me.cyber.model.Slot;
import me.cyber.model.TMode;
import me.cyber.model.TPlayer;
import me.cyber.utils.IdentifierUtils;
import me.cyber.utils.RenderUtils;
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
    private final int buttonHeight = 50;
    private final int buttonWidth = 50;
    private final String color1 = "fffcf2";
    private final String color2 = "ccc5b9";
    private final List<TMode> aiModes = List.of(TMode.EASY, TMode.HARD, TMode.IMPOSSIBLE);
    private Sign gameWinner = Sign.EMPTY;
    private TPlayer currentGameMover;


    private final int gridSize = 3;
    private final int gap = 50;
    private final int buttonSize = 30;

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
        int buttonHeight = 50;
        int buttonWidth = 50;
        int gap = 10;
        int screenWidth = this.width;
        int screenHeight = this.height;
        int rectWidth = 500;
        int rectHeight = 300;

        super.init();

        int columns = 3;
        int rows = 3;
        int totalGridWidth = (columns * buttonWidth) + ((columns - 1) * gap);
        int totalGridHeight = (rows * buttonHeight) + ((rows - 1) * gap);

        int startX = (screenWidth / 2) - (totalGridWidth / 2);
        int startY = (screenHeight / 2) - (totalGridHeight / 2);

        int slotIndex = 1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {

                int currentX = startX + (col * (buttonWidth + gap));
                int currentY = startY + (row * (buttonHeight + gap));

                board.put(slotIndex, new Slot(new SignButton(currentX, currentY, buttonWidth, buttonHeight, color1, color2), slotIndex));
                System.out.println(slotIndex);
                slotIndex++;
            }
        }

        for (Slot button : board.values()) {
            int pos = button.getPos();
            button.getButton().onClick(e ->{

                gameWinner = TSolver.boardHasWin(board);
                if(TSolver.boardHasWin(board).equals(Sign.EMPTY)){
                    if(aiModes.contains(currentGameMode)){
                        if(currentGameMover.equals(TPlayer.HUMAN)){

                            if(button.getSign().equals(Sign.EMPTY)){
                                //if a player clicks the button
                                button.getButton().updateOwner(Sign.O);

                                currentGameMover = TPlayer.COMPUTER;
                                gameWinner = TSolver.boardHasWin(board);
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
                            gameWinner = TSolver.boardHasWin(board);
                            //}, CompletableFuture.delayedExecutor(500, TimeUnit.MILLISECONDS));
                        }
                    }
                }else{

                }
            });


            this.addDrawableChild(button.getButton());

        }


        this.addDrawableChild(new LimboButton(startX+50, startY+185, 80, 25, Text.literal("Restart Game"), "#5e6b5a", 1f).onClick(e ->{
            Limbo.mc.setScreen(new Board(currentGameMode));
        }));

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);

        Text winnerAnnounce = Text.literal((gameWinner == Sign.O) ? "You Won!" :
                (gameWinner == Sign.X) ? "You Lost!" :
                        (TSolver.getEmptySpots(board).isEmpty()) && gameWinner.equals(Sign.EMPTY) ? "Draw." : "");



        int tw = Limbo.mc.textRenderer.getWidth(winnerAnnounce);
        int th = 2;

        int x1 =  (this.width / 2)-tw;
        int y1 = (this.height - th) / 2;


        if(gameWinner.equals(Sign.O) || gameWinner.equals(Sign.X) || gameWinner.equals(Sign.EMPTY) && TSolver.getEmptySpots(board).isEmpty()){
            RenderUtils.drawText(context, Limbo.mc.textRenderer, winnerAnnounce, x1, y1-120, 2,
                    gameWinner == Sign.O ? "#00ff00" : "#800000"
            );
        }





    }

    @Override
    public void close() {
        board.clear();
        currentGameMover = TPlayer.NONE;
        super.close();
    }
}
