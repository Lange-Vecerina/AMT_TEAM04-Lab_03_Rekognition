package helpers.label_detecor;


import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AwsLabelDetectorHelperImpl implements ILabelDetector {
    private final RekognitionClient client;

    public AwsLabelDetectorHelperImpl(RekognitionClient rekognitionClient) {
        client = rekognitionClient;
    }

    /**
     * Detects labels in an image.
     *
     * @param objectUri     the uri of the image
     * @param maxLabels     the maximum number of labels to return
     * @param minConfidence the minimum confidence for a label to be returned in percent
     */
    @Override
    public void analyze(String objectUri, int maxLabels, int minConfidence) {
        try {
            InputStream sourceStream = new FileInputStream(objectUri);
            SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream);

            // Create an Image object for the source image.
            Image souImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder()
                    .image(souImage)
                    .maxLabels(maxLabels)
                    .minConfidence((float) minConfidence)
                    .build();

            DetectLabelsResponse labelsResponse = client.detectLabels(detectLabelsRequest);
            List<Label> labels = labelsResponse.labels();
            System.out.println("Detected labels for the given photo");
            for (Label label : labels) {

                System.out.println(label.name() + ": " + label.confidence().toString());
            }

        } catch (RekognitionException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }

    /**
     * Detects labels in an image.
     *
     * @param objectBytes   the bytes of the image 64 encoded
     * @param maxLabels     the maximum number of labels to return
     * @param minConfidence the minimum confidence for a label to be returned in percent
     */
    @Override
    public Map<String, String> analyze(byte[] objectBytes, int maxLabels, int minConfidence) {

        try {
            InputStream sourceStream = new ByteArrayInputStream(objectBytes);
            SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream);

            // Create an Image object for the source image.
            Image souImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder()
                    .image(souImage)
                    .maxLabels(maxLabels)
                    .minConfidence((float) minConfidence)
                    .build();

            DetectLabelsResponse labelsResponse = client.detectLabels(detectLabelsRequest);
            List<Label> labels = labelsResponse.labels();
            System.out.println("Detected labels for the given photo");
            Map<String, String> result = new HashMap<>();
            for (Label label : labels) {
                result.put(label.name(), label.confidence().toString());
                System.out.println(label.name() + ": " + label.confidence().toString());
            }
            //System.out.println(labelsResponse);
            //return labels;
            return result;

        } catch (RekognitionException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return null;
    }

    /**public static void detectImageLabels(RekognitionClient rekClient, String sourceImage, int nbOfLabels, double minConfidence) {

     try {
     InputStream sourceStream = new FileInputStream(sourceImage);
     SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream);

     // Create an Image object for the source image.
     Image souImage = Image.builder()
     .bytes(sourceBytes)
     .build();

     DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder()
     .image(souImage)
     .maxLabels(nbOfLabels)
     .minConfidence((float) minConfidence)
     .build();

     DetectLabelsResponse labelsResponse = rekClient.detectLabels(detectLabelsRequest);
     List<Label> labels = labelsResponse.labels();
     System.out.println("Detected labels for the given photo");
     for (Label label : labels) {

     System.out.println(label.name() + ": " + label.confidence().toString());
     }

     } catch (RekognitionException | FileNotFoundException e) {
     System.out.println(e.getMessage());
     System.exit(1);
     }
     }**/
}
