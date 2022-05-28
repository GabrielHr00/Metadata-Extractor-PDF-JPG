package Task_3;

import com.aspose.pdf.Document;
import com.aspose.pdf.XmpValue;
import com.aspose.pdf.internal.ms.System.Collections.Generic.lk;
import org.apache.jena.rdf.model.*;

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

        // fill in the model with statements
        fillInModelStatements(namespaces, metadata, model);

        // write the statement triples as txt document
        writeRDFTriplesIntoTXT(model);

        // add analyzed by
        Statement stmt = model.createStatement(model.createResource(root), model.createProperty(namespaces.get("dc") + "analyzedBy"), model.createResource(namespaces.get("dc") + "11914741"));
        model.add(stmt);

        // write to the appropriate file
        FileOutputStream fos = new FileOutputStream(new File("src/main/resources/rdf/rdf/" + this.fileName.substring(0, this.fileName.length() - 3) + "rdf"));
        model.write(fos, "RDF/XML");
    }

    private void fillInModelStatements(Map<String, String> namespaces, Map<String, XmpValue> metadata, Model model) {
        for (var key : metadata.keySet()) {
            int index = key.indexOf(":");
            Resource resource = model.createResource(root);
            Property property = model.createProperty(namespaces.get(key.substring(0, index)) + key.substring(index + 1));
            Resource literal = model.createResource(namespaces.get(key.substring(0, index)) + metadata.get(key));

            Statement stmt = model.createStatement(resource, property, literal);
            model.add(stmt);
        }
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
        FileWriter jpegWriter = new FileWriter("src/main/resources/rdf/txt/" + this.fileName.substring(0, this.fileName.length() - 3) + "txt");
        jpegWriter.write(result.toString());
        jpegWriter.close();
    }
}
