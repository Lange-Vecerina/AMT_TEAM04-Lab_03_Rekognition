import helpers.dataObject.AwsDataObjectHelperImpl;

public class Main {
    public static void main(String[] args) {
        AwsDataObjectHelperImpl awsDataObjectHelper = new AwsDataObjectHelperImpl();
        String path = "amt.team04.diduno.education/test/file";

        awsDataObjectHelper.createObject(path, "docs/testfiles/Hello.txt");

        String link = awsDataObjectHelper.generatePresignedUrl(path);
        System.out.println(link);

        awsDataObjectHelper.downloadObject(path, "docs/testfiles/Hello2.txt");
    }
}
