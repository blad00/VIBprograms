package be.ugent.psb.xml;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class BioSampleSearcherTest {
/*
 * program that search across studies of Maize to find more data sets like ours
 */
	private static final String DOM_ELEMENT_SAMPLES="samples";
	private static final String DOM_ELEMENT_TARGETS="targets";
	private static final String DOM_ELEMENT_SAMPLE="sample";
	private static final String DOM_ATTR_ID="id";
	private static final String DOM_ATTR_ROLE="role";
	
	public static void main(String[] args) throws IOException {
		
		
		
		// TODO Auto-generated method stub
		FileInputStream fis = null;
		Document doc;
		try {
			fis = new FileInputStream(args[0]);
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = documentBuilder.parse(new InputSource(fis));
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		} finally {
			if(fis!=null) fis.close();
		}
		Element rootElement = doc.getDocumentElement();
		Node nodeSamples = rootElement.getElementsByTagName(DOM_ELEMENT_SAMPLES).item(0);
		loadSamplesData((Element)nodeSamples);
		Node nodeTargets = rootElement.getElementsByTagName(DOM_ELEMENT_TARGETS).item(0);
//		loadTargets(breeder, (Element)nodeTargets);*/
	}
	
	private static void loadSamplesData( Element nodeSamples) throws IOException {
		NodeList offspring = nodeSamples.getChildNodes(); 
		for(int i=0;i<offspring.getLength();i++){  
			Node node = offspring.item(i);
			if (node instanceof Element){ 
				Element elem = (Element)node;
				if(DOM_ELEMENT_SAMPLE.equals(elem.getNodeName())) {
					
					
					NamedNodeMap attrs = elem.getAttributes();  
				      for(int j = 0 ; j<attrs.getLength() ; j++) {
				        Attr attribute = (Attr)attrs.item(j);     
				        System.out.print("@"+attribute.getName()+" = "+attribute.getValue());
				        
				      }
				      System.out.println();
					
					String sampleId = elem.getAttribute(DOM_ATTR_ID);
					String roleStr = elem.getAttribute(DOM_ATTR_ROLE);
					try {
//						if(roleStr!=null) System.out.println(sampleId+"--"+Integer.parseInt(roleStr));
					} catch (NumberFormatException e) {
						throw new IOException("Invalid role for sample "+sampleId,e);
					}
				}
			}
		}
	}

}
