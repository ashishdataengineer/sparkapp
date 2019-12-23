package sparkProject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {

	@SerializedName("id")
	@Expose
	private Integer id;
	
	@SerializedName("name")
	@Expose
	private String name;
	
	@SerializedName("phones")
	@Expose
	private Phones phones;
	
	@SerializedName("email")
	@Expose
	private List<String> email = null;
	
	@SerializedName("dateOfBirth")
	@Expose
	private String dateOfBirth;
	
	@SerializedName("registered")
	@Expose
	private Boolean registered;
	
	@SerializedName("emergencyContacts")
	@Expose
	private List<EmergencyContact> emergencyContacts = null;
	
	@SerializedName("InputXMLPath")
	@Expose
	private String InputXMLPath;
	
	@SerializedName("XMLXsltPath")
	@Expose
	private String XMLXsltPath;
	
	@SerializedName("TransformedXMLOutputPath")
	@Expose
	private String TransformedXMLOutputPath;
	
	@SerializedName("TransformedJSONPath")
	@Expose
	private String TransformedJSONPath;	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInputXMLPath() {
		return InputXMLPath;
	}
	
	public String getXMLXsltPath() {
		return XMLXsltPath;
	}
	
	public String getTransformedXMLOutputPath() {
		return TransformedXMLOutputPath;
	}
	
	
	public String getTransformedJSONPath() {
		return TransformedJSONPath;
	}
	
	public void setInputXMLPath(String InputXMLPath) {
		this.InputXMLPath = InputXMLPath;
	}
	
	public void setTransformedJSONPath(String TransformedJSONPath) {
		this.TransformedJSONPath = TransformedJSONPath;
	}
	
	
	public void setXMLXsltPath(String XMLXsltPath) {
		this.XMLXsltPath = XMLXsltPath;
	}
	
	public void setTransformedXMLOutputPath(String TransformedXMLOutputPath) {
		this.TransformedXMLOutputPath = TransformedXMLOutputPath;
	}
	

	public void setPhones(Phones phones) {
		this.phones = phones;
	}
	
	public Phones getPhones() {
		return phones;
	}

	public List<String> getEmail() {
		return email;
	}

	public void setEmail(List<String> email) {
		this.email = email;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Boolean getRegistered() {
		return registered;
	}

	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}

	public List<EmergencyContact> getEmergencyContacts() {
		return emergencyContacts;
	}

	public void setEmergencyContacts(List<EmergencyContact> emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}

}
