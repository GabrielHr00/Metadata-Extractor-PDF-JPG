package Task_6;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SparqlQueries {
        private Model model = ModelFactory.createDefaultModel();
        private String fileName;
        private String queryString;

        public SparqlQueries(String file, String query) {
            this.fileName = file;
            this.queryString = query;
        }

        public void executeQuery() {
            model.read("src/main/java/Task_6/rdf/" + fileName);
            String queryString = this.queryString;

            Query query = QueryFactory.create(queryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            try
            {
                ResultSet results = qexec.execSelect();
                for (; results.hasNext(); ) {
                    QuerySolution soln = results.nextSolution();
                    RDFNode x = soln.get("isso");
                    System.out.println("            * " + fileName);
                }
            }
            finally {
                qexec.close();
            }
        }

    public Set<String> executeQueryIntern() {
        model.read("src/main/java/Task_6/rdf/" + fileName);
        String queryString = this.queryString;
        Set<String> relations = new HashSet<>();

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try
        {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.nextSolution();
                RDFNode x = soln.get("result");
                relations.add(x.toString());
            }
        }
        finally {
            qexec.close();
        }
        return relations;
    }

    // all which HAVE RELATION to other media objects
    public boolean executeQuery1(String s) {
        Set<String> intern = executeQueryIntern();
        Set<String> relations = new HashSet<>();

        Model model1 = ModelFactory.createDefaultModel();
        model1.read("src/main/java/Task_6/rdf/" + s);

        String queryString = "SELECT  ?result\n" + "WHERE { ?x ?y ?result FILTER (isBlank(?x))}";

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model1);
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.nextSolution();
                RDFNode x = soln.get("result");
                relations.add(x.toString());
            }
        } finally {
            qexec.close();
        }

        // check for relationship to neighbor media object
        for (var relation : intern) {
            if(relations.contains(relation)) {
                System.out.println("            * " + fileName);
                return true;
            }
        }
        return false;
    }

    // all which DO NOT HAVE relation to other objects
    public boolean executeQuery2(String s) {
        Set<String> intern = executeQueryIntern();
        Set<String> relations = new HashSet<>();

        Model model1 = ModelFactory.createDefaultModel();
        model1.read("src/main/java/Task_6/rdf/" + s);

        String queryString = "SELECT  ?result\n" + "WHERE { ?x ?y ?result FILTER (isBlank(?x))}";

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model1);
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.nextSolution();
                RDFNode x = soln.get("result");
                relations.add(x.toString());
            }
        } finally {
            qexec.close();
        }

        // check for relationship to neighbor media object
        for (var relation : intern) {
            if(relations.contains(relation)) {
                return false;
            }
        }
        return true;
    }

    // all which HAVE RELATION to other media objects of type PDF
    public boolean executeQuery3(String s, String folder) {
        Set<String> intern = executeQueryIntern();
        Set<String> relations = new HashSet<>();

        Model model1 = ModelFactory.createDefaultModel();
        if(folder.equals("pdf")) {
            model1.read("src/main/java/Task_5/rdf/pdf/" + s);
        } else {
            model1.read("src/main/java/Task_5/rdf/jpg/" + s);
        }

        String queryString = "SELECT  ?result\n" + "WHERE { ?x ?y ?result FILTER (isBlank(?x))}";

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model1);
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.nextSolution();
                RDFNode x = soln.get("result");
                relations.add(x.toString());
            }
        } finally {
            qexec.close();
        }

        // check for relationship to neighbor media object
        for (var relation : intern) {
            if(relations.contains(relation)) {
                System.out.println("            * " + fileName);
                return true;
            }
        }
        return false;
    }

    // all which DO NOT HAVE relation to other objects
    public boolean executeQuery4(String s, String folder) {
        Set<String> intern = executeQueryIntern();
        Set<String> relations = new HashSet<>();

        Model model1 = ModelFactory.createDefaultModel();
        if(folder.equals("pdf")) {
            model1.read("src/main/java/Task_5/rdf/pdf/" + s);
        } else {
            model1.read("src/main/java/Task_5/rdf/jpg/" + s);
        }

        String queryString = "SELECT  ?result\n" + "WHERE { ?x ?y ?result FILTER (isBlank(?x))}";

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model1);
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.nextSolution();
                RDFNode x = soln.get("result");
                relations.add(x.toString());
            }
        } finally {
            qexec.close();
        }

        // check for relationship to neighbor media object
        for (var relation : intern) {
            if(relations.contains(relation)) {
                return false;
            }
        }
        return true;
    }
}
