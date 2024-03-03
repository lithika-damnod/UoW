public class Ticket {
    private char row;
    private int seat, price;
    private Person person;

    public Ticket(char row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public void set_row(char row) {
        this.row = row;
    }
    public char get_row() {
        return row;
    }
    public void set_seat(int seat) {
        this.seat = seat;
    }
    public int get_seat() {
        return seat;
    }
    public void set_price(int price) {
        this.price = price;
    }
    public int get_price() {
        return price;
    }
    public void set_person(Person person) {
        this.person = person;
    }
    public Person get_person() {
        return person;
    }

    public void print_info() {
        System.out.println("Ticket Details \n" +
                "Row: " + row + "\t Seat: " + seat +
                "\nPrice: £" + price +
                "\nName: " + person.get_name() + " " + person.get_surname() +
                "\nEmail: " + person.get_email() + "\n"
        );
    }
}
