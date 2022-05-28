package Task_1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPEGReader {
    private String path = "src/main/java/Assignment3Files/jpeg/";
    private String fileName;

    public JPEGReader(String fileName) {
        this.fileName = fileName;
    }

    public BufferedImage readJPEGFile() throws IOException {
        return ImageIO.read(new File(this.path + this.fileName));
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }
}
