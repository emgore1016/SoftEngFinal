package edu.wpi.cs3733.d22.teamY.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class Scaling {
  private static double WINDOW_DEFAULT_WIDTH;
  private static double WINDOW_DEFAULT_HEIGHT;

  private static ReadOnlyDoubleProperty currHeight = NewSceneLoading.activeWindow.heightProperty();
  private static ReadOnlyDoubleProperty currWidth = NewSceneLoading.activeWindow.widthProperty();

  public static void initialize() {
    WINDOW_DEFAULT_WIDTH = NewSceneLoading.activeWindow.getWidth();
    WINDOW_DEFAULT_HEIGHT = NewSceneLoading.activeWindow.getHeight();
    System.out.println(WINDOW_DEFAULT_WIDTH);
    System.out.println(WINDOW_DEFAULT_HEIGHT);
  }

  public static void scaleFullscreenItemAroundTopLeft(Region itemToScale) {
    double prefX = itemToScale.getLayoutX();
    double prefY = itemToScale.getLayoutY();
    double prefHeight = itemToScale.getHeight();
    double prefWidth = itemToScale.getWidth();
    double widthDiff = WINDOW_DEFAULT_WIDTH - prefWidth;
    double heightDiff = WINDOW_DEFAULT_HEIGHT - prefHeight;
    double windowCurrWidth = NewSceneLoading.activeWindow.getWidth();
    double windowCurrHeight = NewSceneLoading.activeWindow.getHeight();

    NumberBinding minScale =
        Bindings.min(
            currWidth.divide(WINDOW_DEFAULT_WIDTH), currHeight.divide(WINDOW_DEFAULT_HEIGHT));
    itemToScale.scaleXProperty().bind(minScale);
    itemToScale.scaleYProperty().bind(minScale);

    ReadOnlyDoubleProperty width = NewSceneLoading.activeWindow.widthProperty();
    ReadOnlyDoubleProperty height = NewSceneLoading.activeWindow.heightProperty();
    double widthFactor = 2f * WINDOW_DEFAULT_WIDTH / 1200f;
    double heightFactor = 2f * WINDOW_DEFAULT_HEIGHT / 800f;

    // Not perfect, but good enough for now
    itemToScale
        .layoutXProperty()
        .bind(minScale.multiply(width.divide(widthFactor)).subtract(width.divide(widthFactor)));
    itemToScale
        .layoutYProperty()
        .bind(minScale.multiply(height.divide(heightFactor)).subtract(height.divide(heightFactor)));

    System.out.println("Scaling complete.");
  }

  public static void scaleFullscreenItemAroundTopLeft(ObservableList<Node> items) {
    for (Node currItem : items) {
      try {
        scaleFullscreenItemAroundTopLeft((Region) currItem);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void scaleBackground(ImageView image, Rectangle gradient) {

    NumberBinding maxScale =
        Bindings.max(
            currWidth.divide(WINDOW_DEFAULT_WIDTH / 2),
            currHeight.divide(WINDOW_DEFAULT_HEIGHT / 2));

    image.scaleXProperty().bind(maxScale);
    image.scaleYProperty().bind(maxScale);

    /*
    image
        .layoutXProperty()
        .bind(maxScale.multiply(windowCurrWidth / 2).subtract(windowCurrWidth / 2));

    image
        .layoutYProperty()
        .bind(maxScale.multiply(windowCurrHeight / 2).subtract(windowCurrHeight / 2));
     */

    gradient.scaleXProperty().bind(maxScale);
    gradient.scaleYProperty().bind(maxScale);
    /*
    gradient
        .layoutXProperty()
        .bind(maxScale.multiply(windowCurrWidth / 2).subtract(windowCurrWidth / 2));
    gradient
        .layoutYProperty()
        .bind(maxScale.multiply(windowCurrHeight / 2).subtract(windowCurrHeight / 2));
     */
  }
}
