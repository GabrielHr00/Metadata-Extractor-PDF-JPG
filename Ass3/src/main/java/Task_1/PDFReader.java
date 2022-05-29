package Task_1;

import java.io.IOException;

import com.lowagie.text.pdf.PdfReader;


public class PDFReader {
    private String path;
    private String fileName;

    public PDFReader(String path, String fileName) {
        this.path = path;
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