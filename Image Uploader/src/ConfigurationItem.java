/**
 * Created with IntelliJ IDEA.
 * User: philipbjorge
 * Date: 1/12/13
 * Time: 4:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationItem {
    public String name;
    public String normalDir;
    public String zoomDir;

    public ConfigurationItem(String name, String normal, String zoom) {
        this.name = name;
        normalDir = normal;
        zoomDir = zoom;
    }

    public String toString() {
        return this.name;
    }
}
