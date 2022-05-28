package Task_3;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RdfTriples {
    public static void main(String[] args) throws FileNotFoundException {
        Model model = ModelFactory.createDefaultModel();
        // use the RDFDataMgr to find the input file
        FileInputStream fis = new FileInputStream(new File("src/main/resources/pdf/Humans.xmp"));
        model.read(fis, null);

        model.write(System.out);

        StmtIterator iterator = model.listStatements();
        
        while(iterator.hasNext()) {
            Statement statement = iterator.nextStatement();
            Resource subject = statement.getSubject();
            Property predicate = statement.getPredicate();
            RDFNode literal = statement.getObject();
            System.out.println(subject.toString() + "\n");
            System.out.println(predicate.toString() + "\n");
            System.out.println(literal.toString() + "\n");
        }
    }
}
