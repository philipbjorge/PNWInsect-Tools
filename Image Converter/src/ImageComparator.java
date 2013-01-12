import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: philipbjorge
 * Date: 1/11/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageComparator implements Comparator<Image> {
    @Override
    public int compare(Image image, Image image2) {
        return image.name.compareTo(image2.name);
    }
}
