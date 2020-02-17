package sparkProject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonMainBody {

	@SerializedName("product")
	@Expose
	private String product;
	@SerializedName("version")
	@Expose
	private Float version;
	@SerializedName("releaseDate")
	@Expose
	private String releaseDate;
	@SerializedName("demo")
	@Expose
	private Boolean demo;
	@SerializedName("person")
	@Expose
	private Person person;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Float getVersion() {
		return version;
	}

	public void setVersion(Float version) {
		this.version = version;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Boolean getDemo() {
		return demo;
	}

	public void setDemo(Boolean demo) {
		this.demo = demo;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
