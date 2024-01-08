package bookshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.*;


public class DBManager implements AutoCloseable {

    private Connection connection;
    Statement st; 
	ResultSet rs;

    public DBManager() throws SQLException {
        connect();
    }

    private void connect() throws SQLException {
        // TODO: program this method
    	try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	connection = DriverManager.getConnection("jdbc:mysql://localhost/libreria", "root", "admin"); 
    }

    /**
     * Close the connection to the database if it is still open.
     *
     */
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
    }

    /**
     * Return the number of units in stock of the given book.
     *
     * @param book The book object.
     * @return The number of units in stock, or 0 if the book does not
     *         exist in the database.
     * @throws SQLException If somthing fails with the DB.
     */
    public int getStock(Book book) throws SQLException {
        return getStock(book.getId());
    }

    /**
     * Return the number of units in stock of the given book.
     *
     * @param bookId The book identifier in the database.
     * @return The number of units in stock, or 0 if the book does not
     *         exist in the database.
     */
    public int getStock(int bookId) throws SQLException {
        // TODO: program this method
    	st =connection.createStatement();
    	rs = st.executeQuery("select numerounidades from existencias where id='"+bookId+"'"); 
    	int unidades=0;
    	try {
    		while (rs.next()) {
    		unidades=rs.getInt("numerounidades");
    			}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
        return unidades;
    }

    /**
     * Search book by ISBN.
     *
     * @param isbn The ISBN of the book.
     * @return The Book object, or null if not found.
     * @throws SQLException If somthing fails with the DB.
     */
    public Book searchBook(String isbn) throws SQLException {
        // TODO: program this method
    	st =connection.createStatement();
    	rs = st.executeQuery("select * from libros where isbn='"+isbn+"'"); 
    	Book resultado=null;
    	try {
    		while (rs.next()) {
    		int id=rs.getInt("id");
    		String titulo=rs.getString("titulo");
    		String isbnLibro=rs.getString("isbn");
    		String editorial=rs.getString("editorial");
    		int anho=rs.getInt("anho");
    		int precio=rs.getInt("precio");
    		String autores=rs.getString("autores");
    		resultado= new Book();
    		resultado.setId(id);
    		resultado.setIsbn(isbnLibro);
    		resultado.setYear(anho);
    		resultado.setTitle(titulo);
    			}
    		
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
        return resultado;
    }

    /**
     * Sell a book.
     *
     * @param book The book.
     * @param units Number of units that are being sold.
     * @return True if the operation succeeds, or false otherwise
     *         (e.g. when the stock of the book is not big enough).
     * @throws SQLException If somthing fails with the DB.
     */
    public boolean sellBook(Book book, int units) throws SQLException {
        return sellBook(book.getId(), units);
    }

    /**
     * Sell a book.
     *
     * @param book The book's identifier.
     * @param units Number of units that are being sold.
     * @return True if the operation succeeds, or false otherwise
     *         (e.g. when the stock of the book is not big enough).
     * @throws SQLException If something fails with the DB.
     */
    public boolean sellBook(int book, int units) throws SQLException {
        // TODO: program this method
    	boolean ventaRealizada=false;
    	Random random=new Random();
    	int stock=getStock(book);
    	if(stock-units<0) {
    		
    	}else
    	try{
    		Statement s1 = connection.createStatement(); 
    		s1.executeUpdate("update existencias set numerounidades = numerounidades-"+units+" where id='"+book+"'");
			s1 = connection.createStatement();
			s1.executeUpdate("insert into operaciones values("+random.nextInt()+",CURRENT_TIMESTAMP,'"+book+"',"+units+")");
			ventaRealizada=true;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        return ventaRealizada;
    }

    /**
     * Return a list with all the books in the database.
     *
     * @return List with all the books.
     * @throws SQLException If something fails with the DB.
     */
    public List<Book> listBooks() throws SQLException {
        // TODO: program this method
    	ArrayList<Book> lista=new ArrayList<Book>();
    	st =connection.createStatement();
    	rs = st.executeQuery("select * from libros"); 
    	Book resultado;
    	try {
    		while (rs.next()) {
    		int id=rs.getInt("id");
    		String titulo=rs.getString("titulo");
    		String isbnLibro=rs.getString("isbn");
    		String editorial=rs.getString("editorial");
    		int anho=rs.getInt("anho");
    		int precio=rs.getInt("precio");
    		String autores=rs.getString("autores");
    		resultado= new Book();
    		resultado.setId(id);
    		resultado.setIsbn(isbnLibro);
    		resultado.setYear(anho);
    		resultado.setTitle(titulo);
    		lista.add(resultado);
    			}
    	}catch(Exception e) {
			e.printStackTrace();
		}
    	
    	return lista;
    }
}