package client;


import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AwsCloudClient implements ICloudClient {
    private static AwsCloudClient instance = null;
    private static AmazonS3 s3Client = null;


    private AwsCloudClient() {
    }

    public static AwsCloudClient getInstance() {
        if (instance == null) {
            instance = new AwsCloudClient();
            s3Client = AmazonS3ClientBuilder.standard().withCredentials(new EnvironmentVariableCredentialsProvider()).build();
        }
        return instance;
    }

    public AmazonS3 getS3Client() {
        return s3Client;
    }
}
