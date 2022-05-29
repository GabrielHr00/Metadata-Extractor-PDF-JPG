package Task_4;

import java.io.FileNotFoundException;
import java.util.List;

public class MainDataStore {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> jpegs = List.of("ca-1.ttl","ca-21.ttl", "jawa-1.ttl", "jawa-2.ttl", "jawa-3.ttl", "jawa-4.ttl", "lib1-xMedUni_Wien_Josephinum_Bibliothek-meduni_wien.ttl", "lib-1.ttl", "lib-2.ttl", "m-1.ttl", "m-2.ttl", "m-3.ttl", "m-4.ttl", "ml-1.ttl", "ml_2.ttl", "ml-3.ttl", "ml-5.ttl", "onb-1.ttl", "onb-2.ttl", "onb-3.ttl", "par-1.ttl", "vie-1.ttl", "vie-2.ttl", "vie-3.ttl");
        List<String> pdfs = List.of("Cairo.ttl", "Humans.ttl", "Java.ttl", "Jawa.ttl", "Mammals.ttl", "Marine life.ttl", "Paris.ttl", "The Austrian National Library.ttl", "The University Library of the University of Vienna.ttl", "Vienna.ttl");

        for (String pdf: pdfs) {
            RdfDataStoring storeData = new RdfDataStoring(pdf, "src/main/resources/rdf/pdf/rdf/");
            storeData.storeFileInRDFLocalDatastore();
        }

        for (String jpg: jpegs) {
            RdfDataStoring storeData = new RdfDataStoring(jpg, "src/main/resources/rdf/jpg/rdf/");
            storeData.storeFileInRDFLocalDatastore();
        }

    }
}
