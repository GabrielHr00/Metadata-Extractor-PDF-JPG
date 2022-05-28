package Task_1;

import java.io.IOException;

import com.lowagie.text.pdf.PdfReader;


public class PDFReader {
    private String path = "src/main/java/Assignment3Files/pdf/";
    private String fileName;

    public PDFReader(String fileName) {
        this.fileName = fileName;
    }

    public PdfReader readPDF() throws IOException {
        PdfReader reader = new PdfReader(this.path + this.fileName);
        return reader;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }
}

//PDDocument doc = Loader.loadPDF(new File(pdf.getFileName()));
//PDDocumentInformation pdd = doc.getDocumentInformation();
//pdd.getMetadataKeys().stream().forEach(System.out::println);