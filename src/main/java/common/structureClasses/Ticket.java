/**
 * The Ticket class represents a ticket that can be sold for an event. It implements the Comparable interface to allow for
 * sorting by price. The class contains fields for id, creation date, name, coordinates, price, comment, refundable status,
 * ticket type, and venue. It also contains methods for setting and getting these fields, as well as a method for getting
 * the last assigned id value and methods for increasing and decreasing the id value.
 */
package common.structureClasses;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.NONE)
public class Ticket implements Comparable<Ticket>, Serializable {
    private Long id;
    private static Long lastId = 0L;

    /**
     * Returns the last assigned id value.
     *
     * @return the last assigned id value.
     */
    public static Long getLastId() {
        if (lastId == null){
            return 0L;
        }
        return lastId;
    }

    /**
     * Increases the id value by 1.
     */
    public static void increaseId() {
        lastId++;
    }

    public static void decreaseId() {
        lastId--;
    }

    /**
     * Sets the last assigned id value.
     *
     * @param lastId the last assigned id value.
     */
    public static void setLastId(Long lastId) {
        Ticket.lastId = lastId;
    }

    private LocalDate creationDate;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "coordinates")
    private Coordinates coordinates;
    @XmlElement(name = "price")
    private Double price;
    @XmlElement(name = "comment")
    private String comment;
    @XmlElement(name = "refundable")
    private boolean refundable;
    @XmlElement(name = "type")
    private TicketType type;
    @XmlElement(name = "venue")
    private Venue venue;

    /**
     * Sets the id value of the ticket.
     *
     * @param id the id value of the ticket.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the creation date of the ticket.
     *
     * @param creationDate the creation date of the ticket.
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Sets the name of the ticket.
     *
     * @param name the name of the ticket.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the coordinates of the ticket.
     *
     * @param coordinates the coordinates of the ticket.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Sets the price of the ticket.
     *
     * @param price the price of the ticket.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the comment of the ticket.
     *
     * @param comment the comment of the ticket.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Sets the refundable status of the ticket.
     *
     * @param refundable the refundable status of the ticket.
     */
    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    /**
     * Sets the ticket type of the ticket.
     *
     * @param type the ticket type of the ticket.
     */
    public void setType(TicketType type) {
        this.type = type;
    }

    /**
     * Sets the venue for the ticket.
     *
     * @param venue the ticket type of the ticket.
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public double getPrice() {
        return price;
    }

    public String getComment() {
        return comment;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public TicketType getType() {
        return type;
    }

    public Venue getVenue() {
        return venue;
    }

    @Override
    public int compareTo(Ticket o) {
        return price.compareTo(o.getPrice());
    }

    private String lineOutput(String line, Object value) {
        return line + " : " + value + "\n";
    }

    @Override
    public String toString() {
        return "Ticket object:" + "\n" +
                lineOutput("id", id) +
                lineOutput("name", name) +
                lineOutput("creationDate", creationDate) +
                lineOutput("coordinates", coordinates) +
                lineOutput("price", price) +
                lineOutput("comment", comment) +
                lineOutput("refundable", refundable) +
                lineOutput("type", type) +
                lineOutput("venue", venue);
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
