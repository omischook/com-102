package code.COM102.assignment2_COM102;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Loan {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String barcode;
    private String userID;
    private String issueDate;
    private String dueDate;
    private int numRenews;
    private static String type;

    public Loan(String barcode, String userID, String type, String issueDate, String dueDate, int numRenews) {
        this.barcode = barcode;
        this.userID = userID;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.numRenews = numRenews;
        this.type = type;
    }
    /* ------------------------------- CONSTRUCTOR ------------------------------ */
    public static void issueLoan(Item item, String userID){
        if (item == null) {
            System.out.println("wrong barcode entered");
            return;
        }

        String dueDate = item.getDueDate();
        Loan newLoan = new Loan(item.getBarcode(), userID, item.getType(), sdf.format(new Date()), dueDate, 0);

        System.out.println(newLoan.getBarcode() + " " + newLoan.getUserID() + " " + newLoan.getType() + " " + newLoan.getIssueDate() + " " + newLoan.getDueDate() + " " + newLoan.getNumRenews());
        FileManager.writeLoans(newLoan);
    }
    /**
 * The `renew` method is responsible for renewing a loan. It takes a `Loan` object as a parameter and updates its due date based on the loan type and the number of times it has been renewed.
 * If the loan type is "book" and the number of renewals is less than 3, the method extends the due date by two weeks and increments the `numRenews` counter. The updated due date is formatted using the `sdf` SimpleDateFormat object.
 * If the loan type is "multimedia" and the number of renewals is less than 2, the method extends the due date by an implementation-specific duration for multimedia loans. The `numRenews` counter is also incremented.
 * It's important to note that the `renew` method assumes that the `Loan` object passed as a parameter is valid and has a valid loan type. If the loan type is neither "book" nor "multimedia", the method does not perform any actions.
 */
    public static void renew(Loan loan, String barcode, String userID) {
        System.out.println("inside of the renew function");
        Boolean renew = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //System.out.println("Loan Details: \nBarcode: " + loan.getBarcode() + "\nUser ID: " + loan.getUserID() + "\nType: " + loan.getType() + "\nIssue Date: " + loan.getIssueDate() + "\nDue Date: " + loan.getDueDate() + "\nNumber of Renewals: " + loan.getNumRenews());
        if (loan.getType().equals("Book") && loan.numRenews < 3) {
            // Code for renewing a book loan
            loan.numRenews++;
            loan.dueDate = Book.getRenewDate();
            renew = true;
        } else if (loan.getType().equals("Multimedia") && loan.numRenews < 2) {
            // Code for renewing a multimedia loan
            loan.numRenews++;
            loan.dueDate = Multimedia.getRenewDate();
            renew = true;
        } else {
            System.out.println("Invalid loan type or maximum renewals reached.");
        }

        if (renew == true) {
            try {
                System.out.println("barcode: " + barcode + " userID: " + userID);
                FileManager.deleteLoan(barcode, userID);
                System.out.println("Loan deleted alert loan deleted");
                FileManager.writeLoans(loan);
                System.out.println("Loan renewed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void returnLoan(String barcode, String userID){
        FileManager.deleteLoan(barcode, userID);
        System.out.println("Loan returned");
    }

    public static void removeLoan(){
        System.out.println("Loan removed");
    }


    public String getBarcode() {
        return this.barcode;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getType() {
        return this.type;
    }

    public String getIssueDate() {
        return this.issueDate;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public String getNumRenews() {
        return Integer.toString(this.numRenews);
    }

}
