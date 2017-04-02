package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="bookReport")
public class ReportWrapper {
	@XmlElement(name="generateAt")
	private String reportDate;
	@XmlElement
	private String visitType;
	@XmlElement(name="monthlyReport")
	private ArrayList<MonthlyReportWrapper> elements;
	@XmlAttribute
	private String monthFrom;
	@XmlAttribute
	private String monthEnd;
	
	public ReportWrapper(){
		
	}
	
	public ReportWrapper(String reportDate, String visitType, String monthFrom, String monthEnd,
			ArrayList<MonthlyReportWrapper> elements) {
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

	public ArrayList<MonthlyReportWrapper> getElements() {
		return elements;
	}
	
	
}
