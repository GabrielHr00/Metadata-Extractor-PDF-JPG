package Task_3;

import com.aspose.pdf.Document;
import com.aspose.pdf.XmpValue;
import com.aspose.pdf.internal.ms.System.Collections.Generic.lk;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

import java.io.*;
import java.util.*;

public class PdfRdfTriples {
    private final static String root = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private String fileName;

    public PdfRdfTriples(String fileName) {
        this.fileName = fileName;
    }

    public void constructRDFFiles() throws IOException {
        // load an pdf file and get the metadata afterwards
        Document pdf = new Document("src/main/java/Assignment3Files/pdf/" + this.fileName);

        // get all namespaces
        Map<String, String> namespaces = new LinkedHashMap<>();
        Map<String, XmpValue> metadata = new LinkedHashMap<>();

        // iterate over all properties and fill in 2 maps with namespaces and metadata
        fillInInformationMaps(pdf, namespaces, metadata);

        // create a Jena Model, where we store the RDF Triples
        Model model = ModelFactory.createDefaultModel();

        Resource res = model.createResource(root + this.fileName);
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
                Property keyw = model.createProperty("http://purl.org/dc/elements/1.1/", "li");
                node.addProperty(keyw, metadata.get(key).toString());
                res.addProperty(prop, node);
            }
            else if(key.substring(index + 1).equals("creator")) {
                Property prop = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1));
                Resource node = model.createResource(RDF.Seq);
                Property keyw = model.createProperty("http://purl.org/dc/elements/1.1/", "li");
                node.addProperty(keyw, metadata.get(key).toString());
                res.addProperty(prop, node);
            }
            else if(key.substring(index + 1).equals("subject")) {
                Property prop = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1));
                Resource node = model.createResource(RDF.Bag);
                Property keyw = model.createProperty("http://purl.org/dc/elements/1.1/", "li");
                node.addProperty(keyw, metadata.get(key).toString());
                res.addProperty(prop, node);
            }
            else{
                Property dc = model.createProperty(namespaces.get(key.substring(0, index)), key.substring(index + 1));
                res.addProperty(dc, metadata.get(key).toString());
            }
        }

        res.addProperty(model.createProperty("http://purl.org/dc/elements/1.1/", "analyzedBy"), "11914741");

        // write the statement triples as txt document
        writeRDFTriplesIntoTXT(model);

        // write to the appropriate file
        FileOutputStream fos = new FileOutputStream(new File("src/main/resources/rdf/pdf/rdf/" + this.fileName.substring(0, this.fileName.length() - 3) + "rdf"));
        model.write(fos, "RDF/XML");
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

    private void writeRDFTriplesIntoTXT(Model model) throws IOException {
        StmtIterator stmtIterator = model.listStatements();
        StringBuilder result = new StringBuilder();
        while(stmtIterator.hasNext()) {
            Statement next = stmtIterator.next();
            result.append(next.getSubject() + "\n");
            result.append(next.getPredicate() + "\n");
            result.append(next.getObject() + "\n\n");
        }
        FileWriter jpegWriter = new FileWriter("src/main/resources/rdf/pdf/triples/" + this.fileName.substring(0, this.fileName.length() - 3) + "txt");
        jpegWriter.write(result.toString());
        jpegWriter.close();
    }
}
