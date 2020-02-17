package sparkProject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class TestEntryPoint {
	
	public static void main(String[] args) throws JAXBException {
		

		
		JAXBContext jaxbContext = JAXBContext.newInstance(FileWrapper.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader("<employees>    <employee id=\"be129\">        <firstname>Jane</firstname>        <lastname>Doe</lastname>        <title>Engineer</title>        <division>Materials</division>        <building>327</building>        <room>19</room>        <supervisor>be131</supervisor>    </employee>    <employee id=\"be130\">        <firstname>William</firstname>        <lastname>Defoe</lastname>        <title>Accountant</title>        <division>Accts Payable</division>        <building>326</building>        <room>14a</room>    </employee>    <employee id=\"be131\">        <firstname>Jack</firstname>        <lastname>Dee</lastname>        <title>Engineering Manager</title>        <division>Materials</division>        <building>327</building>        <room>21</room>    </employee>    <employee id=\"be132\">        <firstname>Sandra</firstname>        <lastname>Rogers</lastname>        <title>Engineering</title>        <division>Materials</division>        <building>327</building>        <room>22</room>    </employee>    <employee id=\"be133\">        <firstname>Steve</firstname>        <lastname>Casey</lastname>        <title>Engineering</title>        <division>Materials</division>        <building>327</building>        <room>24</room>    </employee>    <employee id=\"be135\">        <firstname>Michelle</firstname>        <lastname>Michaels</lastname>        <title>COO</title>        <division>Management</division>        <building>216</building>        <room>264</room>    </employee></employees>");
		FileWrapper person = (FileWrapper) unmarshaller.unmarshal(reader);
		
		List<Employee> emp = new ArrayList<Employee>(person.getEmployees());
		
		System.out.println(emp.get(0).getFirstname());
	}

}
