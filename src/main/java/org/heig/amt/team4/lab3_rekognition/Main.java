package org.heig.amt.team4.lab3_rekognition;

import org.heig.amt.team4.lab3_rekognition.client.AwsCloudClient;
import software.amazon.ion.Timestamp;

public class Main {
    public static void main(String[] args) {
        // take the first argument as the image path
        String imagePath;

        if (args.length > 0) {
            imagePath = args[0];
        } else {
            imagePath = "src/main/resources/cars.jpg";
        }

        // Make a string constant of current date and time
        String date = Timestamp.now().toString();
        String bucketPath = "amt.team04.diduno.education/" + date + "/";

        // Instantiate the AWS cloud client
        AwsCloudClient client = AwsCloudClient.getInstance();

        // Upload the image to the bucket
        client.dataObjectHelper().create(bucketPath + "image.jpg", imagePath);

        // Generate a link to the image and print it
        String link = client.dataObjectHelper().publish(bucketPath + "image.jpg");
        System.out.println("Link to the image: " + link);

        // Get the labels of the image
        String labels = client.labelDetector().analyze(imagePath, 10, 60f);

        // Put the labels in a new file and upload it to the bucket
        client.dataObjectHelper().create(bucketPath + "labels.txt", labels.getBytes());

        // Generate a link to the labels and print it
        link = client.dataObjectHelper().publish(bucketPath + "labels.txt");
        System.out.println("Link to the labels: " + link);
    }
}
