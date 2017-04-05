package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="bookVisitReport")
public class BookVisitReportWrapper {
	@XmlElement(name="generateDate")
	private String reportDate;
	@XmlElement
	private String visitType;
	@XmlElement(name="monthlyVisitReport")
	private ArrayList<MonthlyVisitReportWrapper> elements;
	@XmlAttribute
	private String monthFrom;
	@XmlAttribute
	private String monthEnd;
	
	public BookVisitReportWrapper(){
		
	}
	
	public BookVisitReportWrapper(String reportDate, String visitType, String monthFrom, String monthEnd,
			ArrayList<MonthlyVisitReportWrapper> elements) {
		super();
		this.reportDate = reportDate;
		this.visitType = visitType;
		this.monthFrom = monthFrom;
		this.monthEnd = monthEnd;

		this.elements = elements;
	}

	public String getReportDate() {
		return reportDate;
	}

	public String getVisitType() {
		return visitType;
	}

	public String getMonthFrom() {
		return monthFrom;
	}

	public String getMonthEnd() {
		return monthEnd;
	}

	public ArrayList<MonthlyVisitReportWrapper> getElements() {
		return elements;
	}
	
	
}
