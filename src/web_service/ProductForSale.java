package web_service;


import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import bean.BookBean;
import model.BookAccessModel;
import model.BookListWrapper;

public class ProductForSale {
	
	BookAccessModel bookModel;
	
	public ProductForSale() throws ClassNotFoundException{
		bookModel = new BookAccessModel();
	}
	
	public String getProductInfo(String productId){
		String info="The product "+productId+" is not exist";

		try {
			BookBean result = bookModel.getBookById(productId);
			ArrayList<BookBean> list = new ArrayList<BookBean>();
			list.add(result);
			BookListWrapper listwrapper = new BookListWrapper(list);
			JAXBContext context = JAXBContext.newInstance(listwrapper.getClass());
			Marshaller ms = context.createMarshaller();
			
		    ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    ms.setProperty(Marshaller.JAXB_FRAGMENT, true);
		    
		    StringWriter sw = new StringWriter();
		    sw.write("<?xml version='1.0'?>\n");
		    
			if(result != null){
				ms.marshal(listwrapper, sw);
				info=sw.toString();
			}
		} catch (SQLException | JAXBException e) {
			e.printStackTrace();
		}
		return info;
	}
}
