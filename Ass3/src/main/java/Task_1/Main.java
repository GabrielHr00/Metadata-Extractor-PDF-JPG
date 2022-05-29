package Task_1;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifImageDirectory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.iptc.IptcDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.xmp.XmpDirectory;
import com.lowagie.text.pdf.PdfReader;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ImageProcessingException {
        // ----------------- JPEG --------------------

        // reading data
        System.out.println("Please give the relative path to the jpeg files: " + "\n");
        Scanner s = new Scanner(System.in);
        String path = s.nextLine();
        // src/main/java/Assignment3Files/jpeg/

        List<JPEGReader> jpegs = List.of(new JPEGReader(path, "ca-1.jpg"), new JPEGReader(path, "ca-21.jpg"), new JPEGReader(path, "jawa-1.jpg"), new JPEGReader(path, "jawa-2.jpg"), new JPEGReader(path, "jawa-3.jpg"), new JPEGReader(path, "jawa-4.jpg"), new JPEGReader(path, "lib1-xMedUni_Wien_Josephinum_Bibliothek-meduni_wien.jpg"), new JPEGReader(path, "lib-1.jpg"), new JPEGReader(path, "lib-2.jpg"), new JPEGReader(path, "m-1.jpg"), new JPEGReader(path, "m-2.jpg"), new JPEGReader(path, "m-3.jpg"), new JPEGReader(path, "m-4.jpg"), new JPEGReader(path, "ml-1.jpg"), new JPEGReader(path, "ml_2.jpg"), new JPEGReader(path, "ml-3.jpg"), new JPEGReader(path, "ml-5.jpg"), new JPEGReader(path, "onb-1.jpg"), new JPEGReader(path, "onb-2.jpg"), new JPEGReader(path, "onb-3.jpg"), new JPEGReader(path, "par-1.jpg"), new JPEGReader(path, "vie-1.jpg"), new JPEGReader(path, "vie-2.jpg"), new JPEGReader(path, "vie-3.jpg"));

        for (JPEGReader jpg : jpegs) {
            System.out.println(jpg.readJPEGFile().toString() + "\n");
        }

        // ----------------- PDF --------------------
        // reading data
        System.out.println("Please give the relative path to the pdf files: " + "\n");
        String pdfPath = s.nextLine();
        // src/main/java/Assignment3Files/pdf/
        List<PDFReader> pdfs = List.of(new PDFReader(pdfPath, "Cairo.pdf"), new PDFReader(pdfPath, "Humans.pdf"), new PDFReader(pdfPath, "Java.pdf"), new PDFReader(pdfPath, "Jawa.pdf"), new PDFReader(pdfPath, "Mammals.pdf"), new PDFReader(pdfPath, "Marine life.pdf"), new PDFReader(pdfPath, "Paris.pdf"), new PDFReader(pdfPath, "The Austrian National Library.pdf"), new PDFReader(pdfPath, "The University Library of the University of Vienna.pdf"), new PDFReader(pdfPath, "Vienna.pdf"));

        for (PDFReader pdf : pdfs) {
            System.out.println(String.valueOf(pdf.readPDF().getInfo()));
        }
    }
}
