package Task_2;

import Task_1.JPEGReader;
import Task_1.PDFReader;
import com.aspose.pdf.Document;
import com.aspose.pdf.internal.ms.System.Collections.Generic.lk;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifImageDirectory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.iptc.IptcDirectory;
import com.drew.metadata.xmp.XmpDirectory;
import com.lowagie.text.pdf.PdfReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MetadataExtractor {
    public static void main(String[] args) throws IOException, ImageProcessingException {
        // ----------------- Load JPEG --------------------
        String path = "src/main/java/Assignment3Files/jpeg/";
        List<JPEGReader> jpegs = List.of(new JPEGReader(path, "ca-1.jpg"), new JPEGReader(path, "ca-21.jpg"), new JPEGReader(path, "jawa-1.jpg"), new JPEGReader(path, "jawa-2.jpg"), new JPEGReader(path, "jawa-3.jpg"), new JPEGReader(path, "jawa-4.jpg"), new JPEGReader(path, "lib1-xMedUni_Wien_Josephinum_Bibliothek-meduni_wien.jpg"), new JPEGReader(path, "lib-1.jpg"), new JPEGReader(path, "lib-2.jpg"), new JPEGReader(path, "m-1.jpg"), new JPEGReader(path, "m-2.jpg"), new JPEGReader(path, "m-3.jpg"), new JPEGReader(path, "m-4.jpg"), new JPEGReader(path, "ml-1.jpg"), new JPEGReader(path, "ml_2.jpg"), new JPEGReader(path, "ml-3.jpg"), new JPEGReader(path, "ml-5.jpg"), new JPEGReader(path, "onb-1.jpg"), new JPEGReader(path, "onb-2.jpg"), new JPEGReader(path, "onb-3.jpg"), new JPEGReader(path, "par-1.jpg"), new JPEGReader(path, "vie-1.jpg"), new JPEGReader(path, "vie-2.jpg"), new JPEGReader(path, "vie-3.jpg"));

         // ----------------- Load PDF --------------------
        String pdfPath = "src/main/java/Assignment3Files/pdf/";
        List<PDFReader> pdfs = List.of(new PDFReader(pdfPath, "Cairo.pdf"), new PDFReader(pdfPath, "Humans.pdf"), new PDFReader(pdfPath, "Java.pdf"), new PDFReader(pdfPath, "Jawa.pdf"), new PDFReader(pdfPath, "Mammals.pdf"), new PDFReader(pdfPath, "Marine life.pdf"), new PDFReader(pdfPath, "Paris.pdf"), new PDFReader(pdfPath, "The Austrian National Library.pdf"), new PDFReader(pdfPath, "The University Library of the University of Vienna.pdf"), new PDFReader(pdfPath, "Vienna.pdf"));


        // ----------------- JPEG Metadata --------------------
        // just output it on the console (different metadata formats)
        for (JPEGReader jpg: jpegs) {
            extractMetadataFromImage(jpg);
        }

        // ----------------- PDF Metadata--------------------
        // NOTE: save in .xmp files in resources folder
        for (PDFReader pdf: pdfs) {
            extractMetadataFromPDF(pdf);
            extractMetadataFromPDFAsText(pdf);
        }
    }

    private static void extractMetadataFromPDFAsText(PDFReader pdf) throws IOException {
        StringBuilder result= new StringBuilder();
        result.append(pdf.getFileName() + "\n");

        Document doc = new Document(pdf.getPath() + pdf.getFileName());
        lk<String> iterator = doc.getMetadata().getKeys().iterator();

        while(iterator.hasNext()) {
            String key = iterator.next();
            result.append(key + ": " + doc.getMetadata().get_Item(key) + "\n");
        }

        FileWriter xmpWriter = new FileWriter("src/main/resources/metadata/pdf/txt/" + pdf.getFileName().substring(0, pdf.getFileName().length() - 3) + "txt");
        xmpWriter.write(result.toString());
        xmpWriter.close();
    }

    private static void extractMetadataFromPDF(PDFReader pdf) throws IOException {
        PdfReader metadataReader = new PdfReader(pdf.getPath() + pdf.getFileName());
        String result = new String(metadataReader.getMetadata());

        FileWriter xmpWriter = new FileWriter("src/main/resources/metadata/pdf/xmp/" + pdf.getFileName().substring(0, pdf.getFileName().length() - 3) + "xmp");
        xmpWriter.write(result.trim());
        xmpWriter.close();
    }


    private static void extractMetadataFromImage(JPEGReader jpg) throws ImageProcessingException, IOException {
        // get metadata
        Metadata metadata = ImageMetadataReader.readMetadata(new File(jpg.getPath() + jpg.getFileName()));

        StringBuilder result = new StringBuilder();
        result.append(jpg.getFileName() + "\n");

        // obtain the xmp directory
        XmpDirectory xmpDirectory = metadata.getFirstDirectoryOfType(XmpDirectory.class);
        List<String> subjects = xmpDirectory.getXmpProperties().keySet().stream().collect(Collectors.toList());
        for (String key : subjects) {
            result.append(key + ": " + xmpDirectory.getXmpProperties().get(key) + "\n");
        }

        // obtain the iptc directory
        if(metadata.getFirstDirectoryOfType(IptcDirectory.class) != null) {
            IptcDirectory exif = metadata.getFirstDirectoryOfType(IptcDirectory.class);
            for (Tag t : exif.getTags()) {
                result.append("iptc:" + t.getTagName() + ": " + t.getDescription() + "\n");
            }
        }

        // obtain the exif directory
        if(metadata.getFirstDirectoryOfType(ExifIFD0Directory.class) != null) {
            ExifIFD0Directory exif = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            for (Tag t : exif.getTags()) {
                result.append("exif:" + t.getTagName() + ": " + t.getDescription() + "\n");
            }
        }

        if(metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class) != null) {
            ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            for (Tag t : exif.getTags()) {
                result.append("exif:" + t.getTagName() + ": " + t.getDescription() + "\n");
            }
        }

        if(metadata.getFirstDirectoryOfType(ExifImageDirectory.class) != null) {
            ExifImageDirectory exif = metadata.getFirstDirectoryOfType(ExifImageDirectory.class);
            for (Tag t : exif.getTags()) {
                result.append("exif:" + t.getTagName() + ": " + t.getDescription() + "\n");
            }
        }

        // write in resource folder .txt format
        FileWriter jpegWriter = new FileWriter("src/main/resources/metadata/jpeg/" + jpg.getFileName().substring(0, jpg.getFileName().length() - 3) + "txt");
        jpegWriter.write(result.toString());
        jpegWriter.close();
    }
}
