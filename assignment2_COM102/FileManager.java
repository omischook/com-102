package code.COM102.assignment2_COM102;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


class FileManager {

    public static boolean checkBarcode(String barcode) {
        try {
            File file = new File("Text/Items.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(barcode)) {
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    // this method reads the items file and returns either a book or multimedia objects which are subclasses of Item
    public static Item readItems(String barcode) {
        try {
            File file = new File("Text/Items.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            ArrayList<String> item = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.contains(barcode)) {
                    item.add(line);
                    break; // Assuming there is only one line with the correct barcode
                }
            }
            System.out.println(item);
            br.close();
            
            // Process the item ArrayList as needed
            // ...

            for (String itemLine : item) {
                String[] fields = itemLine.split(",");
                String type = fields[3]; // Assuming the type is the fourth field

                if (type.equals("Book")) {
                    Book book = new Book(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
                    return book;
                    // Process the book object as needed
                } else if (type.equals("Multimedia")) {
                    Multimedia multimedia = new Multimedia(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
                    return multimedia;
                    // Process the multimedia object as needed
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Loan readLoans(String barcode) {
        try (BufferedReader br = new BufferedReader(new FileReader("Text/Loans.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 6 && fields[0].trim().equals(barcode.trim())) {// Ensure that all necessary fields are present in the CSV
                    System.out.println("Loan found: " + line);
                    // Parse the 5th field (index 4) as an integer
                    int numRenews = Integer.parseInt(fields[5].trim());
                    return new Loan(fields[0], fields[1], fields[2], fields[3], fields[4], numRenews);
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Handle or propagate the exception based on your application's requirements
            e.printStackTrace();
        }
        System.out.println("Loan not found");
        return null; // Return null if loan is not found or error occurs
    }


    public static boolean checkUser(String userID) {
        // Check if userID is null or empty
        if (userID == null || userID.trim().isEmpty()) {
            return false;
        }

        try {
            System.out.println("Checking userID");
            File file = new File("Text/Users.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(userID)) {
                    System.out.println("user is authorised");
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void writeLoans(Loan newLoan) {
        
        try {
    //public Loan(String barcode, String userID, String type, String issueDate, String dueDate, int numRenews)
            FileWriter writer = new FileWriter("Text/Loans.csv", true);
            writer.write(newLoan.getBarcode() + "," + newLoan.getUserID() + "," + newLoan.getType() + "," + newLoan.getIssueDate() + "," + newLoan.getDueDate() + "," + newLoan.getNumRenews() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void renewLoan(String barcode, String userID) {
        try {
            File file = new File("Text/Loans.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            Loan loan = null;
            //! public Loan(String barcode, String userID, String type, String issueDate, String dueDate, int numRenews) 
            while ((line = br.readLine()) != null) {
                if (line.contains(barcode) && line.contains(userID)) {
                    String[] fields = line.split(",");
                    String loanBarcode = fields[0];
                    String loanUserID = fields[1];
                    String loanType = fields[2];
                    String issueDate = fields[3];
                    String dueDate = fields[4];
                    int numRenews = Integer.parseInt(fields[5]);

                    loan = new Loan(loanBarcode, loanUserID, loanType, issueDate, dueDate, 0);
                    Loan.renew(loan, barcode, userID);
                    break; // Assuming there is only one line with the correct barcode and userID
                }
            }

            deleteLoan(barcode, userID);
            


            br.close();

            // Process the loanInfo ArrayList as needed
            // ...

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLoan(String barcode, String userID) {
        //TODO delete a loan from the loans file
        try {
            File loansFile = new File("Text/Loans.csv");
            File tempFile = new File("Text/tempLoans.csv");

            BufferedReader br = new BufferedReader(new FileReader(loansFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            String line;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String loanBarcode = fields[0];
                String loanUserID = fields[1];


                if (!loanBarcode.equals(barcode) || !loanUserID.equals(userID)) {
                    //System.out.println("we are writing the line to the temp file");
                    bw.write(line);
                    bw.newLine();
                }
            }

            br.close();
            bw.close();
            loansFile.delete();
            tempFile.renameTo(loansFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void readAll(String barcode, String userID){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file you want to read (items or loans): ");
        String fileType = scanner.nextLine();
        System.out.print("how much information do you want to see (all or one): ");
        String quantity = scanner.nextLine();
        fileType = fileType.toLowerCase().trim(); // Convert to lowercase and remove leading/trailing spaces
        quantity = quantity.toLowerCase().trim(); // Convert to lowercase and remove leading/trailing spaces
        System.out.println(fileType + " " + quantity + "we have reached line 184");
        if (quantity.equals("all")) {
            
            if (fileType.equals("items")) {
                File itemsFile = new File("Text/Items.csv");
                try {
                    BufferedReader br = new BufferedReader(new FileReader(itemsFile));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
            
            else if (fileType.equals("loans")) {
                File itemsFile = new File("Text/Loans.csv");
                try {
                    BufferedReader br = new BufferedReader(new FileReader(itemsFile));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } 
        
        else if (quantity.equals("one")) {
            if (fileType.equals("items")) {
                readItems(barcode);
            } 
            
            else if (fileType.equals("loans")) {
                readLoans(barcode);
            }
        }
        else {
            System.out.println("Invalid input");
        }
    }

    public static void generateReport(String libraryName) {
        int lineCount = 0;
        int bookCount = 0;
        int multimediaCount = 0;
        int removedCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("Text/Loans.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] words = line.split(",");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    if (word.equals("Book")) {
                        bookCount++;
                    } else if (word.equals("Multimedia")) {
                        multimediaCount++;
                    }
                    // Check if the last field on each line is greater than 0
                    if (i == words.length - 1 && Integer.parseInt(word) > 0) {
                        removedCount++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double renewedPercentage = (double) removedCount / lineCount * 100;
        System.out.println("Library: " + libraryName);
        System.out.println("Number of lines: " + lineCount);
        System.out.println("Number of 'Book': " + bookCount);
        System.out.println("Number of 'Multimedia': " + multimediaCount);
        System.out.println("Number of items renewed: " + removedCount);
        System.out.println("Percentage of loans that have been renewed: " + renewedPercentage + "%");

    }
}
