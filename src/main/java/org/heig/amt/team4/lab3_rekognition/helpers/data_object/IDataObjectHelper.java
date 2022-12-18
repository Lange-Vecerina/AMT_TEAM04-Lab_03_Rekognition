package org.heig.amt.team4.lab3_rekognition.helpers.data_object;

import java.io.IOException;

/**
 * Interface for data object org.heig.amt.team4.helpers.
 *
 * @author Ivan Vecerina
 * @author Yanik Lange
 * @version 1.0
 */
public interface IDataObjectHelper {

    /**
     * Creates an object in the cloud storage.
     *
     * @param objectName the url of the object
     */
    void create(String objectName);

    /**
     * Creates an object in the cloud storage.
     *
     * @param objectUrl   the url of the destination object
     * @param srcFilePath the path of the source file
     */
    void create(String objectUrl, String srcFilePath) throws IOException;

    /**
     * Creates an object in the cloud storage.
     *
     * @param objectUrl    the url of the destination object
     * @param contentBytes the content of the object
     */
    void create(String objectUrl, byte[] contentBytes);

    /**
     * Downloads an object from the cloud storage.
     *
     * @param objectUrl the url of the object
     * @return the content of the object
     */
    byte[] read(String objectUrl);

    /**
     * Downloads an object from the cloud storage.
     *
     * @param objectUrl      the url of the object
     * @param destinationUri the path of the destination file
     */
    void read(String objectUrl, String destinationUri);

    /**
     * Updates an object in the cloud storage.
     *
     * @param objectUrl   the url of the object
     * @param srcFilePath the path of the new source file
     */
    void update(String objectUrl, String srcFilePath);

    /**
     * Updates an object in the cloud storage.
     *
     * @param objectUrl    the url of the object
     * @param contentBytes the new content of the object
     */
    void update(String objectUrl, byte[] contentBytes);

    /**
     * Deletes an object from the cloud storage.
     *
     * @param objectUrl the url of the object
     */
    void delete(String objectUrl);

    /**
     * Creates a link to an object in the cloud storage.
     *
     * @param objectUrl the url of the object
     * @return the link to the object as a string
     */
    String publish(String objectUrl);
}
