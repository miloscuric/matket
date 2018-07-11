package com.poslovna.market.controllers;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.poslovna.market.models.Company;
import com.poslovna.market.services.CompanyService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private static Environment env;

	public static String url;
	public static String username;
	public static String password;
	public static String driver;

	@RequestMapping(value = "/api/company/addCompany", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Company> addCompany(@RequestBody Company company) {

		Company proveraCompany = new Company();

		if (companyService.findByPib(company.getPib()) != null) {
			proveraCompany.setPib(BigInteger.valueOf(-1));
		} else {
			proveraCompany.setPib(BigInteger.valueOf(-2));
		}

		if (companyService.findByCidnumber(company.getCidnumber()) != null) {
			proveraCompany.setCidnumber(BigInteger.valueOf(-1));
		} else {
			proveraCompany.setCidnumber(BigInteger.valueOf(-2));
		}

		if (companyService.findByAccount(company.getAccount()) != null) {
			proveraCompany.setAccount("-1");
		} else {
			proveraCompany.setAccount("-2");
		}

		if (proveraCompany.getPib() == BigInteger.valueOf(-1) || proveraCompany.getCidnumber() == BigInteger.valueOf(-1)
				|| proveraCompany.getAccount().equals("-1")) {
			return new ResponseEntity<Company>(proveraCompany, HttpStatus.OK);
		}

		Company addingCompany = companyService.add(company);
		return new ResponseEntity<Company>(addingCompany, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/company/getAllCompanies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Company>> getCompanies() {
		Collection<Company> allCompanies = companyService.getAll();
		return new ResponseEntity<Collection<Company>>(allCompanies, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/company/izvestajIzlaznihFaktura/{firmaId}/{firmaNaziv}/{datumPocetka}/{datumKraja}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public void izvestajDnevnikIzlaznihFaktura(HttpServletResponse response, @PathVariable Integer firmaId,
			@PathVariable String firmaNaziv, @PathVariable String datumPocetka, @PathVariable String datumKraja)
			throws JRException, IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date datumPoc = new Date(datumPocetka);
		Date datumKr = new Date(datumKraja);
		String datum1 = format.format(datumPoc);
		String datum2 = format.format(datumKr);
		HashMap hm = null;
		exportXML();
		try {
			// System.out.println("Start ....");
			String jrxmlFileName = "./src/main/resources/static/reports/dnevnikIzlaznihFaktura.jrxml";
			String jasperFileName = "./src/main/resources/static/reports/dnevnikIzlaznihFaktura.jasper";
			String pdfFileName = "./src/main/resources/static/pdfFiles/dnevnikIzlaznihFaktura.pdf";

			JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);

			// String dbUrl = props.getProperty("jdbc.url");
			String dbUrl = "jdbc:mysql://localhost:3306/market?verifyServerCertificate=false&useSSL=false&requireSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			// String dbDriver = props.getProperty("jdbc.driver");
			String dbDriver = "com.mysql.jdbc.Driver";
			// String dbUname = props.getProperty("db.username");
			String dbUname = "root";
			// String dbPwd = props.getProperty("db.password");
			String dbPwd = "root";

			// Load the JDBC driver
			Class.forName(dbDriver);
			// Get the connection
			Connection conn = DriverManager.getConnection(dbUrl, dbUname, dbPwd);

			hm = new HashMap();
			hm.put("izdavaocId", firmaId);
			hm.put("izdavaocIme", firmaNaziv);
			hm.put("datumPocetka", datum1);
			hm.put("datumKraja", datum2);
			// Generate jasper print
			JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, hm, conn);

			// Export pdf file
			JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);

			// System.out.println("Done exporting reports to pdf");

			File file = new File("./src/main/resources/static/pdfFiles/dnevnikIzlaznihFaktura.pdf");
			if (file.exists()) {
				// System.out.println("Ima ga!");
				// System.out.println(file.getAbsolutePath());
			}
			try {
				Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
				p.waitFor();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			System.out.print("Exceptiion" + e);
		}
	}

	public void exportXML() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("company");
			doc.appendChild(rootElement);

			// staff elements
			Element staff = doc.createElement("Staff");
			rootElement.appendChild(staff);

			// set attribute to staff element
			Attr attr = doc.createAttribute("id");
			attr.setValue("1");
			staff.setAttributeNode(attr);

			// shorten way
			// staff.setAttribute("id", "1");

			// firstname elements
			Element firstname = doc.createElement("firstname");
			firstname.appendChild(doc.createTextNode("yong"));
			staff.appendChild(firstname);

			// lastname elements
			Element lastname = doc.createElement("lastname");
			lastname.appendChild(doc.createTextNode("mook kim"));
			staff.appendChild(lastname);

			// nickname elements
			Element nickname = doc.createElement("nickname");
			nickname.appendChild(doc.createTextNode("mkyong"));
			staff.appendChild(nickname);

			// salary elements
			Element salary = doc.createElement("salary");
			salary.appendChild(doc.createTextNode("100000"));
			staff.appendChild(salary);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("./src/main/resources/static/pdfFiles/xml/file.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	@RequestMapping(value = "/api/company/findByIdNot/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Company>> findAllByIdNot(@PathVariable Integer id) {
		Collection<Company> allCompanies = companyService.findByIdNot(id);
		return new ResponseEntity<Collection<Company>>(allCompanies, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/company/findByNameContaining/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Company>> findByNameContaining(@PathVariable String name) {
		Collection<Company> allCompanies = companyService.findByNameContaining(name);
		return new ResponseEntity<Collection<Company>>(allCompanies, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/company/findById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Company> findById(@PathVariable Integer id) {
		Company company = companyService.findOne(id);
		return new ResponseEntity<Company>(company, HttpStatus.OK);
	}

}
