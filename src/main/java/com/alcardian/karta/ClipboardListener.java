package com.alcardian.karta;

import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ClipboardListener {
    private static final String FILE_PREFIX = "data_";
    private static final String FILE_EXTENSION = ".csv";
    private static final String FILE_HEADER_ROW = "Pantheon,X,Z,Y\n";   // First row of every new csv file.
    private static final String LOG_FILE_NAME = "karta.log";

    public static void main(String[] args) {
        // Generate a unique file name for this run
        String dataFile = generateUniqueFileName();

        // Ensure the file exists
        ensureFileExists(dataFile);

        // Set up a Timer to periodically check the clipboard
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private String lastClipboardText = "";

            @Override
            public void run() {
                try {
                    String currentClipboardText = getClipboardText();
                    if (currentClipboardText != null && !currentClipboardText.equals(lastClipboardText)) {
                        if (currentClipboardText.contains("/jumploc")){
                            // Only append valid data to file, otherwise just update lastClipboardText
                            appendToCSV(dataFile ,convertSpacesToCommas(currentClipboardText));
                        }
                        lastClipboardText = currentClipboardText;
                    }
                } catch (Exception e) {
                    logError(e);
                }
            }
        }, 0, 1000); // Check every second
    }

    private static void ensureFileExists(String fileName) {
        try {
            Path path = Paths.get(fileName);
            if (Files.notExists(path)) {
                Files.createFile(path);
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    writer.write(FILE_HEADER_ROW); // Write CSV header
                }
            }
        } catch (IOException e) {
            logError(e);
        }
    }

    private static String getClipboardText() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String) contents.getTransferData(DataFlavor.stringFlavor);
            }
        } catch (UnsupportedFlavorException | IOException e) {
            logError(e);
        }
        return null;
    }

    public static String convertSpacesToCommas(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return as is if input is null or empty
        }
        // Replace spaces with commas
        return input.replace(" ", ",");
    }

    private static void appendToCSV(String fileName, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(text + "\n");
        } catch (IOException e) {
            logError(e);
        }
    }

    private static void logError(Exception e) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_NAME, true))) {
            writer.write(new Date() + ": " + e.getMessage());
            for (StackTraceElement element : e.getStackTrace()) {
                writer.write("\n\t" + element.toString());
            }
            writer.write("\n");
        } catch (IOException ioException) {
            ioException.printStackTrace(); // In case logging to file fails
        }
    }

    private static String generateUniqueFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmm");
        String timestamp = sdf.format(new Date());
        return FILE_PREFIX + timestamp + FILE_EXTENSION;
    }
}
