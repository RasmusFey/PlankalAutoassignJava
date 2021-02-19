package de.fey;

import de.tudbut.io.StreamReader;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class Main {

    public static String output = "";

    public static void main(String @NotNull [] args) {
        String inputPath = /*args[0]*/ "./input.txt";
        String inputFile = "";
        try {
            inputFile = new String(new StreamReader(new FileInputStream(inputPath)).readAllAsChars());
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputFile = inputFile.replace(",",".");
        String[] input = inputFile.split("\r\n");
        for(int i = 1; i < input.length; i++) {
            int nutzungseinheit = 1;
            for (int j = 0; j <= input[i].split("\t").length; j++) {
                assert input[i].split("\t").length > 4;
                if(j == 1) {
                    nutzungseinheit = Integer.parseInt(Character.toString(input[i].split("\t")[j].toCharArray()[0]));
                    addToOutput(input[i].split("\t")[j], false);
                } else if(j == 4) {
                    addToOutput(reconstructNutzungseinheit(nutzungseinheit),false);
                } else if(j == input[i].split("\t").length) {
                    addToOutput(input[i].split("\t")[j -1], true);
                } else {
                    addToOutput(input[i].split("\t")[j], false);
                }
            }
        }
        //System.out.println(output);
        BufferedWriter br = null;
        try {
            br = new BufferedWriter(new FileWriter("myfile.csv"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        try {
            assert br != null;
            br.write(output);
            br.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void addToOutput(String addition, boolean createNewRow) {
        output += addition;
        if(createNewRow) {
            output += "\n";
        } else {
            output += ",";
        }
    }

    public static String reconstructNutzungseinheit(int nutzungseinheit) {
        StringBuilder reconstructed = new StringBuilder();
        if(String.valueOf(nutzungseinheit).toCharArray().length < 3) {
            for (int i = 0; i < 3 - String.valueOf(nutzungseinheit).toCharArray().length; i++) {
                reconstructed.append("0");
            }
            reconstructed.append(nutzungseinheit);
        }
        System.out.println(reconstructed);
        return "";
    }
}