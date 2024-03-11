import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlaneManagement {
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
        int rowNumber = (int) userSelectedRowLetter - 65;
        if (!SEATS[rowNumber][userSelectedSeatNumber-1]) {
            SEATS[rowNumber][userSelectedSeatNumber-1] = true;

            // default constructor of Person() prompts for user input
            TICKETS.add(new Ticket(ROWS[rowNumber], userSelectedSeatNumber, calculate_seat_pricing(userSelectedSeatNumber), new Person()));
            save(userSelectedRowLetter, userSelectedSeatNumber); // save info into the file
            System.out.println("seat booked! ✈️ \n");
        } else
            System.out.println("seat not available. ❌ \n");
    }

    private static void cancel_seat() {
        input_seat_info();

        // check if the seat is available and if so book it
        int rowNumber = (int) userSelectedRowLetter - 65;
        if (SEATS[rowNumber][userSelectedSeatNumber-1]) {
            SEATS[rowNumber][userSelectedSeatNumber-1] = false;

            // remove the Ticket object from the TICKETS array
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
        String path = String.valueOf(row).toUpperCase() + seat + ".txt";
        File file = new File(path);
        try {
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

    private static void input_seat_info() {
        while (true) {
            System.out.print("Row Letter: ");
            userSelectedRowLetter = scanner.next().toUpperCase().charAt(0);
            if (!(userSelectedRowLetter == 'A' || userSelectedRowLetter == 'B' || userSelectedRowLetter == 'C' || userSelectedRowLetter == 'D'))
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
