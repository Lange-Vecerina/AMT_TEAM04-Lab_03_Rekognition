package helpers.dataObject;

import client.AwsCloudClient;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class AwsDataObjectHelperImpl implements IDataObjectHelper {
    private boolean bucketExists(String bucketName) {
        return AwsCloudClient.getInstance().getS3Client().doesBucketExistV2(bucketName);
    }

    private boolean objectExists(String bucketName, String objectName) {
        return AwsCloudClient.getInstance().getS3Client().doesObjectExist(bucketName, objectName);
    }

    @Override
    public void createObject(String objectUrl, String filePath) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // create the bucket if it doesn't exist
        if (!bucketExists(objectUrlParts[0])) {
            AwsCloudClient.getInstance().getS3Client().createBucket(objectUrlParts[0]);
        }

        // create the object in the bucket if it doesn't exist
        if (!objectExists(objectUrlParts[0], objectUrlParts[1])) {
            AwsCloudClient.getInstance().getS3Client().putObject(objectUrlParts[0], objectUrlParts[1], new File(filePath));
        }

    }

    @Override
    public void downloadObject(String objectUrl, String destinationUri) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // download the object from the bucket
        if (objectExists(objectUrlParts[0], objectUrlParts[1])) {
            // download the object from the bucket to the destinationUri
            S3Object s3Object = AwsCloudClient.getInstance().getS3Client().getObject(objectUrlParts[0], objectUrlParts[1]);
            try {
                Files.copy(s3Object.getObjectContent(), Path.of(destinationUri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateObject(String objectUrl, String filePath) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // update the object in the bucket if it exists
        if (AwsCloudClient.getInstance().getS3Client().doesObjectExist(objectUrlParts[0], objectUrlParts[1])) {
            AwsCloudClient.getInstance().getS3Client().putObject(objectUrlParts[0], objectUrlParts[1], new File(filePath));
        }
    }

    @Override
    public void deleteObject(String objectUrl) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // delete the object in the bucket if it exists
        if (AwsCloudClient.getInstance().getS3Client().doesObjectExist(objectUrlParts[0], objectUrlParts[1])) {
            AwsCloudClient.getInstance().getS3Client().deleteObject(objectUrlParts[0], objectUrlParts[1]);
        }
    }

    @Override
    public String generatePresignedUrl(String objectUrl) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // generate the presigned url for the object in the bucket if it exists with a 1 hour expiration
        if (AwsCloudClient.getInstance().getS3Client().doesObjectExist(objectUrlParts[0], objectUrlParts[1])) {
            return AwsCloudClient.getInstance().getS3Client().generatePresignedUrl(objectUrlParts[0], objectUrlParts[1], java.util.Date.from(java.time.Instant.now().plusSeconds(900))).toString();
        }
        return null;

    }
}
