package de.fey;

import de.tudbut.io.StreamReader;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class Main {

    public static String output = null;

    public static void main(String @NotNull [] args) {
        String inputPath = /*args[0]*/ "./input.txt";
        String inputFile = null;
        try {
            inputFile = new String(new StreamReader(new FileInputStream(inputPath)).readAllAsChars());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert inputFile != null;
        String[] input = inputFile.split("\r\n");
        for(int i = 1; i < input.length; i++) {
            System.out.println(input[i].split("\t")[1].toCharArray()[0]);
        }
    }

    public static void addToOutput(String addition, boolean newRow) {
        if(newRow) {
            output += "\n";
        } else {
            output += ",";
        }
        output += addition;
    }
}
