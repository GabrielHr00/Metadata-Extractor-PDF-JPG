package Task_6;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> rdfs = List.of("ca-1.rdf","ca-21.rdf", "jawa-1.rdf", "jawa-2.rdf", "jawa-3.rdf", "jawa-4.rdf", "lib1-xMedUni_Wien_Josephinum_Bibliothek-meduni_wien.rdf", "lib-1.rdf", "lib-2.rdf", "m-1.rdf", "m-2.rdf", "m-3.rdf", "m-4.rdf", "ml-1.rdf", "ml_2.rdf", "ml-3.rdf", "ml-5.rdf", "onb-1.rdf", "onb-2.rdf", "onb-3.rdf", "par-1.rdf", "vie-1.rdf", "vie-2.rdf", "vie-3.rdf", "Cairo.rdf", "Humans.rdf", "Java.rdf", "Jawa.rdf", "Mammals.rdf", "Marine-life.rdf", "Paris.rdf", "The-Austrian-National-Library.rdf", "The-University-Library-of-the-University-of-Vienna.rdf", "Vienna.rdf");

        System.out.println("\n" + "================================================================================================");
        System.out.println("        1st Query is executed successfully! All media objects that have relationship to others: ");
        System.out.println("================================================================================================");

        for (var file : rdfs) {
            SparqlQueries query = new SparqlQueries(file,
                    "SELECT  ?result\n" +
                            "WHERE { ?x ?y ?result . FILTER regex(?result, '^Vienna')}");

            query.executeQuery();
        }


        System.out.println("\n" + "======================================================================================");
        System.out.println("        5th Query is executed successfully! All media objects related to 'Vienna': ");
        System.out.println("======================================================================================");

        for (var file : rdfs) {
            SparqlQueries query = new SparqlQueries(file,
                    "SELECT  ?result\n" +
                            "WHERE { ?x ?y ?result . FILTER regex(?result, '^Vienna')}");

            query.executeQuery();
        }

        System.out.println("\n" + "======================================================================================");
        System.out.println("        6th Query is executed successfully! All media objects with ISO less than 400: ");
        System.out.println("======================================================================================");

        for (var file : rdfs) {
            SparqlQueries query = new SparqlQueries(file,
                    "PREFIX exif:    <http://ns.adobe.com/exif/1.0/>\n" +
                            "PREFIX xsd:        <http://www.w3.org/2001/XMLSchema#>\n" +
                            "SELECT  ?isso\n" +
                            "WHERE {?x exif:ISOSpeedRatings ?isso . FILTER (xsd:integer(?isso) < 400)}");

            query.executeQuery();
        }
    }
}
