import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Drawing {

    public static void drawASCIIString(String str) throws IOException {
        int width = 400;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // describing image data
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.BOLD, 24));

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.drawString(str, 10, 20); //draws a string inside of BufferedImage
        File file = new File("ascii.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        BufferedWriter bwriter = new BufferedWriter(writer);
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {
                sb.append(image.getRGB(x, y) == -16777216 ? "." : "$");
            }
            if (sb.toString().trim().isEmpty()) {
                continue;
            }
            bwriter.write(sb.toString());
            bwriter.newLine();

            System.out.println(sb);
        }
        bwriter.close();
    }

    public static boolean drawImage(String path) throws IOException {
        String p = ImageToGrayScale(path);
        File img = new File(path);
        BufferedImage image = ImageIO.read(img);
        Graphics2D g = (Graphics2D)image.getGraphics();

        //FLIP IMAGE
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);

        //get image as [][] of rgbs
        int[][] arr = new int[image.getWidth()][image.getHeight()];
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[i].length; j++)
                arr[i][j] = image.getRGB(i, j);
        File file = new File("ascii.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        BufferedWriter bwriter = new BufferedWriter(writer);
        for (int i = 0; i < arr.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < arr[i].length; j++) {
                Color pixelColor = new Color(image.getRGB(i,j));
                double gValue = (double) pixelColor.getRed() * 0.2989 + (double) pixelColor.getBlue() * 0.5870 + (double) pixelColor.getGreen() * 0.1140;
                final char s = gValue>0?returnStrPos(gValue):returnStrNeg(gValue);
                sb.append(s);
            }
            if (sb.toString().trim().isEmpty()) {
                continue;
            }
            bwriter.write(sb.toString());
            bwriter.newLine();
        }
        bwriter.close();
        return true;
    }

    public static String ImageToGrayScale(String path) throws IOException {
        File img = new File(path);
        if (img.exists()) {
            BufferedImage image = ImageIO.read(img);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = result.createGraphics();
            graphic.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            for (int i = 0; i < result.getHeight(); i++) {
                for (int j = 0; j < result.getWidth(); j++) {
                    Color c = new Color(result.getRGB(j, i));
                    int red = (int) (c.getRed() * 0.299);
                    int green = (int) (c.getGreen() * 0.587);
                    int blue = (int) (c.getBlue() * 0.114);
                    Color newColor = new Color(
                            red + green + blue,
                            red + green + blue,
                            red + green + blue);
                    result.setRGB(j, i, newColor.getRGB());
                }
            }
            File output = new File(path.substring(0, path.indexOf('.')) + "-grayscale.png");
            ImageIO.write(result, "png", output);
            return path.substring(0, path.indexOf('.')) + "-grayscale.png";
        }
        return null;
    }


    private static char returnStrPos(double g)//takes the grayscale value as parameter
    {
        final char str;

        if (g >= 230.0) {
            str = ' ';
        } else if (g >= 200.0) {
            str = '.';
        } else if (g >= 180.0) {
            str = '*';
        } else if (g >= 160.0) {
            str = ':';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = '&';
        } else if (g >= 70.0) {
            str = '8';
        } else if (g >= 50.0) {
            str = '#';
        } else {
            str = '@';
        }
        return str;

    }

    private static char returnStrNeg(double g) {
        final char str;

        if (g >= 230.0) {
            str = '@';
        } else if (g >= 200.0) {
            str = '#';
        } else if (g >= 180.0) {
            str = '8';
        } else if (g >= 160.0) {
            str = '&';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = ':';
        } else if (g >= 70.0) {
            str = '*';
        } else if (g >= 50.0) {
            str = '.';
        } else {
            str = ' ';
        }
        return str;

    }


}