package org.heig.amt.team4.lab3_rekognition.helpers.data_object;

//TODO REVIEW Refactor methods in a business way (Get Object, Download Object)
import org.heig.amt.team4.lab3_rekognition.client.AwsCloudClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;

public class AwsDataObjectHelperImplTest {
    private static String bucketPath;
    private static String folderPath;
    private static String folderPathForAssert;
    private static String carImagePath;
    private static String treeImagePath;
    private static final AwsDataObjectHelperImpl dataObjectHelper = (AwsDataObjectHelperImpl) AwsCloudClient.getInstance().dataObjectHelper();

    @BeforeAll
    public static void setUp() {
        bucketPath = "amt.team04.diduno.education";
        folderPath = "/test/";
        folderPathForAssert = "test/";
        carImagePath = "src/test/resources/cars.jpg";
        treeImagePath = "src/test/resources/trees.jpg";
    }

    @AfterAll
    public static void tearDown() {
        //TODO REVIEW Test before action to avoid -self generated- exception
        // delete images 1 to 6 from the bucket
        for (int i = 1; i <= 7; i++) {
            dataObjectHelper.delete(bucketPath + folderPath + "image" + i + ".jpg");
        }

        // delete the "image6.jpg" file in the resources folder
        File file = new File("src/test/resources/image6.jpg");
        file.delete();
    }

    // Test the creation of a data object using the "cars.jpg" image from the resources
    @Test
    void createFromPath() throws IOException {
        // Get the image from carImagePath
        File file = new File(carImagePath);

        // Check that the object does not exist
        assertFalse(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image1.jpg"));

        // Create a data object from the image
        dataObjectHelper.create(bucketPath + folderPath + "image1.jpg", file.getPath());

        // Check if the data object exists
        assertTrue(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image1.jpg"));
    }

    //TODO REVIEW BDD Annotation
    // Test the creation of a data object using the "cars.jpg" image from the resources as byte[]
    @Test
    void createFromByteArray() throws IOException {
        // Get the image from the resources
        File file = new File(carImagePath);
        byte[] image = Files.readAllBytes(file.toPath());

        // Check that the object does not exist
        assertFalse(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image2.jpg"));

        // Create a data object from the image
        dataObjectHelper.create(bucketPath + folderPath + "image2.jpg", image);

        // Check if the data object exists
        assertTrue(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image2.jpg"));

        // Check if the created object matches the original image
        assertArrayEquals(image, dataObjectHelper.read(bucketPath + folderPath + "image2.jpg"));
    }

    // Test the update of a data object using the "trees.jpg" image from the resources
    @Test
    void updateFromPath() throws IOException {
        // Get the image from the resources
        File file = new File(treeImagePath);

        // Check that the object does not exist
        assertFalse(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image3.jpg"));

        // Create a data object from the image
        dataObjectHelper.create(bucketPath + folderPath + "image3.jpg", file.getPath());

        // Check if the data object exists
        assertTrue(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image3.jpg"));

        // Get the image from the resources
        file = new File(carImagePath);

        // Update the data object from the image
        dataObjectHelper.update(bucketPath + folderPath + "image3.jpg", file.getPath());

        // Check if the data object exists
        assertTrue(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image3.jpg"));

        // Check if the created object matches the original image
        assertArrayEquals(Files.readAllBytes(file.toPath()), dataObjectHelper.read(bucketPath + folderPath + "image3.jpg"));
    }

    // Test the update of a data object using the "trees.jpg" image from the resources as byte[]
    @Test
    void updateFromByteArray() throws IOException {
        // Get the image from the resources
        File file = new File(treeImagePath);
        byte[] image = Files.readAllBytes(file.toPath());

        // Check that the object does not exist
        assertFalse(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image4.jpg"));

        // Create a data object from the image
        dataObjectHelper.create(bucketPath + folderPath + "image4.jpg", image);

        // Check if the data object exists
        assertTrue(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image4.jpg"));

        // Get the image from the resources
        file = new File(carImagePath);
        image = Files.readAllBytes(file.toPath());

        // Update the data object from the image
        dataObjectHelper.update(bucketPath + folderPath + "image4.jpg", image);

        // Check if the data object exists
        assertTrue(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image4.jpg"));

        // Check if the created object matches the original image
        assertArrayEquals(image, dataObjectHelper.read(bucketPath + folderPath + "image4.jpg"));
    }

    // Test the read of a data object without specifying a destination path
    @Test
    void read() throws IOException {
        // Get the image from the resources
        File file = new File(carImagePath);
        byte[] image = Files.readAllBytes(file.toPath());

        // Check that the object does not exist
        assertFalse(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image5.jpg"));

        // Create a data object from the image
        dataObjectHelper.create(bucketPath + folderPath + "image5.jpg", image);

        // Check if the data object exists
        assertTrue(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image5.jpg"));

        // Check if the created object matches the original image
        assertArrayEquals(image, dataObjectHelper.read(bucketPath + folderPath + "image5.jpg"));
    }

    // Test the read of a data object specifying the resource folder as a destination path
    @Test
    void readToPath() throws IOException {
        // Get the image from the resources
        File file = new File(carImagePath);
        byte[] image = Files.readAllBytes(file.toPath());

        // Check that the object does not exist
        assertFalse(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image6.jpg"));

        // Create a data object from the image
        dataObjectHelper.create(bucketPath + folderPath + "image6.jpg", image);

        // Check if the data object exists
        assertTrue(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image6.jpg"));

        // Read the data object to the resource folder
        dataObjectHelper.read(bucketPath + folderPath + "image6.jpg", "src/test/resources/image6.jpg");

        // Check if the created object matches the original image
        assertArrayEquals(image, Files.readAllBytes(new File("src/test/resources/image6.jpg").toPath()));
    }

    // Test the public access of a data object through a generated URL
    @Test
    void publicAccess() throws IOException {
        // Get the image from the resources
        File file = new File(carImagePath);
        byte[] image = Files.readAllBytes(file.toPath());

        // Check that the object does not exist
        assertFalse(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image7.jpg"));

        // Create a data object from the image
        dataObjectHelper.create(bucketPath + folderPath + "image7.jpg", image);

        // Check if the data object exists
        assertTrue(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image7.jpg"));

        // Get the public access URL
        String url = dataObjectHelper.publish(bucketPath + folderPath + "image7.jpg");

        // Check if the URL is not null
        assertNotNull(url);

        // Check if the Url is valid
        assertTrue(url.startsWith("https://"));
    }

    // Test the deletion of a data object
    @Test
    void delete() throws IOException {
        // Get the image from the resources
        File file = new File(carImagePath);
        byte[] image = Files.readAllBytes(file.toPath());

        // Check that the object does not exist
        assertFalse(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image8.jpg"));

        // Create a data object from the image
        dataObjectHelper.create(bucketPath + folderPath + "image8.jpg", image);

        // Check if the data object exists
        assertTrue(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image8.jpg"));

        // Delete the data object
        dataObjectHelper.delete(bucketPath + folderPath + "image8.jpg");

        // Check if the data object does not exist
        assertFalse(dataObjectHelper.objectExists(bucketPath, folderPathForAssert + "image8.jpg"));
    }
}
