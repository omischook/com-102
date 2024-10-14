package code.COM102.assignment2_COM102;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Library {
    public static void main(String[] args) {
        start();
        end();
    }

    static Calendar cal = Calendar.getInstance();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static String userID;
    static String barcode;
    static String libraryName = "Dungannon town library";

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        // TODO Auto-generated method stub
        System.out.println("Welcome to " + libraryName + " Library System");
        System.out.println("Please enter your user ID:");
        userID = scanner.nextLine();
        //searches user file for user ID to check if they are allowed to operate system
        boolean userExists = FileManager.checkUser(userID);
        if (!userExists) {
            System.out.println("User not found");
            end();
        }
        //barcode is essential in almost every function available, so we cannot move on without it
        System.out.println("Please enter the barcode of the item you want operate on otherwise leave blank:");
        barcode = scanner.nextLine();
        if (FileManager.checkBarcode(barcode)) {
            System.out.println("Barcode authenticated successfully.");
        } else {
            System.out.println("The barcode does not exist in the file.");
            end();
        }
        //this will be used in the upcoming switch case operation
        System.out.println("Please enter the operation you would like to perform (issue, renew, return, view, report):");
        String operation = scanner.nextLine();
        operation = operation.toLowerCase().trim(); // Convert to lowercase and remove leading/trailing spaces
        
        /* -------------------------------------------------------------------------- */
        /*                     SELECT OPERATION  AND ISSUE LOAN                       */
        /* -------------------------------------------------------------------------- */
        //switch case to determine which operation the user wants to perform
        //we use a switch case because it is better for multi-branch comparison
        switch (operation) {

            /* -------------------------------------------------------------------------- */
            /*                                 ISSUE CASE                                 */
            /* -------------------------------------------------------------------------- */

            case "issue":
            //reads the item from the file based on the barcode provided earlier
            Item item = FileManager.readItems(barcode);
            // this makes a loan object and then the issueLoan function runs a fileManager function to write the loan to the file
            Loan.issueLoan(item, userID);
            break;

            /* -------------------------------------------------------------------------- */
            /*                                 RENEW CASE                                 */
            /* -------------------------------------------------------------------------- */

            case "renew":
                //reads the loan from the file based on the barcode provided earlier
                Loan userLoan = FileManager.readLoans(barcode);
                if (userLoan == null) {
                    end();
                }
                //this deletes the function from the file and then rewrites it with the new due date
                //in other words it calls some fileManager methods inside the renew method
                Loan.renew(userLoan, barcode, userID);
                break;
            /* -------------------------------------------------------------------------- */
            /*                              RETURN ITEM CASE                              */
            /* -------------------------------------------------------------------------- */
            case "return":
                //runs the deleteLoan fileManager function
                Loan.returnLoan(barcode, userID);
            //Code for returning a book
                break;
            /* -------------------------------------------------------------------------- */
            /*                             VIEW ITEMS OR LOANS                            */
            /* -------------------------------------------------------------------------- */
            case "view":
                //this code requires some user input to determine what to view
                FileManager.readAll(barcode, userID);
                // Code for viewing books
                break;
            /* -------------------------------------------------------------------------- */
            /*                                   Report                                   */
            /* -------------------------------------------------------------------------- */
            case "report":
                FileManager.generateReport(libraryName);
                // Code for generating a report
                break;

            default:
                //if the user enters an invalid operation or nothing at all
                System.out.println("Invalid operation");
        }
    }


    //this is a yes no for whether or not to restart the program we do this to ensure that the files are written to and updated
    //so that when the program is re-run everything is up to date
    public static void end() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to continue using the system? (y/n)");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("y")) {
            Library.main(new String[]{}); // Restart the program
        } else {
            System.out.println("The system will now shut down.");
            System.exit(0);
        }
    }
}
