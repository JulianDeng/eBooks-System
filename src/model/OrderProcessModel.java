package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import DAO.PurchaseOrderDAO;

public class OrderProcessModel {
	private PurchaseOrderDAO orderDao;
	
	public OrderProcessModel() throws ClassNotFoundException{
		orderDao = new PurchaseOrderDAO();
	}
	
	public OrderWrapper getAllOrder() throws SQLException{
		return orderDao.getAllOrder();
	}
	
	public String exportOrder(OrderWrapper wrapper) throws Exception{
		JAXBContext jc = JAXBContext.newInstance(wrapper.getClass());
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	    
	    
//	    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//	    Schema schema = sf.newSchema(new File(path+File.separator+"exportOrder.xsd"));
//	    marshaller.setSchema(schema);
	    
	    StringWriter sw = new StringWriter();
	    sw.write("<?xml version='1.0'?>\n");
	    marshaller.marshal(wrapper, new StreamResult(sw));
	    return sw.toString();
	}
	
}
