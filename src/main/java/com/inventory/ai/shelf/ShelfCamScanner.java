package main.java.com.inventory.ai.shelf;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ShelfCamScanner {

    private Webcam webcam;

    public ShelfCamScanner() {

        System.out.println("DEBUG: Looking for webcams...");

        List<Webcam> cams = Webcam.getWebcams();

        if (cams.isEmpty()) {
            System.out.println("ERROR: No webcams found on system!");
            webcam = null;
            return;
        }

        System.out.println("DEBUG: Webcams detected:");
        for (Webcam cam : cams) {
            System.out.println(" - " + cam.getName());
        }

        // Use first available webcam
        webcam = cams.get(0);

        System.out.println("DEBUG: Trying to open webcam: " + webcam.getName());

        try {
            webcam.open();
            System.out.println("SUCCESS: Webcam opened successfully.");
        } catch (Exception e) {
            System.out.println("ERROR OPENING WEBCAM:");
            e.printStackTrace();
            webcam = null;
        }
    }

    public String captureImage(String outputPath) {

        if (webcam == null) {
            System.out.println("ERROR: Webcam not initialized.");
            return null;
        }

        if (!webcam.isOpen()) {
            System.out.println("ERROR: Webcam is not open!");
            return null;
        }

        try {
            System.out.println("DEBUG: Capturing image...");

            File outputFile = new File(outputPath);
            outputFile.getParentFile().mkdirs();

            BufferedImage img = webcam.getImage();

            if (img == null) {
                System.out.println("ERROR: Webcam returned null image!");
                return null;
            }

            ImageIO.write(img, "jpg", outputFile);

            System.out.println("SUCCESS: Saved image to: " + outputFile.getAbsolutePath());
            return outputFile.getAbsolutePath();

        } catch (Exception ex) {
            System.out.println("ERROR WRITING IMAGE:");
            ex.printStackTrace();
            return null;
        }
    }

    public void release() {
        if (webcam != null) {
            webcam.close();
            System.out.println("DEBUG: Webcam closed.");
        }
    }
}
