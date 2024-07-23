package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    if (board == null) {
      throw new IllegalArgumentException("Null board.");
    }
    this.board = board;
  }

  @Override
  public int getWidth() {
    return this.board[0].length;
  }

  @Override
  public int getHeight() {
    return this.board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r > getWidth() - 1 || c > getHeight() - 1 || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (board[r][c] == 0
        || board[r][c] == 1
        || board[r][c] == 2
        || board[r][c] == 3
        || board[r][c] == 4) {
      return CellType.CLUE;
    } else if (board[r][c] == 5) {
      return CellType.WALL;
    } else {
      return CellType.CORRIDOR;
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (r > getWidth() - 1 || c > getHeight() - 1 || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (!(getCellType(r, c) == CellType.CLUE)) {
      throw new IllegalArgumentException("Cell is not type CLUE");
    }
    if (board[r][c] == 0) {
      return 0;
    }
    if (board[r][c] == 1) {
      return 1;
    }
    if (board[r][c] == 2) {
      return 2;
    }
    if (board[r][c] == 3) {
      return 3;
    }
    if (board[r][c] == 4) {
      return 4;
    }
    return 0;
  }
}
