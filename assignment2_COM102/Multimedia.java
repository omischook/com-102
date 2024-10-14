package code.COM102.assignment2_COM102;

import java.util.Calendar;
import java.util.Date;

import static code.COM102.assignment2_COM102.Library.sdf;

public class Multimedia extends Item {
    
    public Multimedia(String barcode, String author, String title, String type, String year,
            String isbn) {
        super(barcode, author, title, type, year, isbn);
    }
    @Override
    public String getDueDate() {
        // Create a Calendar object and set it to the current date
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // Add 4 weeks to the current date
        cal.add(Calendar.DAY_OF_MONTH, 7);

        // Format the date and return it as a string
        return sdf.format(cal.getTime());
    }

    public static String getRenewDate() {
        // Create a Calendar object and set it to the current date
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // Add 2 weeks to the current date
        cal.add(Calendar.DAY_OF_MONTH, 7);

        // Format the date and return it as a string
        return sdf.format(cal.getTime());
    }
}