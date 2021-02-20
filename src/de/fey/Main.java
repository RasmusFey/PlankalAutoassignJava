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
        String[] input = inputFile.split("\r\n");
        setupFieldnames(input[0]);
        for(int i = 1; i < input.length; i++) {
            int nutzungseinheit = 1;
            String[] inputs = input[i].split("\t");
            for (int j = 0; j < inputs.length  ; j++) {
                assert inputs.length > 5;
                if(j == 1) {
                    nutzungseinheit = Integer.parseInt(Character.toString(inputs[j].toCharArray()[0]));
                    addToOutput(inputs[j], false);
                } else if(j == 4) {
                    addToOutput(reconstructNutzungseinheit(nutzungseinheit),false);
                } else if(j == 5)  {
                    addToOutput(raumbeschreibungToRaumtyp(inputs[2]), false);
                } else if(j == inputs.length - 1) {
                    addToOutput(inputs[j - 1], true);
                } else {
                    addToOutput(inputs[j], false);
                }
            }
        }
        System.out.println(output);
        BufferedWriter br = null;
        try {
            br = new BufferedWriter(new FileWriter("output.csv"));
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
        output += "\"" + addition;
        if(createNewRow) {
            output += "\"\r\n";
        } else {
            output += "\",";
        }
    }

    public static void setupFieldnames(String fieldnames) {
            for(int i = 0; i < fieldnames.split("\t").length; i++) {
                output += "\"" + fieldnames.split("\t")[i] + "\"";
                if(i == fieldnames.split("\t").length - 1) {
                    output += "\r\n";
                } else {
                    output += ",";
                }
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
        return String.valueOf(reconstructed);
    }

    public static String raumbeschreibungToRaumtyp(String raumbeschreibung) {
        if(raumbeschreibung.toLowerCase().contains("wc") || raumbeschreibung.toLowerCase().contains("toilette")) {
            return  "WC";
        } else if(raumbeschreibung.toLowerCase().contains("gäste") || raumbeschreibung.toLowerCase().contains("gast")) {
            return "Gästezimmer";
        } else if(raumbeschreibung.toLowerCase().contains("wohn") || raumbeschreibung.toLowerCase().contains("salon") || raumbeschreibung.toLowerCase().contains("stube")) {
            return "Wohnzimmer";
        } else if(raumbeschreibung.toLowerCase().contains("arbeit")) {
            return "Hausarbeitsraum";
        } else if(raumbeschreibung.toLowerCase().contains("keller")) {
            return "Kellerraum";
        } else if(raumbeschreibung.toLowerCase().contains("kind")) {
            return "Kinderzimmer";
        } else if(raumbeschreibung.toLowerCase().contains("küche")) {
            return "Küche";
        } else if(raumbeschreibung.toLowerCase().contains("sauna")) {
            return "Sauna";
        } else if(raumbeschreibung.toLowerCase().contains("schlaf")) {
            return "Schlafzimmer";
        } else {
            return "Unbelüfteter Raum";
        }
    }
}