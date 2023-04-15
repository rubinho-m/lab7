/**
 * This class represents a TicketXMLParser object that is used to parse an XML file containing a collection of tickets.
 * It extends the abstract class TicketXMLWorker.
 */

package server.xml;

import server.collectionManagement.CollectionManager;
import common.exceptions.XMLTroubleException;
import common.structureClasses.Ticket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public class TicketXMLParser extends TicketXMLWorker {
    /**
     * Constructor for the TicketXMLParser class.
     *
     * @param path the path to the XML file containing the collection of tickets
     */
    public TicketXMLParser(String path) {
        super(path);
    }

    /**
     * Method used to parse the XML file and return the set of tickets contained in it.
     *
     * @return the set of tickets contained in the XML file
     * @throws FileNotFoundException if the XML file cannot be found at the specified path
     * @throws XMLTroubleException   if there is an error parsing the XML file
     */
    public Set<Ticket> parse() throws FileNotFoundException, XMLTroubleException, UnmarshalException {
        try {
            FileInputStream in = new FileInputStream(path);
            Reader reader = new InputStreamReader(in);
            File file = new File(path);
            if (!file.canWrite()){
                System.out.println("Be careful, you can't save into this file");

            }
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            CollectionManager collection = (CollectionManager) unmarshaller.unmarshal(reader);
            Ticket.setLastId(0L);

            for (Ticket ticket : collection.getCollection()) {
                Long nowID = Ticket.getLastId() + 1;
                ticket.setId(nowID);
                if (ticket.getVenue() != null){
                    ticket.getVenue().setId(Math.toIntExact(nowID));
                }
                Ticket.increaseId();
                ticket.setCreationDate(LocalDate.now());
            }
            return collection.getCollection();
        } catch (JAXBException e){
            return new LinkedHashSet<Ticket>();
        } catch (FileNotFoundException e){
            throw new XMLTroubleException("Some troubles with XMLFile. Check that it's not empty and has correct name");
        }

    }
}
