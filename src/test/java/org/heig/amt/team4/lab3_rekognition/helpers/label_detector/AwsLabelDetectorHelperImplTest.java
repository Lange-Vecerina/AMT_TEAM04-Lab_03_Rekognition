package org.heig.amt.team4.lab3_rekognition.helpers.label_detector;

import org.heig.amt.team4.lab3_rekognition.client.AwsCloudClient;
import org.heig.amt.team4.lab3_rekognition.helpers.label_detecor.AwsLabelDetectorHelperImpl;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AwsLabelDetectorHelperImplTest {
    private static final AwsLabelDetectorHelperImpl labelDetectorHelper = (AwsLabelDetectorHelperImpl) AwsCloudClient.getInstance().labelDetector();

    // Test the analysis method of the label detector helper using the "cars.jpg" as path
    @Test
    void detectLabels() throws IOException {
        // Get the image from the resources
        File file = new File("src/test/resources/cars.jpg");

        // Check if the image contains the "car" label
        String result = labelDetectorHelper.analyze(file.getPath(), 4, 50f);
        assertTrue(result.contains("Car"));
    }

    // Test the analysis method of the label detector helper using the "cars.jpg" as byte[]
    @Test
    void detectLabelsFromByteArray() throws IOException {
        // Get the image from the resources
        File file = new File("src/test/resources/cars.jpg");

        // Check if the image contains the "car" label
        String result = labelDetectorHelper.analyze(Files.readAllBytes(file.toPath()), 4, 50f);
        assertTrue(result.contains("Car"));
    }

}
