package bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"uid", "fname", "lname", "total_spend"})
public class AnnomizedReportBean {
	@XmlElement
	private int uid;
	@XmlElement(name="firstName")
	private String fname;
	@XmlElement(name="lastName")
	private String lname;
	@XmlElement
	private int total_spend;
	
	public AnnomizedReportBean(int uid, String fname, String lname, int total_spend) {
		super();
		this.uid = uid;
		this.fname = fname;
		this.lname = lname;
		this.total_spend = total_spend;
	}
	@XmlTransient
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	@XmlTransient
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	@XmlTransient
	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}
	@XmlTransient
	public int getTotal_spend() {
		return total_spend;
	}

	public void setTotal_spend(int total_spend) {
		this.total_spend = total_spend;
	}
}
