package restService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.BookDAO;
import dao.BookDAOImpl;
import model.Book;
import model.Category;

@Path("Books") // this is the path of the service
public class BookService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBooks() {
		
		List<Book> bookList = null;
		
		try {
			// calling DAO method to retrieve a list of all books 
			BookDAO bd = new BookDAOImpl();
			bookList = bd.findAllBooks();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return bookList;
	}
	
	@Path("/category")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories() {
		
		BookDAO bookDao = new BookDAOImpl();
		List<Category> categoryList = bookDao.findAllCategories();
		return categoryList;
	}
	
	@Path("/searchByCat/{catid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> searchByCat(@PathParam("catid") String catid) {
		
		List<Book> bookList = null;
		
		try {
			// calling DAO method to search book by catetory 
			BookDAO bd = new BookDAOImpl();
			
			bookList = bd.findBooksByCategory(catid);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return bookList;
	}
	
	@Path("/searchByKey/{keyword}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> searchByKey(@PathParam("keyword") String keyword) {
		
		List<Book> bookList = null;
		
		try {
			// calling DAO method to search book by catetory 
			BookDAO bd = new BookDAOImpl();
			
			bookList = bd.searchBooksByKeyword(keyword);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return bookList;
	}
}
