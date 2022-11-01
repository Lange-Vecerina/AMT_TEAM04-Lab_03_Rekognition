package helpers.dataObject;

public interface IDataObjectHelper {
    boolean exists(String url);
    void createObject(String url);
    void createObject(String url, String path);
    void removeObject(String url);
    void downloadObject(String srcUrl, String destPath);
}
