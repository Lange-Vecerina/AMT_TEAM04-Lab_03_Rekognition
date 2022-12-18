package org.heig.amt.team4.lab3_rekognition;

import org.heig.amt.team4.lab3_rekognition.client.AwsCloudClient;
import software.amazon.ion.Timestamp;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // take the first argument as the image path
        String imagePath;

        if (args.length > 0) {
            imagePath = args[0];
        } else {
            imagePath = "https://carwow-uk-wp-3.imgix.net/18015-MC20BluInfinito-scaled-e1666008987698.jpg";
        }

        // Make a string constant of current date and time
        String date = Timestamp.now().toString();
        String bucketPath = "amt.team04.diduno.education/" + date + "/";

        // Create a data object from the image byte[]
        //byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

        // Instantiate the AWS cloud client
        AwsCloudClient client = AwsCloudClient.getInstance();

        // Upload the image to the bucket
        client.dataObjectHelper().create(bucketPath + "image.txt", imagePath);

        // Generate a link to the image and print it
        String link = client.dataObjectHelper().publish(bucketPath + "image.txt");
        System.out.println("Link to the image: " + link);

        // Read the image from the bucket
        //byte[] imageBytesFromBucket = client.dataObjectHelper().read(bucketPath + "image.txt");

        // Get the labels of the image
        String labels = client.labelDetector().analyze(imagePath, 10, 60f);

        // Put the labels in a new file and upload it to the bucket
        client.dataObjectHelper().create(bucketPath + "labels.txt", labels.getBytes());

        // Generate a link to the labels and print it
        link = client.dataObjectHelper().publish(bucketPath + "labels.txt");
        System.out.println("Link to the labels: " + link);
    }
}
