package me.cyber.utils;

import me.cyber.model.Sign;
import me.cyber.model.Slot;
import me.cyber.model.TPlayer;

import java.util.HashMap;

public class TSolver {
    public static int getRemeningSlots(HashMap<Integer, Slot> slots) {
        int count = 0;

        for (Slot g : slots.values()) {
            if (g.getButton().getSign() == Sign.EMPTY) {
                count++;
            }
        }

        return count;
    }


    public static HashMap<Integer, Slot> getEmptySpots(HashMap<Integer, Slot> board) {
        HashMap<Integer, Slot> emptySpots = new HashMap<>();

        for (Slot slot : board.values()) {
            if (slot.getButton().getSign() == Sign.EMPTY) {
                // emptySpots.add(slot);
                emptySpots.put(slot.getPos(), new Slot(slot.getButton(), slot.getPos()));
            }
        }

        return emptySpots;
    }

    public static void aiTurn(HashMap<Integer, Slot> board) {
        int pos;
        int[] move;

        if (getRemeningSlots(board) == 9) {
            pos = (int) (Math.random() * 9) + 1;
        } else {
            move = minimax(board, getRemeningSlots(board), TPlayer.COMPUTER);
            pos = move[0];
        }

        if (setMove(pos, TPlayer.COMPUTER, board)) {
            board.get(pos).setSign(Sign.X);
        }

        //TODO: Easy mode and hard mode

    }

    /*
            Algorithm by Cledersonbc from:
            https://github.com/Cledersonbc/tic-tac-toe-minimax/
         */
    public static int[] minimax(HashMap<Integer, Slot> state, int depth, TPlayer player) {
        int[] best;

        if (player.equals(TPlayer.COMPUTER)) {
            best = new int[]{-1, -1000};
        } else {
            best = new int[]{-1, 1000};
        }

        if (depth == 0 || !boardHasWin(state).equals(Sign.EMPTY) || getRemeningSlots(state) == 0) {
            return new int[]{-1, evalute(state)};
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

    public static Sign boardHasWin(HashMap<Integer, Slot> board) {
        int[][] winCheckPatterns = {
                {1, 2, 3}, {4, 5, 6},
                {7, 8, 9}, {1, 4, 7},
                {2, 5, 8}, {3, 6, 9},
                {1, 5, 9}, {3, 5, 7}

        };

        for (int[] row : winCheckPatterns) {
            if (!board.get(row[0]).getSign().equals(Sign.EMPTY) && !board.get(row[1]).getSign().equals(Sign.EMPTY) && !board.get(row[2]).getSign().equals(Sign.EMPTY)) {
                if (board.get(row[0]).getSign() == board.get(row[1]).getSign() && board.get(row[1]).getSign() == board.get(row[2]).getSign()) {
                    return board.get(row[0]).getSign();
                }
            }
        }

        return Sign.EMPTY;
    }

    public static boolean setMove(int pos, TPlayer player, HashMap<Integer, Slot> board) {
        if (board.containsKey(pos) && board.get(pos).getSign().equals(Sign.EMPTY)) {
            return true;
        }
        return false;
    }

    private static int evalute(HashMap<Integer, Slot> board) {
        int score = 0;

        if (boardHasWin(board).equals(Sign.X)) {
            score += 1;
        } else if (boardHasWin(board).equals(Sign.O)) {
            score -= 1;
        }

        return score;
    }

}
