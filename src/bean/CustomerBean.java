package bean;

import javax.xml.bind.annotation.*;

@XmlType(propOrder={"uid", "lname", "fname"})
public class CustomerBean {
	@XmlElement
	private String uid;
	private String username;
	private String password;
	@XmlElement(name="lastname")
	private String lname;
	@XmlElement(name="firstname")
	private String fname;
	
	public CustomerBean() {
		super();
	}

	public CustomerBean(String uid, String username, String password, String lname, String fname) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.lname = lname;
		this.fname = fname;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	
	
}
