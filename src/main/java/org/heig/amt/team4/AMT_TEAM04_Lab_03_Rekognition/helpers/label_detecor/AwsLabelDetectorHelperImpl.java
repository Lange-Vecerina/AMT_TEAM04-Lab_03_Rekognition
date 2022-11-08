package org.heig.amt.team4.AMT_TEAM04_Lab_03_Rekognition.helpers.label_detecor;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

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
     * @return String containing the result of the request.
     */
    @Override
    public String analyze(String objectUri, int maxLabels, int minConfidence) {
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
            return labels.toString();
        } catch (RekognitionException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    /**
     * Detects labels in an image.
     *
     * @param objectBytes   the bytes of the image 64 encoded
     * @param maxLabels     the maximum number of labels to return
     * @param minConfidence the minimum confidence for a label to be returned in percent
     * @return String containing the result of the request.
     */
    @Override
    public String analyze(byte[] objectBytes, int maxLabels, int minConfidence) {

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

            for (Label label : labels) {
                System.out.println(label.name() + ": " + label.confidence().toString());
            }

            return labels.toString();

        } catch (RekognitionException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return null;
    }

}
