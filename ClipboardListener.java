import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.io.*;
import java.nio.file.*;
import java.util.Timer;
import java.util.TimerTask;

public class ClipboardListener {
    private static final String FILE_NAME = "data.csv";

    public static void main(String[] args) {
        // Ensure the file exists
        ensureFileExists(FILE_NAME);

        // Set up a Timer to periodically check the clipboard
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private String lastClipboardText = "";

            @Override
            public void run() {
                try {
                    String currentClipboardText = getClipboardText();
                    if (currentClipboardText != null && !currentClipboardText.equals(lastClipboardText)) {
                        appendToCSV(currentClipboardText);
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

    private static void appendToCSV(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(text + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
