package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary puzzleLibrary;
  private final List<ModelObserver> modelObserverList;
  private boolean[][] lamps;
  private int index;
  private Puzzle puzzle;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException("Library cannot be null.");
    }
    this.puzzleLibrary = library;
    this.index = 0;
    this.puzzle = library.getPuzzle(0);
    this.lamps = new boolean[puzzle.getHeight()][puzzle.getWidth()];
    this.modelObserverList = new ArrayList<>();
    this.resetPuzzle();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r > getActivePuzzle().getHeight() - 1
        || c > getActivePuzzle().getWidth() - 1
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell is not type corridor");
    }
    lamps[r][c] = true;
    notify(this);
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r > getActivePuzzle().getHeight() - 1
        || c > getActivePuzzle().getWidth() - 1
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell is not type corridor.");
    }
    lamps[r][c] = false;
    notify(this);
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r < 0 || c < 0 || r > puzzle.getHeight() - 1 || c > puzzle.getWidth() - 1) {
      throw new IndexOutOfBoundsException("Cell is out of bounds.");
    }
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell is not type CORRIDOR.");
    }
    for (int j = c; j < puzzle.getWidth(); j++) {
      if (lamps[r][j]) {
        return true;
      } else if (puzzle.getCellType(r, j) != CellType.CORRIDOR) {
        break;
      }
    }
    for (int j = c; j >= 0; j--) {
      if (lamps[r][j]) {
        return true;
      } else if (puzzle.getCellType(r, j) != CellType.CORRIDOR) {
        break;
      }
    }
    for (int i = r; i < puzzle.getHeight(); i++) {
      if (lamps[i][c]) {
        return true;
      } else if (puzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }
    for (int i = r; i >= 0; i--) {
      if (lamps[i][c]) {
        return true;
      } else if (puzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }

    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r > getActivePuzzle().getHeight() - 1
        || c > getActivePuzzle().getWidth() - 1
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell is not type Corridor.");
    }
    return lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r > getActivePuzzle().getHeight() - 1
        || c > getActivePuzzle().getWidth() - 1
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (!(lamps[r][c])) {
      throw new IllegalArgumentException("No lamp placed in this spot");
    }
    for (int i = r - 1; i >= 0; i--) {
      if (lamps[i][c]) {
        return true;
      } else if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }
    for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
      if (lamps[i][c]) {
        return true;
      } else if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }
    for (int i = c + 1; i < getActivePuzzle().getWidth(); i++) {
      if (lamps[r][i]) {
        return true;
      } else if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
    }
    for (int i = c - 1; i >= 0; i--) {
      if (lamps[r][i]) {
        return true;
      } else if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzleLibrary.getPuzzle(index);
  }

  @Override
  public int getActivePuzzleIndex() {
    return this.index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= getPuzzleLibrarySize()) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    this.index = index;
    this.puzzle = puzzleLibrary.getPuzzle(index);
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    lamps = new boolean[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];
    notify(this);
  }

  @Override
  public boolean isSolved() {
    for (int i = 0; i < puzzle.getHeight(); i++) {
      for (int j = 0; j < puzzle.getWidth(); j++) {
        if (puzzle.getCellType(i, j) == CellType.CLUE) {
          if (!isClueSatisfied(i, j)) {
            return false;
          }
        }
        if (puzzle.getCellType(i, j) == CellType.CORRIDOR) {
          if (!isLit(i, j)) {
            return false;
          }
          if (isLamp(i, j) && isLampIllegal(i, j)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public boolean isClueSatisfied(int r, int c) {
    if (r > getActivePuzzle().getHeight() - 1
        || c > getActivePuzzle().getWidth() - 1
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("Cell is not type clue.");
    }
    int clues = puzzle.getClue(r, c);
    int adjacentLamps = 0;

    if (r > 0 && lamps[r - 1][c]) {
      adjacentLamps++;
    }
    if (r < puzzle.getHeight() - 1 && lamps[r + 1][c]) {
      adjacentLamps++;
    }
    if (c > 0 && lamps[r][c - 1]) {
      adjacentLamps++;
    }
    if (c < puzzle.getWidth() - 1 && lamps[r][c + 1]) {
      adjacentLamps++;
    }
    return (adjacentLamps == clues);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    modelObserverList.add(observer);
  }

  public void removeObserver(ModelObserver observer) {
    modelObserverList.remove(observer);
  }

  private void notify(ModelImpl model) {
    for (ModelObserver o : modelObserverList) {
      o.update(model);
    }
  }
}
