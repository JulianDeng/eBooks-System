package model;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class AnalyticsModel {
	
	
	
	public AnalyticsModel() {
		super();
	}
	
	public void exportVisitReport(ReportWrapper report, String filename) throws Exception{
		
		String path = filename.substring(0, filename.lastIndexOf(File.separator));
		
		JAXBContext context = JAXBContext.newInstance(report.getClass());
		Marshaller ms = context.createMarshaller();
	    ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    ms.setProperty(Marshaller.JAXB_FRAGMENT, true);
	    
	    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    String filepath = path+File.separator+"analytics.xsd";
	    Schema schema = sf.newSchema(new File(filepath));
	    ms.setSchema(schema);
		
	    StringWriter sw = new StringWriter();
	    sw.write("<?xml version='1.0'?>\n");
	    ms.marshal(report, sw);
	    System.out.println(sw.toString());
	    FileWriter fw = new FileWriter(filename);
	    fw.write(sw.toString());
	    fw.close();
	}
	
}
