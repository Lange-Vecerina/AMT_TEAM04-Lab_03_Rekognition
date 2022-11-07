package client;

import helpers.data_object.IDataObjectHelper;
import helpers.label_detecor.ILabelDetector;

/**
 * Interface for the cloud client
 *
 * @author Ivan Vecerina
 * @author Yanik Lange
 * @version 1.0
 */
public interface ICloudClient {
    /**
     * Returns the data object storage service
     *
     * @return the data object helper
     */
    IDataObjectHelper dataObjectHelper();

    /**
     * Returns the corresponding label detector service
     *
     * @return the label detector
     */
    ILabelDetector labelDetector();
}
