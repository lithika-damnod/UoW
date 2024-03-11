import java.util.Scanner;
public class Person {
    private Scanner scanner = new Scanner(System.in);
    private String name, surname, email;

    public Person() {
        this.name = input("\nName: ");
        this.surname = input("Surname: ");
        this.email = input("Email: ");
        System.out.println();
    }
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public void set_name(String name) {
        this.name = name;
    }
    public String get_name() {
        return name;
    }
    public void set_surname(String surname) {
        this.surname = surname;
    }
    public String get_surname() {
        return surname;
    }
    public void set_email(String email) {
        this.email = email;
    }
    public String get_email() {
        return email;
    }

    public void print_info() {
        System.out.println("Name: " + name + " Surname: " + surname + "Email: " + email);
    }

    private String input(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String inp = scanner.next();
                return inp;
            } catch(Exception e) { // this case is rare because strings can also contain numbers, just in case something unexpected happens.
                System.out.println("invalid input. try again \n");
                scanner.nextLine(); // consume invalid input
            }
        }
    }
}
