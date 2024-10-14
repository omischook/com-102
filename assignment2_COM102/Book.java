package code.COM102.assignment2_COM102;
import code.COM102.assignment2_COM102.Item;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import static code.COM102.assignment2_COM102.Library.sdf;

public class Book extends Item {
    public Book(String barcode, String author, String title, String type, String year, String isbn) {
        super(barcode, author, title, type, year, isbn);
    }
    @Override
    public String getDueDate() {
        // Create a Calendar object and set it to the current date
        Calendar cal = Calendar.getInstance();

        // Add 4 weeks to the current date
        cal.add(Calendar.DAY_OF_MONTH, 28);

        // Format the date and return it as a string
        return sdf.format(cal.getTime());
    }

    public static String getRenewDate() {
        // Create a Calendar object and set it to the current date
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // Add 2 weeks to the current date
        cal.add(Calendar.DAY_OF_MONTH, 14);

        // Format the date and return it as a string
        return sdf.format(cal.getTime());
    }

}