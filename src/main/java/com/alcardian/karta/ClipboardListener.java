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
                            appendToCSV(dataFile ,currentClipboardText);
                        }
                        lastClipboardText = currentClipboardText;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                    writer.write("Clipboard Data\n"); // Write CSV header
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }

    private static void appendToCSV(String fileName, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(text + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateUniqueFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmm");
        String timestamp = sdf.format(new Date());
        return FILE_PREFIX + timestamp + FILE_EXTENSION;
    }
}
