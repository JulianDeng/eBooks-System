package model;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import DAO.CustomerDAO;
import bean.AnnomizedReportBean;

public class AnalyticsModel {
	
	private CustomerDAO customerDao;
	
	public AnalyticsModel() throws ClassNotFoundException {
		super();
		customerDao = new CustomerDAO();
	}
	
	public AnnomizedReportWrapper getAnnomizedReport(String generateDate, String orderBy, String order) throws SQLException{
		return customerDao.getSpendReport(generateDate, orderBy, order);
	}
	
	public void exportVisitReport(BookVisitReportWrapper report, String filename) throws Exception{
		
		String path = filename.substring(0, filename.lastIndexOf(File.separator));
		
		JAXBContext context = JAXBContext.newInstance(report.getClass());
		Marshaller ms = context.createMarshaller();
	    ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    ms.setProperty(Marshaller.JAXB_FRAGMENT, true);
	    
	    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    String filepath = path+File.separator+"bookVisitReport.xsd";
	    Schema schema = sf.newSchema(new File(filepath));
	    ms.setSchema(schema);
		
	    StringWriter sw = new StringWriter();
	    sw.write("<?xml version='1.0'?>\n");
	    sw.write("<?xml-stylesheet type=\"text/xsl\" href=\"bookSold.xsl\"?>\n");
	    ms.marshal(report, sw);
	    //System.out.println(sw.toString());
	    FileWriter fw = new FileWriter(filename);
	    fw.write(sw.toString());
	    fw.close();
	}
	
	public void exportAnnomizedReport(AnnomizedReportWrapper report, String filename) throws Exception{
		
		String path = filename.substring(0, filename.lastIndexOf(File.separator));
		
		JAXBContext context = JAXBContext.newInstance(report.getClass());
		Marshaller ms = context.createMarshaller();
	    ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    ms.setProperty(Marshaller.JAXB_FRAGMENT, true);
	    
	    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    String filepath = path+File.separator+"annomizedReport.xsd";
	    Schema schema = sf.newSchema(new File(filepath));
	    ms.setSchema(schema);
		
	    StringWriter sw = new StringWriter();
	    sw.write("<?xml version='1.0'?>\n");
	    sw.write("<?xml-stylesheet type=\"text/xsl\" href=\"annomized.xsl\"?>\n");
	    ms.marshal(report, sw);
	    //System.out.println(sw.toString());
	    FileWriter fw = new FileWriter(filename);
	    fw.write(sw.toString());
	    fw.close();
	}
	
	
}
