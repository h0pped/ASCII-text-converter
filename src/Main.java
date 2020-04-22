import java.awt.*;
import java.awt.dnd.DropTarget;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean is_end = false;
        String separator = "-----------------------------------------------------------------------------------------------------------";
        Scanner sc = new Scanner(System.in);

        while (!is_end) {

            try {
                System.out.println("Enter your string to draw: ");
                String str = sc.nextLine();
                System.out.println(separator);
                Drawing.drawASCII(str.toUpperCase());
                System.out.println(separator);

            } catch (Exception ex) {
                System.out.println("Error");
            }
        }

    }
}
