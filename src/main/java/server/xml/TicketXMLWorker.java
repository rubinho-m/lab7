/**
 * Abstract class that provides functionality for working with XML files
 */

package server.xml;

public abstract class TicketXMLWorker {
    /**
     * The path to the XML file
     */
    protected String path;

    /**
     * Constructor that takes a path to an XML file
     *
     * @param path - the path to the XML file
     */
    public TicketXMLWorker(String path) {
        this.path = path;
    }


}
