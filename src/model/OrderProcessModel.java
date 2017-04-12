package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
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
	
	public OrderWrapper getOrder(String orderId) throws SQLException{
		return orderDao.getOrder(orderId);
	}
	
	public String exportOrder(OrderWrapper wrapper, ServletContext context) throws Exception{
		JAXBContext jc = JAXBContext.newInstance(wrapper.getClass());
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	    
	    
	    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    
	    String filepath = context.getRealPath(File.separator + "analytics" + File.separator + "exportOrder.xsd");
	    System.out.println("filepath" + filepath);
	    Schema schema = sf.newSchema(new File(filepath));
	    marshaller.setSchema(schema);
	    
	    StringWriter sw = new StringWriter();
	    sw.write("<?xml version='1.0'?>\n");
	    sw.write("<?xml-stylesheet type=\"text/xsl\" href=\""+context.getRealPath(File.separator)+File.separator+"analytics"+File.separator+"order.xsl\"?>\n");
	    marshaller.marshal(wrapper, new StreamResult(sw));
	    return sw.toString();
	}
	
}
