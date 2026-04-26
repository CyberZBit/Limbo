package me.cyber.utils;

import me.cyber.model.Sign;
import me.cyber.model.Slot;
import me.cyber.model.TMode;
import me.cyber.model.TPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TSolver {

    /**
     * Returns the number of empty spots on the board.
     * @param board
     * @return int slots
     */
    public static int getRemainingSlots(HashMap<Integer, Slot> board) {
        int count = 0;

        for (Slot slot : board.values()) {
            if (slot.getButton().getSign() == Sign.EMPTY) {
                count++;
            }
        }

        return count;
    }


    /**
     * Returns a HashMap of empty spots on the board.
     * @param board
     * @return
     */
    public static HashMap<Integer, Slot> getEmptySpots(HashMap<Integer, Slot> board) {
        HashMap<Integer, Slot> emptySpots = new HashMap<>();

        for (Slot slot : board.values()) {
            if (slot.getButton().getSign() == Sign.EMPTY) {
                emptySpots.put(slot.getPos(), new Slot(slot.getButton(), slot.getPos()));
            }
        }

        return emptySpots;
    }


    public static void aiTurn(HashMap<Integer, Slot> board, TMode mode) {
        int pos;
        int[] move;

        //TODO: Actually make a real Hard mode.
        if (mode.equals(TMode.HARD) || mode.equals(TMode.IMPOSSIBLE)) {
            if (getRemainingSlots(board) == 9) {
                pos = (int) (Math.random() * 9) + 1;
            } else {
                move = minimax(board, getRemainingSlots(board), TPlayer.COMPUTER);
                pos = move[0];
            }

            if (setMove(pos, TPlayer.COMPUTER, board)) {
                board.get(pos).setSign(Sign.X);
            }
        }


        if (mode.equals(TMode.EASY)) {
            if (getRemainingSlots(board) == 9) {
                pos = (int) (Math.random() * 9) + 1;
            }

            List<Slot> emptySlots = new ArrayList<>(getEmptySpots(board).values());
            int md = (int) (Math.random() * emptySlots.size());

            pos = emptySlots.get(md).getPos();

            if (setMove(pos, TPlayer.COMPUTER, board)) {
                board.get(pos).setSign(Sign.X);
            }
        }
    }

    /**
     * Algorithm by Cledersonbc from: https://github.com/Cledersonbc/tic-tac-toe-minimax/
     *
     * @param state  - Board
     * @param depth  - Amount of moves left
     * @param player - The player (COMPUTER OR HUMAN)
     * @return move[0], score[1]
     */
    public static int[] minimax(HashMap<Integer, Slot> state, int depth, TPlayer player) {
        int[] best;

        if (player.equals(TPlayer.COMPUTER)) {
            best = new int[]{-1, -1000};
        } else {
            best = new int[]{-1, 1000};
        }

        if (depth == 0 || !boardHasWin(state)[0].equals(Sign.EMPTY) || getRemainingSlots(state) == 0) {
            return new int[]{-1, evaluate(state)};
        }

        for (Slot slot : getEmptySpots(state).values()) {
            int pos = slot.getPos();

            // Make the move
            state.get(pos).setSign(player == TPlayer.COMPUTER ? Sign.X : Sign.O);

            TPlayer nextPlayer = player == TPlayer.COMPUTER ? TPlayer.HUMAN : TPlayer.COMPUTER;
            int[] score = minimax(state, depth - 1, nextPlayer);

            // Undo the move
            state.get(pos).setSign(Sign.EMPTY);

            score[0] = pos;

            if (player.equals(TPlayer.COMPUTER)) {
                if (score[1] > best[1]) {
                    best = score;
                }
            } else {
                if (score[1] < best[1]) {
                    best = score;
                }
            }
        }

        return best;
    }

    /**
     *
     * @param board
     * @return {Sign[0], obj[0,1,2](winning pattern)}
     */
    public static Object[] boardHasWin(HashMap<Integer, Slot> board) {
        Object[] winningPattern;
        int[][] winCheckPatterns = {
                {1, 2, 3}, {4, 5, 6},
                {7, 8, 9}, {1, 4, 7},
                {2, 5, 8}, {3, 6, 9},
                {1, 5, 9}, {3, 5, 7}

        };

        for (int[] row : winCheckPatterns) {
            if (!board.get(row[0]).getSign().equals(Sign.EMPTY) && !board.get(row[1]).getSign().equals(Sign.EMPTY) && !board.get(row[2]).getSign().equals(Sign.EMPTY)) {
                if (board.get(row[0]).getSign() == board.get(row[1]).getSign() && board.get(row[1]).getSign() == board.get(row[2]).getSign()) {
                    winningPattern = new Object[]{row[0], row[1], row[2]};
                    return new Object[]{board.get(row[0]).getSign(), winningPattern};
                }
            }
        }


        return new Object[]{Sign.EMPTY, winCheckPatterns};
    }

    public static boolean setMove(int pos, TPlayer player, HashMap<Integer, Slot> board) {
        return board.containsKey(pos) && board.get(pos).getSign().equals(Sign.EMPTY);
    }

    /**
     * Evaluates the move.
     * @param board
     * @return a score
     */
    private static int evaluate(HashMap<Integer, Slot> board) {
        int score = 0;

        if (boardHasWin(board)[0].equals(Sign.X)) {
            score += 1;
        } else if (boardHasWin(board)[0].equals(Sign.O)) {
            score -= 1;
        }

        return score;
    }

}
