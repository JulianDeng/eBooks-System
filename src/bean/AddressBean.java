package bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={ "street", "province", "country", "zip", "phone"})
public class AddressBean {

	private int id;
	@XmlElement
	private String street;
	@XmlElement
	private String province;
	@XmlElement
	private String country;
	@XmlElement
	private String zip;
	@XmlElement
	private String phone;
	
	public AddressBean(){
		
	}
	public AddressBean(int id, String street, String province, String country, String zip, String phone) {
		this.id = id;
		this.street = street;
		this.province = province;
		this.country = country;
		this.zip = zip;
		this.phone = phone;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@XmlTransient
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	@XmlTransient
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	@XmlTransient
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@XmlTransient
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@XmlTransient
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
