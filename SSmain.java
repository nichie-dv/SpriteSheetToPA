package SpriteSheetToPA;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class SSmain {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException, ImageSizeException {
        BufferedImage image = ImageIO.read(new File("input.png"));
        PrintStream output = new PrintStream(new FileOutputStream("output.txt"), true, "UTF-8");
        int width = image.getWidth();
        int height = image.getHeight();
        int pixelstandard = 15 * (width * height);
        double defaultSpace = 11.5;
        int skipInt = 0;
        pixelstandard += 49;
        if (pixelstandard >= 16000) {
            throw new ImageSizeException("image size exceeds 16,000 characters.");
        }
        output.print("<align=\"left\"><line-height=11.5px><cspace=-0.3em>"); 
        int tp = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (image.getRGB(x, y) != 0) {
                    int RGB = image.getRGB(x, y);
                    @SuppressWarnings("unused")
                    int alpha = (RGB >> 24) & 255;
                    int red = (RGB >> 16) & 255;
                    int green = (RGB >> 8) & 255;
                    int blue = RGB & 255;
                    String hex = String.format("#%02x%02x%02x", red, green, blue).toUpperCase(); 
                    if (hex.equals("#000000")) {
                        hex = "#000";
                    }
                    if (hex.equals("#FFFFFF")) {
                        hex = "#FFF";
                    }
                    output.print("<color=" + hex + ">■"); 
                } else {
                    if (x != width && skipInt == 0) {    
                        for (int i = 1; i < width; i++) {
                            if (x + i < width) {
                                if (image.getRGB(x + i, y) != 0 || i == width - 1) {
                                    break;
                                } else {
                                    defaultSpace += 11.5;
                                    skipInt++;
                                }
                            }
                        }
                        output.print("<space=" + defaultSpace + "px>"); 
                    } else if (skipInt != 0) {
                        skipInt -= 1;
                    }
                    defaultSpace = 11.5;
                }      
            }
            tp++;
            if (tp == width) {
                tp = 0;
                if (height != width) {
                    output.print("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"); 
                }
            }
            output.print("<br>"); 
        }
        output.close();
    }
}
