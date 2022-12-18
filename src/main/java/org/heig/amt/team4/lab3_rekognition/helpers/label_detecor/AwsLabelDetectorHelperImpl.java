package org.heig.amt.team4.lab3_rekognition.helpers.label_detecor;

import com.amazonaws.util.IOUtils;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

public class AwsLabelDetectorHelperImpl implements ILabelDetector {
    private final RekognitionClient client;

    public AwsLabelDetectorHelperImpl(RekognitionClient rekognitionClient) {
        client = rekognitionClient;
    }

    /**
     * get the labels list of the image
     *
     * @param maxLabels     the maximum number of labels to return
     * @param minConfidence the minimum confidence for a label to be returned in percent
     * @param sourceStream  the stream of the image
     * @return the labels list
     */
    private String getLabels(int maxLabels, float minConfidence, InputStream sourceStream) {
        SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream);

        // Create an Image object for the source image.
        Image souImage = Image.builder()
                .bytes(sourceBytes)
                .build();

        DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder()
                .image(souImage)
                .maxLabels(maxLabels)
                .minConfidence(minConfidence)
                .build();

        DetectLabelsResponse labelsResponse = client.detectLabels(detectLabelsRequest);
        List<Label> labels = labelsResponse.labels();
        return labels.toString();
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
    public String analyze(String objectUri, int maxLabels, float minConfidence) {
        try {
            URL url = new URL(objectUri);
            InputStream is = url.openStream();
            String suffix = objectUri.substring(objectUri.lastIndexOf(".") + 1);
            File tempFile = File.createTempFile("tempImg", "." + suffix);
            OutputStream os = Files.newOutputStream(tempFile.toPath());

            IOUtils.copy(is, os);
            is.close();
            os.close();
            InputStream sourceStream = new FileInputStream(tempFile);
            var labels = getLabels(maxLabels, minConfidence, sourceStream);
            tempFile.delete();
            return labels;
        } catch (RekognitionException | IOException e) {
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
    public String analyze(byte[] objectBytes, int maxLabels, float minConfidence) {

        try {
            InputStream sourceStream = new ByteArrayInputStream(objectBytes);
            return getLabels(maxLabels, minConfidence, sourceStream);

        } catch (RekognitionException e) {
            System.exit(1);
        }

        return null;
    }

}
