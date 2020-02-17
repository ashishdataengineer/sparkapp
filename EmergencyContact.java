package sparkProject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmergencyContact {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("phone")
	@Expose
	private String phone;
	@SerializedName("relationship")
	@Expose
	private String relationship;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

}