package Task_3;

import com.aspose.pdf.Document;
import com.aspose.pdf.XmpValue;
import com.aspose.pdf.internal.ms.System.Collections.Generic.lk;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

import java.io.*;
import java.util.*;

import static com.adobe.internal.xmp.XMPConst.NS_DC;

public class PdfRdfTriples {
    private final static String root = "http://www.example.org/pdf/";
    private String fileName;
    private Model model;

    public PdfRdfTriples(String fileName) {
        this.fileName = fileName;
        this.model = ModelFactory.createDefaultModel();
    }

    public void constructRDFFiles() throws IOException {
        // load an pdf file and get the metadata afterwards
        Document pdf = new Document("src/main/java/Assignment3Files/pdf/" + this.fileName);

        // get all namespaces
        Map<String, String> namespaces = new LinkedHashMap<>();
        Map<String, XmpValue> metadata = new LinkedHashMap<>();

        // iterate over all properties and fill in 2 maps with namespaces and metadata
        fillInInformationMaps(pdf, namespaces, metadata);

        // create a resource and fill in with properties
        Resource res = model.createResource(root + this.fileName.replaceAll(" ", "_").substring(0, this.fileName.length() - 4) + "#");
        for (var key : metadata.keySet()) {
            int index = key.indexOf(":");

            // handle the empty node case
            if(key.substring(index + 1).equals("Keywords")) {
                String[] split = metadata.get(key).toString().split(";\s+");
                Property prop = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1));
                Resource node = model.createResource();
                int i = split.length + 1;
                for (String s : split) {
                    Property keyw = model.createProperty(namespaces.get(key.substring(0, index)), "_" + --i);
                    node.addProperty(keyw, s);
                }
                res.addProperty(prop, node);
            }
            else if(key.substring(index + 1).equals("title")) {
                Property prop = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1));
                Resource node = model.createResource(RDF.Alt);
                Property keyw = model.createProperty(NS_DC, "li");
                node.addProperty(keyw, metadata.get(key).toString());
                res.addProperty(prop, node);
            }
            else if(key.substring(index + 1).equals("creator")) {
                Property prop = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1));
                Resource node = model.createResource(RDF.Seq);
                Property keyw = model.createProperty(NS_DC, "li");
                node.addProperty(keyw, metadata.get(key).toString());
                res.addProperty(prop, node);
            }
            else if(key.substring(index + 1).equals("subject")) {
                Property prop = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1));
                Resource node = model.createResource(RDF.Bag);
                Property keyw = model.createProperty(NS_DC, "li");
                node.addProperty(keyw, metadata.get(key).toString());
                res.addProperty(prop, node);
            }
            else{
                Property dc = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1));
                res.addProperty(dc, metadata.get(key).toString());
            }
        }

        res.addProperty(model.createProperty(NS_DC, "analyzedBy"), "11914741");
    }

    public void writeRDFXMLIntoRDFFormat(String s2, String rdf2, String s3) throws FileNotFoundException {
        FileOutputStream rdf = new FileOutputStream(s2 + this.fileName.substring(0, this.fileName.length() - 3).replaceAll("\\s+", "-") + rdf2);
        this.model.write(rdf, s3);
    }

    private void fillInInformationMaps(Document pdf, Map<String, String> namespaces, Map<String, XmpValue> metadata) {
        lk<String> iterator = pdf.getMetadata().getKeys().iterator();
        while(iterator.hasNext()) {
            String next = iterator.next();
            int index = next.indexOf(":");
            String prefix = next.substring(0,index);
            namespaces.putIfAbsent(prefix, pdf.getMetadata().getNamespaceUriByPrefix(prefix));
            metadata.putIfAbsent(next, pdf.getMetadata().get_Item(next));
        }
    }
}
