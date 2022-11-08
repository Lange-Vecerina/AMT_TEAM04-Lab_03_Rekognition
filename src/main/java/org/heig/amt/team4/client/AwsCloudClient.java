package org.heig.amt.team4.client;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.heig.amt.team4.helpers.data_object.AwsDataObjectHelperImpl;
import org.heig.amt.team4.helpers.data_object.IDataObjectHelper;
import org.heig.amt.team4.helpers.label_detecor.AwsLabelDetectorHelperImpl;
import org.heig.amt.team4.helpers.label_detecor.ILabelDetector;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;


/**
 * Singleton class for the AWS cloud org.heig.amt.team4.client
 *
 * @author Ivan Vecerina
 * @author Yanik Lange
 * @version 1.0
 */
public class AwsCloudClient implements ICloudClient {
    /* Singleton instance */
    private static AwsCloudClient instance = null;

    /* Helpers */
    private final AwsDataObjectHelperImpl dataObjectHelper;
    private final AwsLabelDetectorHelperImpl labelDetectorHelper;

    /**
     * Private constructor for the singleton class
     */
    private AwsCloudClient() {


        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(Regions.EU_WEST_2)
                .build();

        RekognitionClient rekognitionClient = RekognitionClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(System.getenv("AWS_ACCESS_KEY_ID"), System.getenv("AWS_SECRET_KEY"))))
                .region(Region.EU_WEST_2)
                .build();

        dataObjectHelper = new AwsDataObjectHelperImpl(s3Client);
        labelDetectorHelper = new AwsLabelDetectorHelperImpl(rekognitionClient);
    }

    /**
     * Returns the singleton instance of the AWS cloud org.heig.amt.team4.client
     *
     * @return the singleton instance
     */
    public static AwsCloudClient getInstance() {
        if (instance == null) {
            instance = new AwsCloudClient();
        }
        return instance;
    }

    /**
     * Returns the data object storage service
     *
     * @return the data object helper
     */
    @Override
    public IDataObjectHelper dataObjectHelper() {
        return dataObjectHelper;
    }

    /**
     * Returns the corresponding label detector service
     *
     * @return the label detector
     */
    @Override
    public ILabelDetector labelDetector() {
        return labelDetectorHelper;
    }
}
