package code.COM102.assignment2_COM102;

public abstract class Item {
    String barcode;
    String author;
    String title;
    String type;
    String year;
    String isbn;

    public Item(String barcode, String author, String title, String type, String year, String isbn) {
        this.barcode = barcode;
        this.author = author;
        this.title = title;
        this.type = type;
        this.year = year;
        this.isbn = isbn;
    }

    public Item(String title2, double price) {
        //TODO Auto-generated constructor stub
    }

    public static void getItemType(String barcode) {
        //TODO Auto-generated method stub
        

    }

    public String getDueDate() {
        System.out.println("we are using the wrong function");
        return null;
    }

    public static String getRenewDate(){
        return "";
    };

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public String isType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
}
