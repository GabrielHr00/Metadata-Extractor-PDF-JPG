package Task_5;

import Task_3.JpgRdfTriples;
import Task_3.PdfRdfTriples;
import com.adobe.internal.xmp.XMPException;
import com.drew.imaging.ImageProcessingException;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ImageProcessingException, XMPException {
        List<String> jpegs = List.of("ca-1.jpg","ca-21.jpg", "jawa-1.jpg", "jawa-2.jpg", "jawa-3.jpg", "jawa-4.jpg", "lib1-xMedUni_Wien_Josephinum_Bibliothek-meduni_wien.jpg", "lib-1.jpg", "lib-2.jpg", "m-1.jpg", "m-2.jpg", "m-3.jpg", "m-4.jpg", "ml-1.jpg", "ml_2.jpg", "ml-3.jpg", "ml-5.jpg", "onb-1.jpg", "onb-2.jpg", "onb-3.jpg", "par-1.jpg", "vie-1.jpg", "vie-2.jpg", "vie-3.jpg");
        List<String> pdfs = List.of("Cairo.pdf", "Humans.pdf", "Java.pdf", "Jawa.pdf", "Mammals.pdf", "Marine life.pdf", "Paris.pdf", "The Austrian National Library.pdf", "The University Library of the University of Vienna.pdf", "Vienna.pdf");

        try {
            for (String pdf : pdfs) {
                PdfRdfTriples pdfRDF = new PdfRdfTriples(pdf);
                pdfRDF.constructRDFFiles();
                pdfRDF.writeRDFXMLIntoRDFFormat("src/main/java/Task_5/rdf/pdf/", "rdf", "RDF/XML");
                pdfRDF.writeRDFXMLIntoRDFFormat("src/main/java/Task_6/rdf/", "rdf", "RDF/XML");
            }
            System.out.println("\n" + "================================================================");
            System.out.println("PDF RDF files with original namespaces are successfully added!");
            System.out.println("================================================================");

            for (String jpg : jpegs) {
                JpgRdfTriples jpgRDF = new JpgRdfTriples(jpg);
                jpgRDF.constructRDFFiles();
                jpgRDF.writeRDFXMLIntoRDFFormat("src/main/java/Task_5/rdf/jpg/", "rdf", "RDF/XML");
                jpgRDF.writeRDFXMLIntoRDFFormat("src/main/java/Task_6/rdf/", "rdf", "RDF/XML");
            }

            System.out.println("\n" + "================================================================");
            System.out.println("JPEG RDF files with original namespaces are successfully added!");
            System.out.println("================================================================");

        }
        catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
