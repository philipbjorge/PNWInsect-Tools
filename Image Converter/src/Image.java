import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
/**
 * Created with IntelliJ IDEA.
 * User: philipbjorge
 * Date: 1/11/13
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Image {
    public String name;
    public ImageIcon thumbnail;
    public String size;
    public Date lastModified;
    private String filePath;

    public Image(File f) {
        this.filePath = f.getAbsolutePath();
        this.name = f.getName();
        this.lastModified = new Date(f.lastModified());
    }

    public void update() {
        if (this.size == null || this.thumbnail == null) {
            File f = new File(this.filePath);

            if (this.size == null)
                this.size = FileUtils.byteCountToDisplaySize(f.length());

            if (this.thumbnail == null) {
                int retry = 0;
                while (retry < 3 && this.thumbnail == null) {
                    try {
                        this.thumbnail = new ImageIcon(Scalr.resize(ImageIO.read(f), 200));
                    } catch (IOException e) { this.thumbnail = null; retry++; System.err.println(e.toString()); }
                }
            }
        }
    }

    public String size() {
        return (this.size == null) ? "Loading..." : this.size;
    }

    public ImageIcon thumbnail() {
        return this.thumbnail;
    }

    public Boolean queued() {
        return true;
    }
}
