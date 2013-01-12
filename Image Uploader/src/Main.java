import org.codehaus.jackson.map.ObjectMapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: philipbjorge
 * Date: 1/12/13
 * Time: 2:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private JComboBox siteComboBox;
    private JPanel panel1;
    private JButton zoomDirButton;
    private JButton uploadButton;
    private JTextArea logArea;
    private JButton normalDirButton;
    private JLabel normalDirLabel;
    private JLabel zoomDirLabel;
    private JButton clearButton;

    public Main() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            logArea.append("Downloading configuration files...\n");
            Map<String,Object> map = mapper.readValue(new URL("http://jsonblob.com/api/jsonBlob/50f1509ce4b086d3fd3a0d65"), Map.class);
            logArea.append("Finished downloading configuration files...\n");
            for (Map site : (ArrayList<Map>) map.get("sites")) {
                siteComboBox.addItem(new ConfigurationItem((String)site.get("site"),
                                                           (String)site.get("normal"),
                                                           (String)site.get("zoom")));
            }
        } catch (Exception e) { logArea.append("Error downloading configuration files...\n"); logArea.append(e.toString()); }

        zoomDirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File f = pickFolder();
                if (f != null)
                    zoomDirLabel.setText(f.getAbsolutePath());
            }
        });

        normalDirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File f = pickFolder();
                if (f != null)
                    normalDirLabel.setText(f.getAbsolutePath());
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                normalDirLabel.setText("");
                zoomDirLabel.setText("");
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Boolean e = false; siteComboBox.setEnabled(e); normalDirButton.setEnabled(e); zoomDirButton.setEnabled(e); clearButton.setEnabled(e); uploadButton.setEnabled(e);

                (new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            try { Thread.sleep(1000); } catch (InterruptedException e) { e = null; }
                            logArea.append("T minus " + i + " seconds...\n");
                        }
                        Boolean e = true; siteComboBox.setEnabled(e); normalDirButton.setEnabled(e); zoomDirButton.setEnabled(e); clearButton.setEnabled(e); uploadButton.setEnabled(e);
                    }
                }).start();
            }
        });
    }

    private File pickFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return (fileChooser.showDialog(null, "Select Directory") == JFileChooser.APPROVE_OPTION) ?
                    fileChooser.getCurrentDirectory() : null;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 800);
        frame.setVisible(true);
    }
}
