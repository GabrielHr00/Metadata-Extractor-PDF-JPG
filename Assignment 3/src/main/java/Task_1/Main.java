package Task_1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JPEGReader jpeg = new JPEGReader("ca-1.jpg");
        System.out.println(jpeg.readJPEGFile().toString() + "\n");

        PDFReader pdf = new PDFReader("Cairo.pdf");
        String pdfInfo = String.valueOf(pdf.readPDF().getInfo());
        System.out.println(pdfInfo);


    }
}
