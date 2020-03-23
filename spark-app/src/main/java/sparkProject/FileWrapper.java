package sparkProject;

import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employees")
public class FileWrapper {
	private LinkedHashSet<Employee> employees;

	public LinkedHashSet<Employee> getEmployees() {
		return employees;
	}

	@XmlElement(name = "employee")
	public void setEmployees(LinkedHashSet<Employee> employees) {
		this.employees = employees;
	}

}