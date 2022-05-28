package Task_2;

import Task_1.JPEGReader;
import Task_1.PDFReader;
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

public class MetadataExtractor {
    public static void main(String[] args) throws IOException, ImageProcessingException {
        // ----------------- Load JPEG --------------------
        List<JPEGReader> jpegs = List.of(new JPEGReader("ca-1.jpg"), new JPEGReader("ca-21.jpg"), new JPEGReader("jawa-1.jpg"), new JPEGReader("jawa-2.jpg"), new JPEGReader("jawa-3.jpg"), new JPEGReader("jawa-4.jpg"), new JPEGReader("lib1-xMedUni_Wien_Josephinum_Bibliothek-meduni_wien.jpg"), new JPEGReader("lib-1.jpg"), new JPEGReader("lib-2.jpg"), new JPEGReader("m-1.jpg"), new JPEGReader("m-2.jpg"), new JPEGReader("m-3.jpg"), new JPEGReader("m-4.jpg"), new JPEGReader("ml-1.jpg"), new JPEGReader("ml_2.jpg"), new JPEGReader("ml-3.jpg"), new JPEGReader("ml-5.jpg"), new JPEGReader("onb-1.jpg"), new JPEGReader("onb-2.jpg"), new JPEGReader("onb-3.jpg"), new JPEGReader("par-1.jpg"), new JPEGReader("vie-1.jpg"), new JPEGReader("vie-2.jpg"), new JPEGReader("vie-3.jpg"));
        // ----------------- Load PDF --------------------
        List<PDFReader> pdfs = List.of(new PDFReader("Cairo.pdf"), new PDFReader("Humans.pdf"), new PDFReader("Java.pdf"), new PDFReader("Jawa.pdf"), new PDFReader("Mammals.pdf"), new PDFReader("Marine life.pdf"), new PDFReader("Paris.pdf"), new PDFReader("The Austrian National Library.pdf"), new PDFReader("The University Library of the University of Vienna.pdf"), new PDFReader("Vienna.pdf"));


        // ----------------- JPEG Metadata --------------------
        // just output it on the console (different metadata formats)
        for (JPEGReader jpg: jpegs) {
            extractMetadataFromImage(jpg);
        }

        // ----------------- PDF Metadata--------------------
        // NOTE: save in .xmp files in resources folder
        for (PDFReader pdf: pdfs) {
            extractMetadataFromPDF(pdf);
        }
    }


    private static void extractMetadataFromPDF(PDFReader pdf) throws IOException {
        PdfReader metadataReader = new PdfReader(pdf.getPath() + pdf.getFileName());
        String result = new String(metadataReader.getMetadata());

        FileWriter xmpWriter = new FileWriter("src/main/resources/pdf/" + pdf.getFileName().substring(0, pdf.getFileName().length() - 3) + "xmp");
        xmpWriter.write(result.trim());
        xmpWriter.close();
    }


    private static void extractMetadataFromImage(JPEGReader jpg) throws ImageProcessingException, IOException {
        // get metadata
        Metadata metadata = ImageMetadataReader.readMetadata(new File(jpg.getPath() + jpg.getFileName()));

        StringBuilder result = new StringBuilder();

        // obtain the Exif directory
        XmpDirectory xmp = metadata.getFirstDirectoryOfType(XmpDirectory.class);
        for (Tag t : xmp.getTags()) {
            result.append(t.getTagName() + ": " + t.getDescription());
            result.append("\n");
        }

        // obtain the iptc directory
        IptcDirectory iptc = metadata.getFirstDirectoryOfType(IptcDirectory.class);
        for (Tag t : iptc.getTags()) {
            result.append(t.getTagName() + ": " + t.getDescription());
            result.append("\n");
        }

        // obtain the exif directory
        //        if(metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class) != null) {
        //            ExifSubIFDDirectory exifDir = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        //            for (Tag t : exifDir.getTags()) {
        //                result.append(t.getTagName() + ": " + t.getDescription());
        //                result.append("\n");
        //            }
        //        }
        if(metadata.getFirstDirectoryOfType(ExifImageDirectory.class) != null) {
            ExifImageDirectory exif = metadata.getFirstDirectoryOfType(ExifImageDirectory.class);
            for (Tag t : exif.getTags()) {
                result.append(t.getTagName() + ": " + t.getDescription());
                result.append("\n");
            }
        }

        // write in resource folder .txt format
        FileWriter jpegWriter = new FileWriter("src/main/resources/jpeg/" + jpg.getFileName().substring(0, jpg.getFileName().length() - 3) + "txt");
        jpegWriter.write(result.toString());
        jpegWriter.close();
    }
}
