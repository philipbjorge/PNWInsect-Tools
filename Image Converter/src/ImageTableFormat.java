import ca.odell.glazedlists.gui.TableFormat;

/**
 * Created with IntelliJ IDEA.
 * User: philipbjorge
 * Date: 1/11/13
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageTableFormat implements TableFormat<Image> {
    private static String[] columnNames = {"Q", "Thumbnail", "Name", "Size", "Last Modified"};

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int column) {
        if (column < 0 || column >= columnNames.length)
            throw new IllegalStateException();
        return columnNames[column];
    }

    public Object getColumnValue(Image image, int column) {
        if (column < 0 || column >= columnNames.length)
            throw new IllegalStateException();

        if (columnNames[column].equals("Q"))                return image.queued();
        else if (columnNames[column].equals("Thumbnail"))   return image.thumbnail();
        else if (columnNames[column].equals("Name"))   return image.name;
        else if (columnNames[column].equals("Size"))   return image.size();
        else if (columnNames[column].equals("Last Modified"))   return image.lastModified;

        throw new IllegalStateException();
    }
}
