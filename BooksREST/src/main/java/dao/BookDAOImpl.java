package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Author;
import model.Book;
import model.Category;

public class BookDAOImpl implements BookDAO {

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException ex) {
		}
	}

	// complete this method
	private Connection getConnection() throws SQLException {
		
		 return DriverManager.getConnection("jdbc:sqlite:/Users/riley/Desktop/Books.db");
	}

	private void closeConnection(Connection connection) {
		if (connection == null)
			return;
		try {
			connection.close();
		} catch (SQLException ex) {
		}
	}

	//complete this method
	public List<Book> findAllBooks() {
		List<Book> result = new ArrayList<Book>();
		
        // join 3 tables to get needed info
		String sql = "select * from book inner join author, category on book.id = author.book_id and book.category_id = category.id";
				
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				
				Book book = new Book();
				Author author = new Author();
				
				// populate book and author beans with needed info, and then set author to book
				book.setId(resultSet.getLong("book_id"));
				//System.out.println(book.getId());
				book.setCategoryId(resultSet.getLong("category_id"));
				book.setBookTitle(resultSet.getString("book_title"));
				book.setCategory(resultSet.getString("category_description"));
				
				author.setId(book.getId());
				author.setBookId(book.getId());
				author.setFirstName(resultSet.getString("first_name"));
				author.setLastName(resultSet.getString("last_name"));
				//System.out.println(author.getFirstName());
				
				book.setAuthor(author);
				 				
				result.add(book);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return result;
	}

	// complete this method
	@Override
	public List<Book> searchBooksByKeyword(String keyWord) {
		List<Book> result = new ArrayList<Book>();
		
		String sql = "select * from book inner join author, category on book.id = author.book_id and book.category_id = category.id"
				+ " where book_title like '%"
				+ keyWord.trim()
				+ "%'"
				+ " or first_name like '%"
				+ keyWord.trim()
				+ "%'"
				+ " or last_name like '%" + keyWord.trim() + "%'";

		Connection connection = null;
		try {

			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Book book = new Book();
				Author author = new Author();
                
				// populate book and author with needed info, and then set author to book
				
				book.setId(resultSet.getLong("book_id"));
				book.setCategoryId(resultSet.getLong("category_id"));
				book.setBookTitle(resultSet.getString("book_title"));
				book.setCategory(resultSet.getString("category_description"));
				
				author.setId(book.getId());
				author.setBookId(book.getId());
				author.setFirstName(resultSet.getString("first_name"));
				author.setLastName(resultSet.getString("last_name"));
				
				book.setAuthor(author);
				
				result.add(book);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return result;
	}

	// complete this method
	public List<Category> findAllCategories() {
		List<Category> result = new ArrayList<>();
		String sql = "select * from category";

		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Category category = new Category();
				
				// populate category bean with needed info
                category.setId(resultSet.getLong("id"));
                category.setCategoryDescription(resultSet.getString("category_description"));

				result.add(category);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return result;
	}

	
	
	
	public List<Book> findBooksByCategory(String category) {
		List<Book> result = new ArrayList<Book>();
		 

		String sql = "select * from book inner join author, category on book.id = author.book_id and book.category_id = category.id where "
				+ "CATEGORY_DESCRIPTION='" + category + "'";

		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Book book = new Book();
				Author author = new Author();
				
				// populate book and author beans with needed info, and then set author to book
				book.setId(resultSet.getLong("book_id"));
				book.setCategoryId(resultSet.getLong("category_id"));
				book.setBookTitle(resultSet.getString("book_title"));
				book.setCategory(resultSet.getString("category_description"));
				
				author.setId(book.getId());
				author.setBookId(book.getId());
				author.setFirstName(resultSet.getString("first_name"));
				author.setLastName(resultSet.getString("last_name"));
				
				book.setAuthor(author);
				
				result.add(book);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return result;
	}
	
	
	public void insert(Book book) {
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"insert into Book (book_title) values (?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, book.getBookTitle());
			statement.execute();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				book.setId(generatedKeys.getLong(1));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	
	public void delete(Long bookId) {
		Connection connection = null;

		try {
			connection = getConnection();
			PreparedStatement statement = connection
					.prepareStatement("delete from book where id=?");
			statement.setLong(1, bookId);
			statement.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	

}