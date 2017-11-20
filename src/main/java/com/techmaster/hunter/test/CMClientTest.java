package com.techmaster.hunter.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;


public class CMClientTest {
	
	public static final String URL = "https://sgw01.cm.nl/gateway.ashx";
	
	public static void main(String[] args) {
		
		try {
            UUID productToken = UUID.fromString("b9dc662c-254a-4947-948a-a28acb0f17e3");
            String xml = createXml(productToken, "TechMasters", "0017324704894","Hi! Techmasters here testing connection with CM.");
            String response =	doHttpPost(URL, xml);
            System.out.println("Response: " + response);
            System.in.read();
        } catch (IOException e) {
            System.err.println(e); // Display the string.
        }
		
	}
	
	private static String createXml(UUID productToken, String sender, String recipient, String message) {
        try {
            ByteArrayOutputStream xml = new ByteArrayOutputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            // Get the DocumentBuilder
            DocumentBuilder docBuilder = factory.newDocumentBuilder();

            // Create blank DOM Document
            DOMImplementation impl = docBuilder.getDOMImplementation();
            Document doc = impl.createDocument(null, "MESSAGES", null);

            // create the root element
            Element root = doc.getDocumentElement();
            Element authenticationElement = doc.createElement("AUTHENTICATION");
            Element productTokenElement = doc.createElement("PRODUCTTOKEN");
            authenticationElement.appendChild(productTokenElement);
            Text productTokenValue = doc.createTextNode("" + productToken);
            productTokenElement.appendChild(productTokenValue);
            root.appendChild(authenticationElement);

            Element msgElement = doc.createElement("MSG");
            root.appendChild(msgElement);

            Element fromElement = doc.createElement("FROM");
            Text fromValue = doc.createTextNode(sender);
            fromElement.appendChild(fromValue);
            msgElement.appendChild(fromElement);

            Element bodyElement = doc.createElement("BODY");
            Text bodyValue = doc.createTextNode(message);
            bodyElement.appendChild(bodyValue);
            msgElement.appendChild(bodyElement);

            Element toElement = doc.createElement("TO");
            Text toValue = doc.createTextNode(recipient);
            toElement.appendChild(toValue);
            msgElement.appendChild(toElement);

            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

            Source src = new DOMSource(doc);
            Result dest = new StreamResult(xml);
            aTransformer.transform(src, dest);

            return xml.toString();

        } catch (TransformerException ex) {
            System.err.println(ex);
            return ex.toString();
        } catch (ParserConfigurationException p) {
            System.err.println(p);
            return p.toString();
        }
    }
	
	 private static String doHttpPost(String urlString, String requestString) {
	        try {
	            URL url = new URL(urlString);
	            URLConnection conn = url.openConnection();
	            conn.setDoOutput(true);

	            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	            wr.write(requestString);
	            wr.flush();
	            // Get the response
	            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line;
	            String response = "";
	            while ((line = rd.readLine()) != null) {
	                response += line;
	            }
	            wr.close();
	            rd.close();

	            return response;
	        } catch (IOException ex) {
	            System.err.println(ex); return ex.toString();
	        }
	 }

}
