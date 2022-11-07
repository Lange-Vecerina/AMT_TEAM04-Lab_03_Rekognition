package helpers.label_detecor;

/**
 * Interface for label detector helpers.
 *
 * @author Ivan Vecerina
 * @author Yanik Lange
 * @version 1.0
 */
public interface ILabelDetector {

    /**
     * Detects labels in an image.
     *
     * @param objectUri     the uri of the image
     * @param maxLabels     the maximum number of labels to return
     * @param minConfidence the minimum confidence for a label to be returned in percent
     */
    void analyze(String objectUri, int maxLabels, int minConfidence);

    /**
     * Detects labels in an image.
     *
     * @param objectBytes   the bytes of the image 64 encoded
     * @param maxLabels     the maximum number of labels to return
     * @param minConfidence the minimum confidence for a label to be returned in percent
     */
    void analyze(Byte[] objectBytes, int maxLabels, int minConfidence);
}
