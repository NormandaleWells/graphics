// CreateColors
//
// This program reads the color_names.txt file and creates the
// code used to create the X11 color names in ColorRGB.java.
//
// Unfortunately, I can no longer remember where I found the
// data used in color_names.txt.  It's from the web somewhere.
// I used this particular data because it included both one-
// and multi-word names for colors like "alice blue".

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CreateColors {
    
    public static void main(String[] args) throws FileNotFoundException {

        FileInputStream colorFile = new FileInputStream("color_names.txt");
        Scanner sc = new Scanner(colorFile);
        
        while (sc.hasNextLine()) {
            
            String line = sc.nextLine();
            if (line.length() < 51) continue;
            if (line.charAt(44) != '#')
                continue;

            String name1 = line.substring(0, 23);
            String name2 = line.substring(23, 44);
            String color = line.substring(44, 51);
            name1 = name1.strip();
            name2 = name2.strip();
            color = color.strip();

            System.out.printf("        colorNames.put(\"%s\", new Color(0x%s, 0x%s, 0x%s));\n",
                name1,
                color.substring(1, 3),
                color.substring(3, 5),
                color.substring(5, 7));
        }

        sc.close();
    }
}
