package Task_3;

import com.adobe.internal.xmp.*;
import com.adobe.internal.xmp.properties.XMPPropertyInfo;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifImageDirectory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.iptc.IptcDirectory;
import com.drew.metadata.xmp.XmpDirectory;
import org.apache.jena.rdf.model.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.adobe.internal.xmp.XMPConst.NS_EXIF;
import static com.adobe.internal.xmp.XMPConst.NS_IPTCCORE;

public class JpgRdfTriples {
    private final static String root = "http://www.example.org/jpeg/";
    private String fileName;
    private Model model;

    public JpgRdfTriples(String fileName) {
            this.fileName = fileName;
            this.model = ModelFactory.createDefaultModel();
    }

    public void constructRDFFiles() throws ImageProcessingException, XMPException, IOException {
        // get all namespaces
        Map<String, String> namespaces = new LinkedHashMap<>();
        Map<String, String> meta = new LinkedHashMap<>();

        Metadata metadata = ImageMetadataReader.readMetadata(new File("src/main/java/Assignment3Files/jpeg/" + this.fileName));

        fillInMetadataIntoMapsIptcAndExif(namespaces, meta, metadata);

        // fill in the metadata info in the map
        fillInMetadataIntoMapsXmp(namespaces, meta, metadata);

        // fill in Properties into the JENA model
        Resource res = model.createResource(root + this.fileName.replaceAll(" ", "_").substring(0, this.fileName.length() - 4) + "#");
        for (var key : meta.keySet()) {
            int index = key.indexOf(":");

            if(key.substring(0,index).equals("iptc")) {
                if(key.substring(index + 1).startsWith("Keywords")) {
                    String[] split = meta.get(key).split(";");
                    Property prop = model.createProperty(NS_IPTCCORE, key.substring(index + 1));
                    Resource node = model.createResource();

                    int i = split.length + 1;
                    for (String s : split) {
                        // empty node
                        Property keyw = model.createProperty(NS_IPTCCORE, "_" + --i);
                        node.addProperty(keyw, s);
                    }
                    res.addProperty(prop, node);
                }
                else {
                    Property iptc = model.createProperty(NS_IPTCCORE, key.substring(index + 1));
                    res.addProperty(iptc, meta.get(key));
                }
            }
            else if(key.substring(0,index).equals("exif")) {
                Property iptc = model.createProperty(NS_EXIF, key.substring(index + 1));
                res.addProperty(iptc, meta.get(key));
            }
            else {
                if(key.substring(index + 1).contains("[") && key.substring(index + 1).contains("]")) {
                    int i = key.indexOf("[");
                    // find the element position
                    int element = Integer.parseInt(key.substring(i+1, i+2));
                    String[] result = meta.get(key).split(" \\| ");
                    Property dc = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1, i));

                    if(result.length <= element-1) {
                        res.addProperty(dc, meta.get(key));
                    }
                    else {
                        res.addProperty(dc, result[element-1]);
                    }
                }
                else {
                    Property dc = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1));
                    res.addProperty(dc, meta.get(key));
                }
            }
        }

        // add the additional analyzedBy property
        res.addProperty(model.createProperty("http://purl.org/dc/elements/1.1/", "analyzedBy"), "11914741");

    }

    public void writeRDFXMLIntoRDFFormat(String s2, String triples, String s3) throws FileNotFoundException {
        FileOutputStream rdf = new FileOutputStream(s2 + this.fileName.substring(0, this.fileName.length() - 3) + triples);
        this.model.write(rdf, s3);
    }

    private void fillInMetadataIntoMapsIptcAndExif(Map<String, String> namespaces, Map<String, String> meta, Metadata metadata) {
        // obtain the iptc directory
        if(metadata.getFirstDirectoryOfType(IptcDirectory.class) != null) {
            IptcDirectory exif = metadata.getFirstDirectoryOfType(IptcDirectory.class);
            for (Tag t : exif.getTags()) {
                if(t.hasTagName()){
                    namespaces.put("iptc", "");
                    String tagN = t.getTagName().replaceAll(" ", "");
                    meta.putIfAbsent("iptc:" + tagN, t.getDescription());
                }
            }
        }

        // obtain the exif directory
        if(metadata.getFirstDirectoryOfType(ExifIFD0Directory.class) != null) {
            ExifIFD0Directory exif = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            for (Tag t : exif.getTags()) {
                if(t.hasTagName()) {
                    namespaces.put("exif", "");
                    String tagN = t.getTagName().replaceAll(" ", "");
                    meta.putIfAbsent("exif:" + tagN, t.getDescription());
                }
            }
        }

        if(metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class) != null) {
            ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            for (Tag t : exif.getTags()) {
                if(t.hasTagName()) {
                    namespaces.put("exif", "");
                    String tagN = t.getTagName().replaceAll(" ", "");
                    meta.putIfAbsent("exif:" + tagN, t.getDescription());
                }
            }
        }

        if(metadata.getFirstDirectoryOfType(ExifImageDirectory.class) != null) {
            ExifImageDirectory exif = metadata.getFirstDirectoryOfType(ExifImageDirectory.class);
            for (Tag t : exif.getTags()) {
                if(t.hasTagName()) {
                    namespaces.put("exif", "");
                    String tagN = t.getTagName().replaceAll(" ", "");
                    meta.putIfAbsent("exif:" + tagN, t.getDescription());
                }
            }
        }
    }

    private void fillInMetadataIntoMapsXmp(Map<String, String> namespaces, Map<String, String> meta, Metadata metadata) throws XMPException {
        XmpDirectory xmpDirectory = metadata.getFirstDirectoryOfType(XmpDirectory.class);
        List<String> subjects = xmpDirectory.getXmpProperties().keySet().stream().collect(Collectors.toList());
        for (String key : subjects) {
            meta.putIfAbsent(key, xmpDirectory.getXmpProperties().get(key));
        }

        XMPMeta xmpMeta = xmpDirectory.getXMPMeta();
        XMPIterator itr = xmpMeta.iterator();

        while (itr.hasNext()) {
            XMPPropertyInfo next = (XMPPropertyInfo) itr.next();
            if (next.getNamespace() != null && next.getPath() != null) {
                int index = next.getPath().indexOf(":");
                namespaces.putIfAbsent(next.getPath().substring(0, index), next.getNamespace());
            }
        }
    }
}
