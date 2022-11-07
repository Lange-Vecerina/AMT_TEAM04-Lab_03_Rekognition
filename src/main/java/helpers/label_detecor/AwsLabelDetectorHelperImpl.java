package helpers.label_detecor;


import com.amazonaws.services.rekognition.AmazonRekognition;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AwsLabelDetectorHelperImpl implements ILabelDetector {
    private final AmazonRekognition client;

    public AwsLabelDetectorHelperImpl(AmazonRekognition rekognitionClient) {
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
    public void analyze(Byte[] objectBytes, int maxLabels, int minConfidence) {
        // TODO document why this method is empty
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
