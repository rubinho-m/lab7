/**
 * The CollectionInput class is responsible for collecting user input for creating Ticket objects.
 * It prompts the user to enter the required fields such as name, coordinates, price, comment, and refundable.
 * It also prompts the user to optionally enter the type of ticket and venue.
 */

package common.commandParsing;


import common.exceptions.WrongScriptException;
import common.structureClasses.*;

import java.util.List;
import java.util.Scanner;

public class CollectionInput {
    private Scanner scanner;
    private boolean isFile;
    private String[] stringCoords;
    private float x;
    private int y;
    private boolean refundable;
    private String response;
    private String ticketType;
    private String venueType;
    private double price;
    private long capacity;
    private String name;
    private String comment;
    private String venueName;
    private String street;
    private String stringRefundable;

    public CollectionInput(Scanner scanner, boolean isFile) {
        this.scanner = scanner;
        this.isFile = isFile;
    }

    private String[] inputCoordinates() {
        Double temp_x = inputX();
        Integer temp_y = inputY();
        String[] tmp = new String[2];
        tmp[0] = String.valueOf(temp_x);
        tmp[1] = String.valueOf(temp_y);
        return tmp;

    }
    private Double inputX(){
        if (!isFile){
            System.out.println("Please, enter x coordinate");
        }
        return Double.valueOf(scanner.nextLine());
    }

    private Integer inputY(){
        if (!isFile){
            System.out.println("Please, enter y coordinate");
        }
        return Integer.valueOf(scanner.nextLine());
    }

    private String inputPrice() {
        if (!isFile) {
            System.out.println("Please, enter the price");
        }
        return scanner.nextLine();
    }

    private String inputRefundable() {
        if (!isFile) {
            System.out.println("Is your ticket refundable? (true/false)");
        }
        return scanner.nextLine();
    }

    private String inputYesOrNo(String type) {
        if (!isFile) {
            System.out.printf("Do you want to enter the type of %s? (y/n)%n", type);
        }
        return scanner.nextLine();
    }

    public String inputTypeOfTicket() {
        if (!isFile) {
            System.out.println("Please, enter the type of ticket");
            System.out.println("One of these: VIP, BUDGETARY, CHEAP");
        }
        return scanner.nextLine().toUpperCase();
    }

    public String inputTypeOfVenue() {
        if (!isFile) {
            System.out.println("Please, enter the type of venue");
            System.out.println("One of these: BAR, LOFT, THEATRE, STADIUM");
        }
        return scanner.nextLine().toUpperCase();
    }

    public Ticket getCollection() throws WrongScriptException {
        Ticket newTicket = new Ticket();
        if (!isFile) {
            System.out.println("Please, enter the name");
        }
        while (true) {
            name = scanner.nextLine();
            if (name.isBlank()) {
                System.out.println("Name can't be the empty");
                System.out.println("Please, enter the name");
                continue;
            }
            newTicket.setName(name);
            break;
        }
        while (true) {
            try {
                stringCoords = inputCoordinates();
                x = Float.parseFloat(stringCoords[0]);
                y = Integer.parseInt(stringCoords[1]);
                if (x > -804 & y <= 269) {
                    break;
                }
                System.out.println("X should be more than -804 and max Y is 269");
            } catch (Exception e) {
                if (isFile) {
                    throw new WrongScriptException();
                }

            }
        }
        Coordinates coords = new Coordinates();
        coords.setX(x);
        coords.setY(y);
        newTicket.setCoordinates(coords);
        while (true) {
            try {
                price = Double.parseDouble(inputPrice());
                if (price < 0) {
                    System.out.println("Price can't be less than 0");
                    continue;
                }
                newTicket.setPrice(price);
                break;
            } catch (Exception e) {
                if (isFile) {
                    throw new WrongScriptException();
                }

            }
        }

        if (!isFile) {
            System.out.println("Please, enter the comment");
        }

        while (true) {
            comment = scanner.nextLine();
            if (comment.isBlank()) {
                System.out.println("Comment can't be the empty");
                System.out.println("Please, enter the comment");
                continue;
            }
            newTicket.setComment(comment);
            break;
        }
        int counter = 0;
        do {
            counter++;
            if (counter > 1 && isFile) {
                throw new WrongScriptException();
            }
            stringRefundable = inputRefundable();
            refundable = Boolean.parseBoolean(stringRefundable);
            newTicket.setRefundable(refundable);
        } while (!(stringRefundable.equals("true") | stringRefundable.equals("false")));

        boolean inputTicketType;
        boolean inputVenue;
        counter = 0;
        do {
            counter++;
            if (counter > 1 && isFile) {
                throw new WrongScriptException();
            }
            response = inputYesOrNo("ticket");
        } while (!(response.equals("y") | response.equals("n")));
        inputTicketType = response.equals("y");
        if (inputTicketType) {
            counter = 0;
            while (true) {
                counter++;
                if (counter > 1 && isFile) {
                    throw new WrongScriptException();
                }
                ticketType = inputTypeOfTicket();
                List<TicketType> enumValues = List.of(TicketType.values());
                if (enumValues.contains(TicketType.valueOf(ticketType))) {
                    newTicket.setType(TicketType.valueOf(ticketType));
                    break;
                }
            }

        }
        counter = 0;
        do {
            counter++;
            if (counter > 1 && isFile) {
                throw new WrongScriptException();
            }
            response = inputYesOrNo("venue");
        } while (!(response.equals("y") | response.equals("n")));
        inputVenue = response.equals("y");
        if (inputVenue) {
            newTicket.setVenue(getVenue());
        }
        return newTicket;
    }

    public String inputCapacity() {
        if (!isFile) {
            System.out.println("Please, enter the capacity");
        }
        return scanner.nextLine();
    }

    public Venue getVenue() throws WrongScriptException {
        Venue newVenue = new Venue();
        if (!isFile) {
            System.out.println("Please, enter the venue:");
            System.out.println("Please, enter the name of venue");
        }
        while (true) {
            venueName = scanner.nextLine();
            if (venueName.isBlank()) {
                System.out.println("Name of venue can't be the empty");
                System.out.println("Please, enter the name of venue");
                continue;
            }
            newVenue.setName(venueName);
            break;
        }

        while (true) {
            try {
                capacity = Long.parseLong(inputCapacity());
                if (capacity <= 0) {
                    System.out.println("Capacity should be more than 0");
                    continue;
                }
                newVenue.setCapacity(capacity);
                break;
            } catch (Exception e) {
                throw new WrongScriptException();
            }
        }
        int counter = 0;
        while (true) {
            counter++;
            if (counter > 1 && isFile) {
                throw new WrongScriptException();
            }
            venueType = inputTypeOfVenue();
            List<VenueType> enumValues = List.of(VenueType.values());
            if (enumValues.contains(VenueType.valueOf(venueType))) {
                newVenue.setType(VenueType.valueOf(venueType));
                break;
            }
        }
        boolean addressInput;
        response = inputYesOrNo("address");
        addressInput = response.equals("y");
        if (addressInput) {
            newVenue.setAddress(getAddress());
        }
        return newVenue;

    }

    public Address getAddress() {
        Address newAddress = new Address();
        if (!isFile) {
            System.out.println("Please, enter the address:");
            System.out.println("Please, enter the street");
        }
        while (true) {
            street = scanner.nextLine();
            if (street.isBlank()) {
                System.out.println("Street name can't be the empty");
                System.out.println("Please, enter the street");
                continue;
            }
            newAddress.setStreet(street);
            break;
        }
        return newAddress;
    }
}
