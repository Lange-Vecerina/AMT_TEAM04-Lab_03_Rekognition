package helpers.dataObject;

public interface IDataObjectHelper {
    // CRUD operations
    void createObject(String objectUrl, String filePath);
    void downloadObject(String objectUrl, String destinationUri);
    void updateObject(String objectUrl, String filePath);
    void deleteObject(String objectUrl);

    // Other operations
    String generatePresignedUrl(String objectUrl);
}
