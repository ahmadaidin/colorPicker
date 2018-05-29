/**
 * Created by Aidin Ahmad on 05/11/2016.
 */
import jdk.nashorn.internal.ir.debug.JSONWriter;
import sun.plugin2.message.Message;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import org.json.simple.*;

import javax.imageio.ImageIO;

public class ColorCounter {
    BufferedImage image;
    int width;
    int height;
    Hashtable <Color, Integer> colorCounter = new Hashtable <Color, Integer>();

    public ColorCounter(String filename) {
        try {
            File input = new File(filename);
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            int all=0;
            for(int i=0; i<height; i++){
                for(int j=0; j<width; j++){
                    Color c = new Color(image.getRGB(j, i));
                    Integer colorNum = colorCounter.getOrDefault(c, 0);
                    colorNum++;
                    colorCounter.put(c, colorNum++);
                    all++;
                }
            }
        } catch (Exception e) {}
    }

    public void printColorCount(){

        Enumeration<Color> colorEnum = colorCounter.keys();
        int count = 1;
        while (colorEnum.hasMoreElements()){
            Color c = colorEnum.nextElement();
            Integer cNum = colorCounter.get(c);
            System.out.println(count + ") RGB: " + c.getRed() +"," + c.getGreen() + "," + c.getBlue()+" = "+ cNum +" pixels");
            count++;
        }
    }

    public void createJSONFile() {
        Enumeration<Color> colorEnum = colorCounter.keys();
        JSONArray matrix = new JSONArray();
        int count = 1;
        while (colorEnum.hasMoreElements()) {
            JSONArray array = new JSONArray();
            Color c = colorEnum.nextElement();
            array.add(c.getRed());
            array.add(c.getGreen());
            array.add(c.getBlue());
            matrix.add(array);
            int cNum = colorCounter.get(c);
            System.out.println(count + ") RGB: "+c.getRGB() +" :"+ c.getRed() +"," + c.getGreen() + "," + c.getBlue()+" = "+ cNum +" pixels");
            count++;
        }
        try  {
            FileWriter file = new FileWriter("color.json");
            file.write(matrix.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            //System.out.println("\nJSON Array: " + array);
        } catch (IOException exception) { }
    }


    public static void main(String args[]) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Nama file: ");
        String filename = input.nextLine();
        ColorCounter ci = new ColorCounter(filename);
        ci.createJSONFile();
    }
}
