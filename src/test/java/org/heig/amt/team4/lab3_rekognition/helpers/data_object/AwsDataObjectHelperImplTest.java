package org.heig.amt.team4.lab3_rekognition.helpers.data_object;

import org.heig.amt.team4.lab3_rekognition.client.AwsCloudClient;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AwsDataObjectHelperImplTest {
    private static AwsCloudClient client;
    // before all tests set up a bucket and an object
    @BeforeAll
    public static void setUp() throws IOException {
        AwsCloudClient client = AwsCloudClient.getInstance();

        // create a bucket
        client.dataObjectHelper().create("amt.team04.diduno.education.test");

        // create an object
    }

}
