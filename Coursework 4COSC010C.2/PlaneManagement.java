import java.util.ArrayList;
import java.util.Scanner;

class PlaneManagement {
    private static Scanner scanner = new Scanner(System.in);
    private static final char[] ROWS = {'A', 'B', 'C', 'D'};
    private static final boolean[][] SEATS = { new boolean[14], new boolean[12], new boolean[12], new boolean[14] };
    private static ArrayList<Ticket> TICKETS = new ArrayList<>();
    private static char userSelectedRowLetter;
    private static int userSelectedSeatNumber;

    public static void main(String[] args) {
        System.out.println("Welcome to the Plane Management Application");
        boolean terminate = false;
        do {
            int menuOption = menu();
            switch (menuOption) {
                case 1 -> buy_seat();
                case 2 -> cancel_seat();
                case 3 -> find_first_available();
                case 4 -> show_seating_plan();
                case 5 -> print_tickets_info();
                case 0 -> terminate = true;
                default -> System.out.println("Invalid option. Please enter a valid number. \n");
            }
        } while(!terminate);
    }

    private static int menu() {
        System.out.print(
                "**************************************** \n" +
                        "*             MENU OPTIONS             * \n" +
                        "**************************************** \n" +
                        "\t 1) Buy a Seat\n" +
                        "\t 2) Cancel a Seat\n" +
                        "\t 3) Find first available\n" +
                        "\t 4) Show seating plan\n" +
                        "\t 5) Print tickets information and total sales\n" +
                        "\t 6) Search ticket\n" +
                        "\t 0) Quit\n" +
                        "**************************************** \n" +
                        "Please select an option: "
        );

        return scanner.nextInt();
    }

    private static void buy_seat() {
        input_seat_info();

        // check if the seat is available and if so book it
        int rowNumber = (int) userSelectedRowLetter - 97;
        if (!SEATS[rowNumber][userSelectedSeatNumber-1]) {
            SEATS[rowNumber][userSelectedSeatNumber-1] = true;

            // default constructor of Person() prompts for user input
            TICKETS.add(new Ticket(ROWS[rowNumber], userSelectedSeatNumber, calculate_seat_pricing(userSelectedSeatNumber), new Person()));
            System.out.println("seat booked! ✈️ \n");
        } else
            System.out.println("seat not available. ❌ \n");
    }

    private static void cancel_seat() {
        input_seat_info();

        // check if the seat is available and if so book it
        int rowNumber = (int) userSelectedRowLetter - 97;
        if (SEATS[rowNumber][userSelectedSeatNumber-1]) {
            SEATS[rowNumber][userSelectedSeatNumber-1] = false;

            // remove the Ticket object from the TICKETS array:TODO
            for(int i=0; i<TICKETS.size(); i++) {
                Ticket ticket = TICKETS.get(i);
                if(ticket.get_seat() == userSelectedSeatNumber && ticket.get_row() == userSelectedRowLetter) {
                    TICKETS.remove(i);
                }
            }
            System.out.println("seat canceled! 👍️ \n");
        } else
            System.out.println("seat is not yet booked. ✋ \n");
    }

    private static void find_first_available() {
        for(int i=0; i<SEATS.length; i++) {
            for(int j=0; j<SEATS[i].length; j++) {
                if (!SEATS[i][j]) {
                    System.out.println("Row Letter: " + ROWS[i] + " Seat Number: " + (j+1) + "\n");
                    return;
                }
            }
        }

        System.out.println("All the seats are reserved. 🔎 \n");
    }

    private static void show_seating_plan() {
        for (boolean[] row : SEATS) {
            for (boolean seat : row)
                System.out.print(seat ? 'X' : 'O');
            System.out.println();
        }

        System.out.println();
    }

    private static void print_tickets_info() {
        int total = 0;
        for (Ticket ticket : TICKETS) {
            total += ticket.get_price();
            ticket.print_info();
            System.out.println();
        }

        System.out.println("Total Sales: £" + total);
    }

    private static void input_seat_info() {
        while (true) {
            System.out.print("Row Letter: ");
            userSelectedRowLetter = scanner.next().toLowerCase().charAt(0);
            if (!(userSelectedRowLetter == 'a' || userSelectedRowLetter == 'b' || userSelectedRowLetter == 'c' || userSelectedRowLetter == 'd'))
                System.out.println("row letter entered is not valid. ⚠️\n");
            else
                break;
        }

        while (true) {
            System.out.print("Seat Number: ");
            userSelectedSeatNumber = scanner.nextInt();
            if (userSelectedSeatNumber > 0 && userSelectedSeatNumber <= (userSelectedRowLetter == 'a' || userSelectedRowLetter == 'd' ? 14 : 12))
                break;
            else
                System.out.println("seat number entered is not valid. ⚠️\n");
        }
    }
    private static int calculate_seat_pricing(int seat) {
        if (seat >= 10 && seat <= 14)
            return 180;
        else if(seat >= 6)
            return 150;
        else if(seat > 0)
            return 200;

        return -1;
    }
}

class Person {
    private static Scanner scanner = new Scanner(System.in);
    private static String name, surname, email;

    public Person() {
        System.out.print("\nName: ");
        name = scanner.next();
        System.out.print("Surname: ");
        surname = scanner.next();
        System.out.print("Email: ");
        email = scanner.next();
        System.out.println();
    }
    public Person(String name, String surname, String email) {
        Person.name = name;
        Person.surname = surname;
        Person.email = email;
    }

    public static void set_name(String name) {
        Person.name = name;
    }
    public static String get_name() {
        return name;
    }
    public static void set_surname(String surname) {
        Person.surname = surname;
    }
    public static String get_surname() {
        return surname;
    }
    public static void set_email(String email) {
        Person.email = email;
    }
    public static String get_email() {
        return email;
    }

    public static void print_info() {
        System.out.println("Name: " + name + " Surname: " + surname + "Email: " + email);
    }
}

class Ticket {
    private static char row;
    private static int seat, price;
    private static Person person;

    public Ticket(char row, int seat, int price, Person person) {
        Ticket.row = row;
        Ticket.seat = seat;
        Ticket.price = price;
        Ticket.person = person;
    }

    public static void set_row(char row) {
        Ticket.row = row;
    }
    public static char get_row() {
        return row;
    }
    public static void set_seat(int seat) {
        Ticket.seat = seat;
    }
    public static int get_seat() {
        return seat;
    }
    public static void set_price(int price) {
        Ticket.price = price;
    }
    public static int get_price() {
        return price;
    }
    public static void set_person(Person person) {
        Ticket.person = person;
    }
    public static Person get_person() {
        return person;
    }

    public static void print_info() {
        System.out.println("Ticket Details \n" +
                "Row: " + Ticket.row + "\t Seat: " + seat +
                "\nPrice: £" + price +
                "\nName: " + Person.get_name() + " " + Person.get_surname() +
                "\nEmail: " + Person.get_email() + "\n"
        );
    }

}

/* e935a372151f4ee092dc6f54a4a6c40a4d254f9e */