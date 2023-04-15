/**
 * The Venue class represents a venue object, which is used to hold information about a location.
 * <p>
 * It implements Comparable interface, which allows comparison of venues based on their ids.
 */
package common.structureClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class Venue implements Comparable<Venue>, Serializable {
    /**
     * The id of the venue
     */
    private Integer id;
    /**
     * The name of the venue
     */
    @XmlElement(name = "name")
    private String name;
    /**
     * The capacity of the venue
     */
    @XmlElement(name = "capacity")
    private long capacity;
    /**
     * The type of the venue
     */
    @XmlElement(name = "type")
    private VenueType type;
    /**
     * The address of the venue
     */
    @XmlElement(name = "address")
    private Address address;

    /**
     * Gets the id of the venue
     *
     * @return the id of the venue
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the venue
     *
     * @param id the id of the venue
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the venue
     *
     * @return the name of the venue
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the venue
     *
     * @param name the name of the venue
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the capacity of the venue
     *
     * @return the capacity of the venue
     */
    public long getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the venue
     *
     * @param capacity the capacity of the venue
     */
    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the type of the venue
     *
     * @return the type of the venue
     */
    public VenueType getType() {
        return type;
    }

    /**
     * Sets the type of the venue
     *
     * @param type the type of the venue
     */
    public void setType(VenueType type) {
        this.type = type;
    }

    /**
     * Gets the address of the venue
     *
     * @return the address of the venue
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the venue
     *
     * @param address the address of the venue
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Returns a formatted string that represents a line of output.
     *
     * @param line the line to print
     */
    private String lineOutput(String line, Object value) {
        return line + " : " + value + "\n";
    }

    /**
     * Returns a formatted string that represents the Venue object.
     *
     * @return a formatted string that represents the Venue object
     */
    @Override
    public String toString() {
        return "Venue object: " + "\n" +
                lineOutput("id", id) +
                lineOutput("name", name) +
                lineOutput("capacity", capacity) +
                lineOutput("type", type) +
                lineOutput("address", address);
    }

    /**
     * Compares this Venue object with the specified Venue object for order based on their ids.
     *
     * @param o the Venue object to be compared
     * @return a negative integer, zero, or a positive integer as this Venue object is less than, equal to, or greater than the specified Venue object
     */
    @Override
    public int compareTo(Venue o) {
        return id.compareTo(o.getId());
    }
}
