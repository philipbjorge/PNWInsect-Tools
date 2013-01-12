import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import org.apache.commons.io.FileUtils;

import javax.imageio.spi.IIORegistry;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.awt.Component;
import java.util.Iterator;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Point;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Created with IntelliJ IDEA.
 * User: philipbjorge
 * Date: 1/11/13
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private JPanel panel1;
    private JLabel normalDirButton;
    private JLabel zoomDirLabel;
    private JButton startButton;
    private JLabel descriptionLabel;
    private JButton lookInAnotherPlaceButton;
    private JTabbedPane thumbnailsPane;
    private JPanel fileExplorerPanel;
    private JPanel queuePanel;
    private JButton selectAllButton;
    private JButton deselectAllButton;
    private JFileChooser fileChooser;

    private JTable imageTable;
    private JButton addQueueButton;
    private EventTableModel<Image> imageTableModel;
    private ArrayList<Image> images;
    private static final ExecutorService EXEC = Executors.newFixedThreadPool(4);

    public Main() {
        images = new ArrayList<Image>();
        lookInAnotherPlaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File rootDirectory = new File("/");
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (jFileChooser.showDialog(null, "Select Directory") != JFileChooser.APPROVE_OPTION)
                    return;
                rootDirectory = jFileChooser.getCurrentDirectory();

                imageTable.setModel(new DefaultTableModel(new Object[]{"Q", "Thumbnail", "Name", "Size (MB)", "Last Modified"}, 0));
                EventList<Image> imageList = new BasicEventList<Image>();

                // Add the images
                Iterator<File> files = FileUtils.iterateFiles(rootDirectory, new String[]{"tif", "jpg"}, true);
                while (files.hasNext()) {
                    File f = files.next();
                    Image i = new Image(f);
                    images.add(i);
                    imageList.add(i);
                }

                SortedList<Image> imageTableData = new SortedList<Image>(imageList, new ImageComparator());
                imageTableModel = new EventTableModel<Image>(imageTableData, new ImageTableFormat());
                imageTable.setModel(imageTableModel);
                TableComparatorChooser tableSorter = TableComparatorChooser.install(imageTable, imageTableData, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

                update();
            }
        });
    }

    private void update() {
        for (int i = 0; i < images.size(); i++) {
            final Image image = images.get(i);
            EXEC.submit(new Runnable() {
                @Override
                public void run() { image.update(); }
            });
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        imageTable = new JTable() {
            @Override
            public Class<?> getColumnClass(int i) {
                // Tell the Table to render an image for the thumbnail column
                if (i == 1)
                    return Icon.class;
                return Object.class;
            }
        };
        imageTable.setAutoCreateColumnsFromModel(true);
        imageTable.setAutoCreateRowSorter(true);
        imageTable.setEnabled(true);
        imageTable.setRowHeight(200);
    }
}
