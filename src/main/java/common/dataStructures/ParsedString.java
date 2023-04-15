package common.dataStructures;

public class ParsedString<T, S> {
    private T array;
    private S ticket;

    public ParsedString(T array, S... tickets) {
        this.array = array;
        if (tickets.length > 0) {
            this.ticket = tickets[0];
        } else {
            ticket = null;
        }
    }

    public T getArray() {
        return array;
    }

    public S getTicket() {
        return ticket;
    }
}
