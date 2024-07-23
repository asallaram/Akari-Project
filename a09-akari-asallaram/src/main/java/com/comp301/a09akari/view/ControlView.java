package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlView implements FXComponent {
  private final ControllerImpl controller;

  public ControlView(ControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    layout.setAlignment(Pos.TOP_CENTER);
    Button randomize = new Button("Random");
    randomize.setOnAction(
        (javafx.event.ActionEvent event) -> {
          controller.clickRandPuzzle();
        });
    layout.getChildren().add(randomize);

    Button previous = new Button("Previous");
    previous.setOnAction(
        (javafx.event.ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });
    layout.getChildren().add(previous);

    Button next = new Button("Next");
    next.setOnAction(
        (javafx.event.ActionEvent event) -> {
          controller.clickNextPuzzle();
        });
    layout.getChildren().add(next);

    Button reset = new Button("Reset");
    reset.setOnAction(
        (javafx.event.ActionEvent event) -> {
          controller.clickResetPuzzle();
        });
    layout.getChildren().add(reset);
    layout.setSpacing(10.0);
    layout.setPadding(new Insets(20, 20, 20, 20));
    return layout;
  }
}
