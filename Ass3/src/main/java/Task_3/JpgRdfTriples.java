package Task_3;

import com.adobe.internal.xmp.*;
import com.adobe.internal.xmp.impl.xpath.XMPPath;
import com.adobe.internal.xmp.properties.XMPAliasInfo;
import com.adobe.internal.xmp.properties.XMPProperty;
import com.adobe.internal.xmp.properties.XMPPropertyInfo;
import com.aspose.pdf.XmpValue;
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
import org.apache.commons.imaging.common.XmpImagingParameters;
import org.apache.jena.rdf.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JpgRdfTriples {
    private final static String root = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private String fileName;

    public JpgRdfTriples(String fileName) {
            this.fileName = fileName;
    }

    public void constructRDFFiles() throws ImageProcessingException, XMPException, IOException {
        // get all namespaces
        Map<String, String> namespaces = new LinkedHashMap<>();
        Map<String, String> meta = new LinkedHashMap<>();

        Metadata metadata = ImageMetadataReader.readMetadata(new File("src/main/java/Assignment3Files/jpeg/" + this.fileName));

        // fill in the metadata info in the map
        fillInMetadataIntoMaps(namespaces, meta, metadata);

        // create a Jena model, where to put all the values in
        Model model = ModelFactory.createDefaultModel();

        // fill in Properties into the JENA model
        Resource res = model.createResource(root + this.fileName);
        for (var key : meta.keySet()) {
            int index = key.indexOf(":");

            if(key.substring(index + 1).startsWith("subject")) {
                String[] split = meta.get(key).split("\s+\\|\s+");
                Property prop = model.createProperty(namespaces.get(key.substring(0, index)), "subject");
                // key.substring(index + 1)
                Resource node = model.createResource();
                int i = split.length + 1;
                for (String s : split) {
                    // empty node
                    Property keyw = model.createProperty(namespaces.get(key.substring(0, index)), "_" + --i);
                    node.addProperty(keyw, s);
                }
                res.addProperty(prop, node);
            }
            else if(key.substring(index + 1).contains("[") && key.substring(index + 1).contains("]")) {
                int i = key.substring(index + 1).indexOf("[");
                Property dc = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1, i));
                res.addProperty(dc, meta.get(key));
            }
            else {
                Property dc = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1));
                res.addProperty(dc, meta.get(key));
            }
        }

        // add the additional analyzedBy property
        res.addProperty(model.createProperty("http://purl.org/dc/elements/1.1/", "analyzedBy"), "11914741");

        // write triples into txt file
        writeRDFTriplesIntoTXT(model);

        // write to the appropriate file
        FileOutputStream fos = new FileOutputStream(new File("src/main/resources/rdf/jpg/rdf/" + this.fileName.substring(0, this.fileName.length() - 3) + "rdf"));
        model.write(fos, "RDF/XML");
    }

    private void fillInMetadataIntoMaps(Map<String, String> namespaces, Map<String, String> meta, Metadata metadata) throws XMPException {
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

    private void writeRDFTriplesIntoTXT(Model model) throws IOException {
        StmtIterator stmtIterator = model.listStatements();
        StringBuilder result = new StringBuilder();
        while(stmtIterator.hasNext()) {
            Statement next = stmtIterator.next();
            result.append(next.getSubject() + "\n");
            result.append(next.getPredicate() + "\n");
            result.append(next.getObject() + "\n\n");
        }
        FileWriter jpegWriter = new FileWriter("src/main/resources/rdf/jpg/triples/" + this.fileName.substring(0, this.fileName.length() - 3) + "txt");
        jpegWriter.write(result.toString());
        jpegWriter.close();
    }
}
