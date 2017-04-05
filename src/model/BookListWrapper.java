package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

import bean.BookBean;

@XmlRootElement(name="list_Of_Books")
public class BookListWrapper {
	@XmlElement(name="book_Info")
	private ArrayList<BookBean> items;
	
	public BookListWrapper() {
		super();
	}
	
	public BookListWrapper(ArrayList<BookBean> items) {
		super();
		this.items = items;
	}
	
	public ArrayList<BookBean> getItems() {
		return items;
	}
}
