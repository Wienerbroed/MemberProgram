package org.example.filehandler;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;




class FileHandlerTest {

    private static final String TEMP_FILE = "test_members.csv";
    private FileHandler fileHandler;

    @BeforeEach
    void setUp() throws IOException {
        fileHandler = new FileHandler(TEMP_FILE);
        Files.createFile(Paths.get(TEMP_FILE));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEMP_FILE));
    }

    @Test
    void testWriteFile() throws IOException {
        String[] data = {"Alice", "Wonderland", "28"};

        fileHandler.writeFile(data);  // Write data to TEMP_FILE

        String content = new String(Files.readAllBytes(Paths.get(TEMP_FILE)));  // Read file content
        assertTrue(content.contains("Alice,Wonderland,28"));
    }

    @org.junit.jupiter.api.Test
    void testReadFile() throws IOException {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(TEMP_FILE))) {
            writer.write("Alice,Wonderland,28\n");
            writer.write("John,Doe,25\n");
        }
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(TEMP_FILE))) {
            fileHandler.readFile();
        }
    }

}