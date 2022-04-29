package edu.wpi.cs3733.d22.teamY;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.util.ImageUtils;
import edu.wpi.cs3733.d22.teamY.controllers.PersonalSettings;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Camera {

  private static String imageFolder =
      "src/main/resources/edu/wpi/cs3733/d22/teamY/views/profilePics/";
  private static String fileType = ".jpg";
  static Webcam webcam;

  public void initialize() {
    try {
      webcam = Webcam.getDefault();
    } catch (Exception e) {
      System.out.println("No webcam found");
    }
  }

  public static void newPfp() throws IOException {
    webcam.open();
    Dimension frame = webcam.getViewSize();
    double x = frame.getWidth();
    double y = frame.getHeight();
    // (x + "," + y);
    BufferedImage image = webcam.getImage();
    BufferedImage image2 = image.getSubimage((int) ((x - y) / 2), 0, (int) y, (int) y);
    ImageIO.write(
        image2,
        ImageUtils.FORMAT_JPG,
        new File(imageFolder + PersonalSettings.currentEmployee.getIDNumber() + fileType));
    webcam.close();
  }

  public static BufferedImage getPfp() throws IOException {
    try {
      return ImageIO.read(
          new File(imageFolder + PersonalSettings.currentEmployee.getIDNumber() + fileType));
    } catch (IOException e) {
      return ImageIO.read(new File(imageFolder + "default" + fileType));
    }
  }
}
