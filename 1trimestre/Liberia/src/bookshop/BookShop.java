package bookshop;

import java.sql.SQLException;
import java.util.List;

public class BookShop {

	public static void main(String[] args) {
            if (args.length < 1) {
                error();
            }
	    try (DBManager manager = new DBManager()) {
	        if (args[0].equals("search")) {
	            if (args.length < 2) {
	                error();
	            }
	            String isbn = args[1];
	            Book book = manager.searchBook(isbn);
	            if (book != null) {
	                System.out.println(book);
	            } else {
	                System.out.println("Book not found: " + isbn);
	            }
	        } else if (args[0].equals("sell")) {
	            if (args.length < 3) {
	                error();
	            }
	            String isbn = args[1];
	            int units = Integer.parseInt(args[2]);
	            Book book = manager.searchBook(isbn);
	            if (book != null) {
	                boolean success = manager.sellBook(book, units);
	                if (success) {
	                    System.out.println("Book sold: " + units + "x "
                                               + book);
	                } else {
	                    System.out.println("The book could not be sold: "
	                            + units + "x " + book);
	                }
	            } else {
	                System.out.println("Book not found: " + isbn);
	            }
	        } else if (args[0].equals("stock")) {
	            if (args.length < 2) {
	                error();
	            }
	            String isbn = args[1];
	            Book book = manager.searchBook(isbn);
	            if (book != null) {
	                int units = manager.getStock(book);
	                System.out.println("There are " + units
                                           + " copies of " + book);
	            } else {
	                System.out.println("Book not found: " + isbn);
	            }
	        } else if (args[0].equals("list")) {
	            if (args.length > 1) {
	                error();
	            }
	            List<Book> books = manager.listBooks();
	            for (Book libro: books) {
	               System.out.println(libro);
	            }
	        }
	    } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
	}

	public static void error() {
		System.err.println("Wrong command-line arguments. Use:");
		System.err.println("java bookshop.BookShop search <isbn>");
		System.err.println("java bookshop.BookShop stock <isbn>");
		System.err.println("java bookshop.BookShop sell "
                                   + "<isbn> <units>");
		System.err.println("java bookshop.BookShop list");
		System.exit(1);
	}
}