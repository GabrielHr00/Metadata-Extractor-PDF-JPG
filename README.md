# Metadata Java (XMP, Exif, IPTC, DC, RDF...)
The Description of the task:

The Digital Media Production Company (DMPCO) has a vast number of archived media objects such as image, audio, and PDF files. DMPCO would like to make use of RDF technology to find the relationships between these variously typed media objects. To assist DMPCO to achieve its goal, you are assigned the task to implement a system that reads the various types of metadata that annotate the media objects. Having extracted the metadata, you have to encode those extracted metadata elements by means of RDF triples and add them to the RDF store to allow DMPCO to search the extracted metadata using SPARQL.

The goal of this assignment is to implement a system that realizes the following functional requirements:

1 - The user shall be able to provide the path of the folder where the media objects are stored. The system shall be able to read all the media files of the types JPEG and PDF in the user specified folder and its subfolders.

2 - The system shall recognize the type of each media object and extract the metadata encoded by a specific metadata scheme which is relevant to that object. (Consider Exif, XMP, DC, and IPTC)

3 - The extracted metadata elements have to be encoded by means of RDF triples. In addition, the system shall create an RDF resource for each analyzed media object and interlink the extracted metadata elements with that RDF resource. For each analyzed media object add a new RDF property (analyzedBy) to the RDF resource that has always the value of your Matrikelnummer (student ID).

4 - The created RDF resources shall be added to the main RDF data store. The RDF data store can be an in-memory data store.

5- The system shall preserve the namespaces of the original metadata schemes (e.g., Subject1 dc:title Object1) where dc is defined in the RDF as (xmlns:dc="http://purl.org/dc/elements/1.1/"), and accordingly dc:title qualifies to http://purl.org/dc/elements/1.1/title.

6 - The user shall be able to search the main RDF data store using SPARQL. Provide a SPARQL statement to the following:

   - 6.1. Find all media objects that have relationships to other media objects.
   - 6.2. Find all media objects that have relationships to some media objects of a specific media type.
   - 6.3. Find all media objects of a specific media type that have relationships to other media objects.
   - 6.4. Find media objects that do not have any type of relationships with other media objects.
   - 6.5. Find all media objects that relate to “Vienna”.
   - 6.6. Find all media objects that related to an image that has been take with ISO less than 400.

