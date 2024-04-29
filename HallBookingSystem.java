import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

class HallBookingSystem {
    static LocalDateTime time = LocalDateTime.now();
    public static final StringBuilder[] bookedHistory = new StringBuilder[100];
    private static int bookingCount = 0;

    public static void line(int a, char b) {
        for (int i = 0; i <= a; i++) {
            System.out.print(b);
        }
        System.out.println();
    }

    static void Showtime() {
        line(60,'-');
        System.out.println("# Showtime Information");
        line(60,'-');
        System.out.println("A) Morning   (10:00AM - 12:30PM)");
        System.out.println("B) Afternoon (03:00AM - 5:30PM)");
        System.out.println("C) Night     (07:00AM - 9:30PM)");
        line(60,'-');
    }

    public static void menu(){
        System.out.println("[[ Application Menu ]]");
        System.out.println("<A> Booking");
        System.out.println("<B> Hall");
        System.out.println("<C> ShowTime");
        System.out.println("<D> Reboot Showtime");
        System.out.println("<E> History");
        System.out.println("<F> Exit");
    }


    static void booking(String[][] array) {
        line(60, '-');
        System.out.println("# HALL " );
        for (String[] strings : array) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print("|" + strings[j] + "|" + "\t");
            }
            System.out.println();
        }
        line(60, '-');
        System.out.println("# INSTRUCTION");
        System.out.println("# Single (UpperCase , No space): C-1 ");
        System.out.println("# Multiple (separated by comma): C-1,C-2");
        String[] data;
        do {
            System.out.print("> Please select available seat: ");
            String input = new Scanner(System.in).next().trim();
            if (input.matches("[A-Z]-\\d+(,[A-Z]-\\d+)*")) {
                data = input.split(",");
                break;
            } else {
                System.out.println("Invalid input. Please enter seat(s) in the format 'A-1' ");
            }
        } while (true);

        int studentID;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("> Please enter student ID : ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }
            studentID = scanner.nextInt();
            if (studentID <= 0) {
                System.out.println("Invalid student ID.");
            }
        } while (studentID <= 0);


        line(40,'-');
        Character answer;
        do {
            System.out.print("> Are you sure to book? (Y/N): ");
            String input = new Scanner(System.in).next().trim();
            if (input.matches("[a-zA-Z]")) {
                answer = input.toUpperCase().charAt(0);
                break;
            } else {
                System.out.println("Invalid input.");
            }
        } while (true);
        if (answer.equals('Y')) {
            String[][] splitHall = new String[array.length][(array[0].length) * 2];
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++) {

                    String[] part = array[i][j].split("::");
                    splitHall[i][j * 2] = part[0];
                    splitHall[i][j * 2 + 1] = part[1];
                }
            }
            for (String bookedSeat : data) {
                boolean found = false;
                for (int j = 0; j < array.length; j++) {
                    for (int l = 0; l < array[0].length; l++) {
                        if (Objects.equals(bookedSeat, splitHall[j][l * 2])) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    System.out.println(bookedSeat + " - Invalid Seat");
                    continue;
                }
                for (int j = 0; j < array.length; j++) {
                    for (int k = 0; k < array[0].length; k++) {
                        if (Objects.equals(bookedSeat, splitHall[j][k * 2])) {
                            if (Objects.equals(splitHall[j][k * 2 + 1], "AV")) {
                                splitHall[j][k * 2 + 1] = "BO";
                                HallBookingSystem.bookedHistory[bookingCount++] = new StringBuilder(":" + bookedSeat + ":" + studentID);
                                System.out.println(bookedSeat +" is booked successfully!!");
                            } else
                                System.out.println(bookedSeat + " is already Booked!!");
                        }
                    }
                }
            }
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++) {
                    array[i][j] =  splitHall[i][j * 2] + "::" + splitHall[i][j * 2 + 1];
                }
            }
        }
    }

    public static String[][] rebootHall(int row, int col, char a) {
        String[][] hall = new String[row][col];
        createSeat(hall, a);
        bookingCount = 0;
        return hall;
    }



    public static void history(StringBuilder[] bookingHistory, int bookingCount) {
        if (bookingCount == 0) {
            line(60,'-');
            System.out.println("No booking history found!");
        } else {
            line(60,'-');
            System.out.println("# Booking History");
            line(60,'-');
            for (int i = 0; i < bookingCount; i++) {
                String[] bookingInfo = bookingHistory[i].toString().split(":");
                System.out.println("#NO : " + (i+1));
                System.out.println("#SEATS: |" + bookingInfo[1] + "|");
                System.out.println("  #HALL       #STU.ID           #CREATE MENU  \n");
                System.out.println("  " + " HALL" + "\t" + bookingInfo[0] + "\t" + bookingInfo[2] + "\t\t" + time + "  \n");

                line(60,'-');
            }
        }
    }
    static void createSeat(String[][] array, char a) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = a + "-" + (j + 1) + "::AV";
            }
            a++;
        }
    }


    static void hall(String[][] array, String keyword) {
        System.out.println("#HALL " + " - " + keyword);
        for (String[] strings : array) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print("|" + strings[j] + "|" + "\t");
            }
            System.out.println();
        }
        line(60,'-');
    }




