package org.heig.amt.team4.lab3_rekognition;

import org.apache.commons.io.FileUtils;
import org.heig.amt.team4.lab3_rekognition.client.AwsCloudClient;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String bucketPath = "amt.team04.diduno.education/test/filebis";
        String imageFromDiskPath = "src/main/resources/cars.jpg";
        AwsCloudClient client = AwsCloudClient.getInstance();

        System.out.println("=== Generating Link ===");
        String link = client.dataObjectHelper().publish(bucketPath);
        System.out.println(link);

        client.dataObjectHelper().create(bucketPath, imageFromDiskPath);
        System.out.println("=== Analyzing image ===");
        client.labelDetector().analyze(imageFromDiskPath, 1, 80);
        byte[] fileContent = FileUtils.readFileToByteArray(new File(imageFromDiskPath));

        System.out.println("=== Analyzing byte array ===");
        String response = client.labelDetector().analyze(fileContent, 1, 80);
        byte[] byteArray = response.getBytes();

        System.out.println("=== Storing result into S3 ===");
        client.dataObjectHelper().create(bucketPath, byteArray);

    }
}
