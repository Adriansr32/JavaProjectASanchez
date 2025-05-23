package controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.Book;
import model.User;
import util.DBUtils;

public class LoanController {
	
	private Book currentBook;
    private MainController mainController; 
    private DatePicker datePicker;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    public void setLoan(Book book) {
    	currentBook = book;
    }
    
    public void handleLoanButton() {
    	Book book = currentBook;
    	LocalDate date = datePicker.getValue();	
    	User user = mainController.getLoggedUser(); 
   	try {
   		boolean loan = DBUtils.createLoan(user.getUserId(), book.getBookId());
    	
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
}
