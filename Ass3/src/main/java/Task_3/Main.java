package Task_3;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> jpegs = List.of("ca-1.jpg","ca-21.jpg", "jawa-1.jpg", "jawa-2.jpg", "jawa-3.jpg", "jawa-4.jpg", "lib1-xMedUni_Wien_Josephinum_Bibliothek-meduni_wien.jpg", "lib-1.jpg", "lib-2.jpg", "m-1.jpg", "m-2.jpg", "m-3.jpg", "m-4.jpg", "ml-1.jpg", "ml_2.jpg", "ml-3.jpg", "ml-5.jpg", "onb-1.jpg", "onb-2.jpg", "onb-3.jpg", "par-1.jpg", "vie-1.jpg", "vie-2.jpg", "vie-3.jpg");
        List<String> pdfs = List.of("Cairo.pdf", "Humans.pdf", "Java.pdf", "Jawa.pdf", "Mammals.pdf", "Marine life.pdf", "Paris.pdf", "The Austrian National Library.pdf", "The University Library of the University of Vienna.pdf", "Vienna.pdf");

        for (String pdf: pdfs) {
            PdfRdfTriples pdfRDF = new PdfRdfTriples(pdf);
            pdfRDF.constructRDFFiles();
        }
    }
}
