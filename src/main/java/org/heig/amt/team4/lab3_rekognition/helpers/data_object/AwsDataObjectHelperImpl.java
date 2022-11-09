package org.heig.amt.team4.lab3_rekognition.helpers.data_object;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.RejectedExecutionException;


/**
 * This class is responsible for creating and downloading objects from AWS S3.
 * It uses the AWS SDK for Java v2.
 *
 * @author Ivan Vecerina
 * @author Yanik Lange
 * @version 1.0
 */
public class AwsDataObjectHelperImpl implements IDataObjectHelper {
    private final AmazonS3 client;

    /**
     * Constructor
     *
     * @param s3Client - the s3 org.heig.amt.team4.client
     */
    public AwsDataObjectHelperImpl(AmazonS3 s3Client) {
        client = s3Client;
    }

    /**
     * Checks if the bucket exists
     *
     * @param bucketName the name of the bucket
     * @return true if the bucket exists, false otherwise
     */
    public boolean bucketMissing(String bucketName) {
        return !client.doesBucketExistV2(bucketName);
    }

    /**
     * Checks if an object exists in a bucket
     *
     * @param bucketName the name of the bucket
     * @param objectName the name of the object
     * @return true if the object exists, false otherwise
     */
    public boolean objectExists(String bucketName, String objectName) {
        return client.doesObjectExist(bucketName, objectName);
    }

    /**
     * Creates an object in the cloud storage.
     *
     * @param objectName the url of the object
     */
    @Override
    public void create(String objectName) {// create the bucket if it doesn't exist
        if (bucketMissing(objectName)) {
            client.createBucket(objectName);
        }
    }

    /**
     * Creates an object in the cloud storage.
     *
     * @param objectUrl   the url of the destination object
     * @param srcFilePath the path of the source file
     */
    @Override
    public void create(String objectUrl, String srcFilePath) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // create the bucket if it doesn't exist
        if (bucketMissing(objectUrlParts[0])) {
            client.createBucket(objectUrlParts[0]);
        }

        // create the object in the bucket if it doesn't exist
        if (!objectExists(objectUrlParts[0], objectUrlParts[1])) {
            client.putObject(objectUrlParts[0], objectUrlParts[1], new File(srcFilePath));
        }
    }

    /**
     * Creates an object in the cloud storage.
     *
     * @param objectUrl    the url of the destination object
     * @param contentBytes the content of the object
     */
    @Override
    public void create(String objectUrl, byte[] contentBytes) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // create the bucket if it doesn't exist
        if (bucketMissing(objectUrlParts[0])) {
            client.createBucket(objectUrlParts[0]);
        }

        // create the object in the bucket if it doesn't exist
        if (!objectExists(objectUrlParts[0], objectUrlParts[1])) {
            client.putObject(objectUrlParts[0], objectUrlParts[1], new String(contentBytes));
        }
    }

    /**
     * Downloads an object from the cloud storage.
     *
     * @param objectUrl the url of the object
     * @return the content of the object
     */
    @Override
    public byte[] read(String objectUrl) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // download the object from the bucket
        if (objectExists(objectUrlParts[0], objectUrlParts[1])) {
            S3Object s3Object = client.getObject(objectUrlParts[0], objectUrlParts[1]);
            try {
                return IOUtils.toByteArray(s3Object.getObjectContent());
            } catch (IOException e) {
                throw new RejectedExecutionException("Error reading object from S3", e);
            }
        }
        return null;
    }

    /**
     * Downloads an object from the cloud storage.
     *
     * @param objectUrl      the url of the object
     * @param destinationUri the path of the destination file
     */
    @Override
    public void read(String objectUrl, String destinationUri) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // download the object from the bucket
        if (objectExists(objectUrlParts[0], objectUrlParts[1])) {
            // download the object from the bucket to the destinationUri
            S3Object s3Object = client.getObject(objectUrlParts[0], objectUrlParts[1]);
            try {
                Files.copy(s3Object.getObjectContent(), Path.of(destinationUri));
            } catch (Exception e) {
                throw new RejectedExecutionException("Error reading object from S3", e);
            }
        }
    }

    /**
     * Updates an object in the cloud storage.
     *
     * @param objectUrl   the url of the object
     * @param srcFilePath the path of the new source file
     */
    @Override
    public void update(String objectUrl, String srcFilePath) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // update the object in the bucket if it exists
        if (objectExists(objectUrlParts[0], objectUrlParts[1])) {
            client.putObject(objectUrlParts[0], objectUrlParts[1], new File(srcFilePath));
        }
    }

    /**
     * Updates an object in the cloud storage.
     *
     * @param objectUrl    the url of the object
     * @param contentBytes the new content of the object
     */
    @Override
    public void update(String objectUrl, byte[] contentBytes) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // update the object in the bucket if it exists
        if (objectExists(objectUrlParts[0], objectUrlParts[1])) {
            client.putObject(objectUrlParts[0], objectUrlParts[1], new String(contentBytes));
        }
    }

    /**
     * Deletes an object from the cloud storage.
     *
     * @param objectUrl the url of the object
     */
    @Override
    public void delete(String objectUrl) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // delete the object from the bucket if it exists
        if (objectExists(objectUrlParts[0], objectUrlParts[1])) {
            client.deleteObject(objectUrlParts[0], objectUrlParts[1]);
        }
    }

    /**
     * Creates a link to an object in the cloud storage.
     *
     * @param objectUrl the url of the object
     * @return the link to the object as a string
     */
    @Override
    public String publish(String objectUrl) {
        // split the objectUrl into root and path
        String[] objectUrlParts = objectUrl.split("/", 2);

        // create a link to the object in the bucket if it exists
        if (objectExists(objectUrlParts[0], objectUrlParts[1])) {
            return client.generatePresignedUrl(objectUrlParts[0], objectUrlParts[1], java.util.Date.from(java.time.Instant.now().plusSeconds(3600))).toString();
        }
        return null;
    }
}
