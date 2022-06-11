package Task_6;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

public class SparqlQueries {
        private Model model = ModelFactory.createDefaultModel();
        private String fileName;
        private String queryString;

        public SparqlQueries(String file, String query) {
            this.fileName = file;
            this.queryString = query;
        }

        public void executeQuery() {
            model.read("src/main/java/Task_6/rdf/" + fileName) ;
            String queryString = this.queryString;

            Query query = QueryFactory.create(queryString) ;
            QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
            try
            {
                ResultSet results = qexec.execSelect();
                for (; results.hasNext() ; ) {
                    QuerySolution soln = results.nextSolution();
                    RDFNode x = soln.get("isso");
                    System.out.println("            * " + fileName);
                }
            }
            finally {
                qexec.close();
            }
        }
}