/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void main(String[] args) {
        line(34,'+');
        System.out.println("     CSTAD Hall Booking System   ");
        line(34,'-');

        int row,column;
        do {
            System.out.print("Config Total rows in Hall  : ");
            String input = new Scanner(System.in).nextLine().trim();
            if (!input.matches("\\d+")) {
                System.out.println("Invalid input.");
            } else {
                row = Integer.parseInt(input);
                break;
            }
        } while (true);

        do {
            System.out.print("Config Total Seats(col) per row in hall : ");
            String input = new Scanner(System.in).nextLine().trim();
            if (!input.matches("\\d+")) {
                System.out.println("Invalid input.");
            } else {
                column = Integer.parseInt(input);
                break;
            }
        } while (true);
        String[][] morningHall = new String[row][column];
        String[][] afterHall = new String[row][column];
        String[][] nightHall = new String[row][column];
        char a = 'A';
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {

                morningHall[i][j] = a + "-" + (j + 1) + "::AV";
                afterHall[i][j] = a + "-"  + (j + 1) + "::AV";
                nightHall[i][j] = a + "-"  + (j + 1) + "::AV";
            }
            a++;
        }

        char op;
        do {
            line(60,'-');
            menu();
            line(60,'-');

            do {
                Scanner scanner = new Scanner(System.in);
                System.out.print("> Please select menu no: ");
                String input = scanner.nextLine();
                if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                    op = Character.toUpperCase(input.charAt(0));
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a single letter.");
                }
            } while (true);

            switch (op) {
                case 'A':
                    line(60, '-');
                    System.out.println("# BOOKING");
                    line(60, '-');
                    Showtime();
                    line(60, '-');
                    Character showOp;
                    do {
                        System.out.print("> Please select show time (A | B | C): ");
                        String input = new Scanner(System.in).next().trim();
                        if (input.matches("[a-cA-C]")) {
                            showOp = input.toUpperCase().charAt(0);
                            break;
                        } else {
                            System.out.println("Invalid input.");
                        }
                    } while (true);
                    if (showOp.equals('A')) {
                        System.out.println("Morning");
                        booking(morningHall);
                        line(60,'-');
                    }
                    else if (showOp.equals('B')) {
                        System.out.println("Afternoon");
                        booking(afterHall);
                        line(60,'-');
                    }
                    else if (showOp.equals('C')) {
                        System.out.println("Night");
                        booking(nightHall);
                        line(60,'-');
                        break;
                    }


                case 'B':
                    line(60, '-');
                    System.out.println("# Hall Information");
                    line(60, '-');
                    hall(morningHall, "Morning");
                    hall(afterHall, "Afternoon");
                    hall(nightHall, "Night");
                    break;
                case 'C':
                    Showtime();
                    break;
                case 'D':
                    char rebootAnswer;
                    do {
                        System.out.print("Are you sure to reboot? (Y/N): ");
                        String input = new Scanner(System.in).next().trim().toUpperCase();
                        if (input.matches("[YN]")) {
                            rebootAnswer = input.charAt(0);
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
                        }
                    } while (true);
                    if (rebootAnswer == 'Y') {
                        morningHall = rebootHall(row, column, 'A');
                        afterHall = rebootHall(row, column, 'A');
                        nightHall = rebootHall(row, column, 'A');
                        System.out.println("Reboot successfully!");
                        line(60,'-');
                    }
                    break;

                case 'E':
                    history(bookedHistory, bookingCount);
                    break;
                case 'F':
                    System.out.println(" \n\n THANK YOU FOR USING OUR SYSTEM .... <3 \n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Insert the valid option[A-F]!!");
            }
        } while (op != 'F');
    }
}