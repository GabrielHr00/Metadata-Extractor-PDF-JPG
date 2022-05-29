package Task_4;

import org.apache.jena.rdf.model.ModelFactory;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import java.io.*;


public class RdfDataStoring {
    private String fileName;
    private String pathName;

    public RdfDataStoring(String fileName, String pathName) {
        this.fileName = fileName;
        this.pathName = pathName;
    }

    public void storeFileInRDFLocalDatastore() throws FileNotFoundException {
        // create a local data store
        Repository db = new SailRepository(new MemoryStore());

        // Open a connection to the database
        try (RepositoryConnection conn = db.getConnection()) {
            // load file
            System.out.println("\n" + this.fileName);
            InputStream input = new FileInputStream(new File(this.pathName + this.fileName));
            Model model = Rio.parse(input, "", RDFFormat.TURTLE);

            // add the model
            conn.add(model);

            // check if the data is in the database
            try (RepositoryResult<Statement> result = conn.getStatements(null, null, null)) {
                for (Statement st : result) {
                    System.out.println(st.getSubject() + " " + st.getPredicate() + " " + st.getObject().stringValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.shutDown();
        }
    }
}
