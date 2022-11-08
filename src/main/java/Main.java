import client.AwsCloudClient;
import helpers.data_object.AwsDataObjectHelperImpl;
import org.apache.commons.io.FileUtils;
import org.joda.time.field.FieldUtils;
import software.amazon.awssdk.services.rekognition.model.Label;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws IOException {
       /* AwsDataObjectHelperImpl awsDataObjectHelper = new AwsDataObjectHelperImpl();
        String path = "amt.team04.diduno.education/test/file";

        awsDataObjectHelper.createObject(path, "docs/testfiles/Hello.txt");

        String link = awsDataObjectHelper.generatePresignedUrl(path);
        System.out.println(link);

        awsDataObjectHelper.downloadObject(path, "docs/testfiles/Hello2.txt");*/

        AwsCloudClient client = AwsCloudClient.getInstance();

        client.labelDetector().analyze("D:\\cars.jpg", 10, 80);
        byte[] fileContent = FileUtils.readFileToByteArray(new File("D:\\cars.jpg"));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);

        System.out.println("======================");
       //List<Label> ls = client.labelDetector().analyze(fileContent, 8, 90);
        /*for(int i=0;i<ls.size();i++){
            System.out.println(ls.get(i));
        }*/
        //System.out.println(Arrays.toString(ls.toArray()));
        Map<String, String> result = client.labelDetector().analyze(fileContent, 3, 80);
        System.out.println(result);

        //client.dataObjectHelper().create();
    }
}
