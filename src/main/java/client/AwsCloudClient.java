package client;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import helpers.data_object.AwsDataObjectHelperImpl;
import helpers.data_object.IDataObjectHelper;
import helpers.label_detecor.AwsLabelDetectorHelperImpl;
import helpers.label_detecor.ILabelDetector;


/**
 * Singleton class for the AWS cloud client
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

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(Regions.EU_WEST_2)
                .build();

        dataObjectHelper = new AwsDataObjectHelperImpl(s3Client);
        labelDetectorHelper = new AwsLabelDetectorHelperImpl(rekognitionClient);
    }

    /**
     * Returns the singleton instance of the AWS cloud client
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
