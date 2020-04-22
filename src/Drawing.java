import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Drawing {
    public static void drawASCII(String str) throws IOException {
      int width = 400;
      int height = 50;
      BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); // describing image data
      Graphics g = image.getGraphics();
      g.setFont(new Font("SansSerif", Font.BOLD, 24));

      Graphics2D g2d = (Graphics2D)g;

      g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g2d.drawString(str,10,20); //draws a string inside of BufferedImage
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

}