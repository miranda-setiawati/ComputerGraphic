package glsccompgraph;

import java.io.File;
import java.io.FileInputStream;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ImageProcessor {
	public Image toGrayscale(File file) {
        try {
            Image image = new Image(new FileInputStream(file));
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            WritableImage grayscaleImage = new WritableImage(width, height);
            PixelReader pixelReader = image.getPixelReader();
            PixelWriter pixelWriter = grayscaleImage.getPixelWriter();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int argb = pixelReader.getArgb(x, y);

                    int alpha = (argb >> 24) & 0xff;
                    int red = (argb >> 16) & 0xff;
                    int green = (argb >> 8) & 0xff;
                    int blue = argb & 0xff;

                    int gray = (int) (0.3 * red + 0.59 * green + 0.11 * blue);
                    int grayscaleArgb = (alpha << 24) | (gray << 16) | (gray << 8) | gray;

                    pixelWriter.setArgb(x, y, grayscaleArgb);
                }
            }

            return grayscaleImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Image toBlur(File file) {
        // Kernel-based blur implementation (similar approach to grayscale)
    	try {
            Image image = new Image(new FileInputStream(file));
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            WritableImage blurredImage = new WritableImage(width, height);
            PixelReader pixelReader = image.getPixelReader();
            PixelWriter pixelWriter = blurredImage.getPixelWriter();

            int[][] kernel = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
            };
            int kernelSum = 9;

            for (int y = 1; y < height - 1; y++) {
                for (int x = 1; x < width - 1; x++) {
                    int red = 0, green = 0, blue = 0;

                    for (int ky = -1; ky <= 1; ky++) {
                        for (int kx = -1; kx <= 1; kx++) {
                            int argb = pixelReader.getArgb(x + kx, y + ky);
                            red += ((argb >> 16) & 0xff) * kernel[ky + 1][kx + 1];
                            green += ((argb >> 8) & 0xff) * kernel[ky + 1][kx + 1];
                            blue += (argb & 0xff) * kernel[ky + 1][kx + 1];
                        }
                    }

                    red /= kernelSum;
                    green /= kernelSum;
                    blue /= kernelSum;

                    int argb = (0xff << 24) | (red << 16) | (green << 8) | blue;
                    pixelWriter.setArgb(x, y, argb);
                }
            }

            return blurredImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Replace with actual logic
    }

}
