package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import bean.AnnomizedReportBean;

@XmlRootElement(name="annomizedReport")
public class AnnomizedReportWrapper {
	@XmlElement
	private String generateDate;
	@XmlElement
	private ArrayList<AnnomizedReportBean> annomizedUser;
	@XmlAttribute
	private String orderBy;
	@XmlAttribute
	private String order;
	
	public AnnomizedReportWrapper() {
		super();
	}

	public AnnomizedReportWrapper(String generateDate, ArrayList<AnnomizedReportBean> annomizedUser, String orderBy, String order) {
		super();
		this.generateDate = generateDate;
		this.annomizedUser = annomizedUser;
		this.orderBy = orderBy;
		this.order = order;
	}

	public String getGenerateDate() {
		return generateDate;
	}

	public ArrayList<AnnomizedReportBean> getAnnomizedUser() {
		return annomizedUser;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public String getOrder() {
		return order;
	}
	
	
	
}
