package org.example.filehandler;

import java.io.*;

public class FileHandler {
    private String filePath;
    BufferedWriter writer = null;
    BufferedReader reader = null;
    String line = "";

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public void writeFile(String[] data){
        try{
            writer = new BufferedWriter(new FileWriter(filePath, true));
            for(String index : data) {
                writer.write(index + ",");
            }
            writer.newLine();

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(writer != null) writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void readFile() {

        try {
            reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                for (String index : data) {
                    System.out.printf("%-10s", index);
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
