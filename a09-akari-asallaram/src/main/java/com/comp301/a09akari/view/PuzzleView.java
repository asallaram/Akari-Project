package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Puzzle;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PuzzleView implements FXComponent {
  private final ControllerImpl controller;

  public PuzzleView(ControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    Puzzle puzzle = controller.getActivePuzzle();
    GridPane layout = new GridPane();
    layout.setGridLinesVisible(true);
    int cols = puzzle.getWidth();
    int rows = puzzle.getHeight();
    for (int i = 0; i < cols; i++) {
      ColumnConstraints colConst = new ColumnConstraints();
      colConst.setMaxWidth(100);
      colConst.setMinWidth(40);
      layout.getColumnConstraints().add(colConst);
    }
    for (int i = 0; i < rows; i++) {
      RowConstraints rowConst = new RowConstraints();
      rowConst.setMaxHeight(90);
      rowConst.setMinHeight(40);
      layout.getRowConstraints().add(rowConst);
    }
    for (int row = 0; row < puzzle.getHeight(); row++) {
      for (int col = 0; col < puzzle.getWidth(); col++) {
        if (puzzle.getCellType(row, col) == CellType.CLUE) {
          StackPane square = new StackPane();
          square.setStyle("-fx-background-color: red;");
          int clue = (puzzle.getClue(row, col));
          String clueS = Integer.toString(clue);
          Text title = new Text(clueS);
          title.setFill(Color.WHITE);
          square.getChildren().add(title);
          layout.add(square, col, row, 1, 1);
          layout.setAlignment(Pos.CENTER);
          if (controller.isClueSatisfied(row, col)) {
            square.setStyle("-fx-background-color: green;");
          }
        }
        if (puzzle.getCellType(row, col) == CellType.WALL) {
          StackPane square = new StackPane();
          square.setStyle("-fx-background-color: black;");
          layout.add(square, col, row, 1, 1);
        }
        if (puzzle.getCellType(row, col) == CellType.CORRIDOR) {
          StackPane stack = new StackPane();
          stack.setMaxHeight(40);
          stack.setMaxWidth(40);
          stack.setMinHeight(40);
          stack.setMinWidth(40);
          Image img = new Image("light-bulb.png");
          ImageView imgView = new ImageView(img);
          imgView.setFitWidth(20);
          imgView.setFitHeight(20);

          int r1 = row;
          int c1 = col;
          stack.setOnMouseClicked(
              (event) -> {
                controller.clickCell(r1, c1);
              });

          if (controller.isLit(row, col)) {
            stack.setStyle("-fx-background-color: yellow;");
          }
          if (controller.isLamp(row, col)) {
            if (controller.getIsIllegal(row, col)) {
              stack.getChildren().add(new Label("X"));
            } else {
              stack.getChildren().add(imgView);
            }
          }

          layout.add(stack, col, row);
        }
      }
    }
    return layout;
  }
}
