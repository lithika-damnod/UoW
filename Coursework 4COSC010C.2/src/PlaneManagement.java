import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlaneManagement {
    private static Scanner scanner = new Scanner(System.in);
    private static final char[] ROWS = {'A', 'B', 'C', 'D'}; // possible row letters

    private static final boolean[][] SEATS = { new boolean[14], new boolean[12], new boolean[12], new boolean[14] };
    /*
        false: 0 (default), true: 1
        int: 4 bytes, boolean: 1 byte,
        Using `boolean` instead of `int` can be more memory-efficient.
     */

    private static Ticket[] TICKETS = {};
    // for pushing data into the TICKETS array
    private static void push(Ticket ticket) {
        int newSize = TICKETS.length + 1;
        Ticket[] newArr = new Ticket[newSize];

        // copy elements from old array to the new array
        for(int i=0; i<TICKETS.length; i++) {
            newArr[i] = TICKETS[i];
        }

        // push the new value
        newArr[newSize - 1] = ticket;
        TICKETS = newArr;
    }
    // for removing data from TICKETS array by index
    private static void remove(int index) {
        int newSize = TICKETS.length - 1;
        Ticket[] newArr = new Ticket[newSize];

        // copy elements from old array to the new array
        for(int i=0; i<TICKETS.length; i++) {
            if (i != index)
                newArr[i] = TICKETS[i];
        }

        TICKETS = newArr;
    }


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
                case 6 -> search_ticket();
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
        int rowNumber = (int) userSelectedRowLetter - 65; // converts the entered row letter into an integer to match up with the SEATS array
        if (!SEATS[rowNumber][userSelectedSeatNumber-1]) {
            SEATS[rowNumber][userSelectedSeatNumber-1] = true;

            // default constructor of Person() prompts for user input
            push(new Ticket(ROWS[rowNumber], userSelectedSeatNumber, calculate_seat_pricing(userSelectedSeatNumber), new Person()));
            save(userSelectedRowLetter, userSelectedSeatNumber); // save info into the file
            System.out.println("seat booked! ✈️ \n");
        } else
            System.out.println("seat not available. ❌ \n");
    }

    private static void cancel_seat() {
        input_seat_info();

        // check if the seat is available and if so book it
        int rowNumber = (int) userSelectedRowLetter - 65; // converts the entered row letter into an integer to match up with the SEATS array
        if (SEATS[rowNumber][userSelectedSeatNumber-1]) {
            SEATS[rowNumber][userSelectedSeatNumber-1] = false;

            // remove the Ticket object from the TICKETS array
            for(int i=0; i<TICKETS.length; i++) {
                Ticket ticket = TICKETS[i];
                if(ticket.get_seat() == userSelectedSeatNumber && ticket.get_row() == userSelectedRowLetter) {
                    remove(i); // remove the ticket from the TICKETS array
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

        // if the function still runs, it means that all the seats are booked...
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
            total += ticket.get_price(); // add the price of the ticket to total
            ticket.print_info();
            System.out.println();
        }

        System.out.println("Total Sales: £" + total);
    }

    private static void search_ticket() {
        input_seat_info();

        Ticket result = search(userSelectedRowLetter, userSelectedSeatNumber);
        if(result == null)
            System.out.println("This seat is available. \n");
        else {
            System.out.println();
            result.print_info();
        }
    }

    // searches for a ticket in TICKETS array and returns a Ticket object
    private static Ticket search(char row, int seat) {
        Ticket result = null;
        for(Ticket ticket : TICKETS) {
            if(ticket.get_seat() == seat && ticket.get_row() == row) {
                result = ticket;
            }
        }

        return result; // if null, results have not been found.
    }
    private static void save(char row, int seat) {
        String path = String.valueOf(row).toUpperCase() + seat + ".txt"; // path name
        File file = new File(path);
        try {
            // if file doesn't exist, create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter filewriter = new FileWriter(path);
            Ticket ticket = search(row, seat);
            filewriter.write(
                    "row: " + Character.toUpperCase(ticket.get_row()) + " seat: " + ticket.get_seat() +
                        "\nprice: £" +  ticket.get_price() + "\n" +
                        "Name: " + ticket.get_person().get_name() + " " + ticket.get_person().get_surname() + "\n" +
                        "Email: " + ticket.get_person().get_email()
            );
            filewriter.flush();
            filewriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // handles getting input for row letter and the seat number with error handling
    private static void input_seat_info() {
        while (true) {
            System.out.print("Row Letter: ");
            try {
                userSelectedRowLetter = scanner.next().toUpperCase().charAt(0);
                if (!(userSelectedRowLetter == 'A' || userSelectedRowLetter == 'B' || userSelectedRowLetter == 'C' || userSelectedRowLetter == 'D'))
                    System.out.println("invalid input. please enter a single character 'A', 'B', 'C', 'D'.  This runs!\n");
                else
                    break;
            } catch (Exception e) {
                System.out.println("invalid input. please enter a single character 'A', 'B', 'C', 'D'. \n");
                scanner.nextLine(); // consume invalid input
            }
        }

        while (true) {
            System.out.print("Seat Number: ");
            try {
                userSelectedSeatNumber = scanner.nextInt();
                if (userSelectedSeatNumber > 0 && userSelectedSeatNumber <= (userSelectedRowLetter == 'A' || userSelectedRowLetter == 'D' ? 14 : 12))
                    break;
                else
                    System.out.println("invalid input. seat number entered is not valid.\n");
            } catch (InputMismatchException e) {
                System.out.println("invalid input. only integer values are accepted.\n");
                scanner.nextLine(); // consume invalid input
            } catch(Exception e) {
                System.out.println("invalid input.");
            }
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


