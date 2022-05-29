package Task_3;

import com.adobe.internal.xmp.XMPException;
import com.adobe.internal.xmp.XMPIterator;
import com.adobe.internal.xmp.XMPMeta;
import com.adobe.internal.xmp.XMPSchemaRegistry;
import com.adobe.internal.xmp.properties.XMPPropertyInfo;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.iptc.IptcDirectory;
import com.drew.metadata.jpeg.JpegReader;
import com.drew.metadata.xmp.XmpDirectory;
import com.lowagie.text.Document;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class JpgRdfTriples {
    public static void main(String[] args) throws XMPException, ImageProcessingException, IOException, ImageReadException {
        Metadata metadata = ImageMetadataReader.readMetadata(new File("src/main/java/Assignment3Files/jpeg/ca-1.jpg"));

        ImageMetadata metadata1 = Imaging.getMetadata(new File("src/main/java/Assignment3Files/jpeg/jawa-4.jpg"));
        metadata1.getItems().stream().forEach(System.out::println);

        //        XmpDirectory xmpDirectory = metadata.getFirstDirectoryOfType(XmpDirectory.class);
//        List<String> subjects = xmpDirectory.getXmpProperties().keySet().stream().collect(Collectors.toList());
//        for (String key : subjects) {
//            System.out.println(key + ": " + xmpDirectory.getXmpProperties().get(key));
//        }
//        XMPMeta xmpMeta = xmpDirectory.getXMPMeta();
//        System.out.println(xmpMeta.dumpObject());
//        System.out.println(xmpMeta.getPacketHeader());
    }
}
