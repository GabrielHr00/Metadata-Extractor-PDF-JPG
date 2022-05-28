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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ImageProcessingException {
        // ----------------- JPEG --------------------
        List<JPEGReader> jpegs = List.of(new JPEGReader("ca-1.jpg"), new JPEGReader("ca-21.jpg"), new JPEGReader("jawa-1.jpg"), new JPEGReader("jawa-2.jpg"), new JPEGReader("jawa-3.jpg"), new JPEGReader("jawa-4.jpg"), new JPEGReader("lib1-xMedUni_Wien_Josephinum_Bibliothek-meduni_wien.jpg"), new JPEGReader("lib-1.jpg"), new JPEGReader("lib-2.jpg"), new JPEGReader("m-1.jpg"), new JPEGReader("m-2.jpg"), new JPEGReader("m-3.jpg"), new JPEGReader("m-4.jpg"), new JPEGReader("ml-1.jpg"), new JPEGReader("ml_2.jpg"), new JPEGReader("ml-3.jpg"), new JPEGReader("ml-5.jpg"), new JPEGReader("onb-1.jpg"), new JPEGReader("onb-2.jpg"), new JPEGReader("onb-3.jpg"), new JPEGReader("par-1.jpg"), new JPEGReader("vie-1.jpg"), new JPEGReader("vie-2.jpg"), new JPEGReader("vie-3.jpg"));

        for (JPEGReader jpg : jpegs) {
            System.out.println(jpg.readJPEGFile().toString() + "\n");
        }

        // ----------------- PDF --------------------
        List<PDFReader> pdfs = List.of(new PDFReader("Cairo.pdf"), new PDFReader("Humans.pdf"), new PDFReader("Java.pdf"), new PDFReader("Jawa.pdf"), new PDFReader("Mammals.pdf"), new PDFReader("Marine life.pdf"), new PDFReader("Paris.pdf"), new PDFReader("The Austrian National Library.pdf"), new PDFReader("The University Library of the University of Vienna.pdf"), new PDFReader("Vienna.pdf"));

        for (PDFReader pdf : pdfs) {
            System.out.println(String.valueOf(pdf.readPDF().getInfo()));
        }
    }
}
