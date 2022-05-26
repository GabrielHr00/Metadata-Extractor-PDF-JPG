package Task_1;

import java.io.IOException;

import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;


public class PDFReader {
    private String path = "src/main/java/Assignment3Files/pdf/";
    private String fileName;

    public PDFReader(String fileName) {
        this.fileName = this.path + fileName;
    }

    public PdfReader readPDF() throws IOException {
        PdfReader reader = new PdfReader(this.fileName);
        return reader;
    }



}
