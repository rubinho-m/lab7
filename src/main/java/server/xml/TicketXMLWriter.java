/**
 * The TicketXMLWriter class is responsible for writing data to an XML file.
 */
package server.xml;

import server.collectionManagement.CollectionManager;
import common.exceptions.XMLTroubleException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TicketXMLWriter extends TicketXMLWorker {
    /**
     * Constructor for creating a new TicketXMLWriter instance.
     *
     * @param path the path of the XML file to write to
     */
    public TicketXMLWriter(String path) {
        super(path);
    }

    /**
     * Saves the given CollectionManager's data to an XML file.
     *
     * @param collection the CollectionManager object to be saved
     * @throws IOException         if there are any issues with the output stream
     * @throws XMLTroubleException if there are any issues with writing the XML file
     */
    public void save(CollectionManager collection) throws IOException, XMLTroubleException {
        try {
            FileOutputStream out = new FileOutputStream(path);
            BufferedOutputStream writer = new BufferedOutputStream(out);
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(collection, writer);
            writer.close();
        } catch (JAXBException e) {
            throw new XMLTroubleException("Some troubles with xml file, please fix it");
        }
    }

}
