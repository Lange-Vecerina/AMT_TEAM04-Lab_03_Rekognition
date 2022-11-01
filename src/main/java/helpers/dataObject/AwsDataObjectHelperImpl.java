package helpers.dataObject;

import client.AwsCloudClient;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class AwsDataObjectHelperImpl implements IDataObjectHelper {
    private String _bucketName;

    public AwsDataObjectHelperImpl(String bucketUrl) {
        _bucketName = bucketUrl;
    }

    @Override
    public boolean exists(String url) {
        return AwsCloudClient.getInstance().getS3Client().doesBucketExistV2(url);
    }

    @Override
    public void createObject(String url) {
        if (!exists(url)) {
            AwsCloudClient.getInstance().getS3Client().createBucket(url);
        }
    }

    @Override
    public void createObject(String url, String path) {
        createObject(url);
        // Get string content of file located at local path
        try {
            File file = new File(path);
            String name = file.getName();
            String content = Files.readString(file.toPath());
            AwsCloudClient.getInstance().getS3Client().putObject(_bucketName, name, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeObject(String url) {
        if (exists(url)) {
            AwsCloudClient.getInstance().getS3Client().deleteBucket(url);
        }
    }

    @Override
    public void downloadObject(String srcUrl, String destPath) {
        return;
    }
}
