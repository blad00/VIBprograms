package be.ugent.psb.xml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class BioSampleSearcher {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fis = null;
		Document doc;
		PrintWriter outFile = null;
		try {
			fis = new FileInputStream(args[0]);
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = documentBuilder.parse(new InputSource(fis));
			outFile = new PrintWriter(new FileOutputStream(args[1]));


			Element rootElement = doc.getDocumentElement();
			NodeList nodeSamples = rootElement.getElementsByTagName("BioSample");
			loadSamplesData(nodeSamples,outFile);


		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		} finally {
			if(fis!=null) fis.close();
			if(outFile!=null) outFile.close();
		}

	}

	private static void loadSamplesData( NodeList nodeSamples,PrintWriter outFile) throws IOException {
		NodeList offspring = nodeSamples;
		System.out.println("TotalSamples: "+offspring.getLength());

		//Desc
		Element description;
		
		Element eleNodAtt;
		Node nodeAtt;
		Element eleNo;
		
		NodeList attriList;
		
		String allAttris="";
		String attribute_name="";
		String harmonized_name="";
		String display_name="";
		
		String cultivar=null,genotype=null,ecotype=null,tissue=null,dev_stage=null,leaf_number=null,stage=null,source_name=null,maize_cultivar=null;
		
		int numAtt=0;

		

		outFile.println("BioSample"+"\t"+"Title"+"\t"+"Paragraph"+"\t"+"cultivar"+"\t"+"genotype"+"\t"+"ecotype"+"\t"+"tissue"+"\t"+"dev_stage"+"\t"+"leaf number"+"\t"+
		"Stage"+"\t"+"source name"+"\t"+"maize cultivar"+"\t"+"#Attr"+"\t"+"ConcatAttrNames");

		for(int i=0;i<offspring.getLength();i++){  
			Node node = offspring.item(i);
			if (node instanceof Element){ 
				Element elem = (Element)node;
				//full Biosample
				if("BioSample".equals(elem.getNodeName())) {
					outFile.print(elem.getAttribute("accession"));
					outFile.print("\t");
					
					//Get description (Title)
//					System.out.println(((Element)elem.getElementsByTagName("Description").item(0)).getElementsByTagName("Title").item(0).getTextContent());
					description = (Element)elem.getElementsByTagName("Description").item(0);
					outFile.print(description.getElementsByTagName("Title").item(0).getTextContent());
					outFile.print("\t");
					
					//Get description (Paragraph)
					Element comment = (Element)description.getElementsByTagName("Comment").item(0);
					
					if(comment!=null){
						NodeList paragraph = comment.getElementsByTagName("Paragraph");
						outFile.print(paragraph.item(0).getTextContent());
						outFile.print("\t");
					}else{
						outFile.print("null");
						outFile.print("\t");
					}
					
					
					

					//Get attributes
					eleNodAtt = (Element)elem.getElementsByTagName("Attributes").item(0);
					attriList = eleNodAtt.getChildNodes();
//					System.out.println(attriList.getLength());
//wrong number		outFile.print(attriList.getLength());
					
					for(int j=0;j<attriList.getLength();j++){
						nodeAtt = attriList.item(j);
						if (nodeAtt instanceof Element){ 
							eleNo = (Element)nodeAtt;
							if("Attribute".equals(nodeAtt.getNodeName())) {
								numAtt++;
								attribute_name = eleNo.getAttribute("attribute_name");
								harmonized_name = eleNo.getAttribute("harmonized_name");
								display_name = eleNo.getAttribute("display_name");
								allAttris += "~"+attribute_name;
								
								String[] names={attribute_name,harmonized_name,display_name};
								
								if(Arrays.asList(names).contains("cultivar")){
									cultivar = eleNo.getTextContent();
								}
								if(Arrays.asList(names).contains("genotype")){
									genotype = eleNo.getTextContent();
								}
								if(Arrays.asList(names).contains("ecotype")){
									ecotype = eleNo.getTextContent();
								}
								if(Arrays.asList(names).contains("tissue")){
									tissue = eleNo.getTextContent();
								}
								if(Arrays.asList(names).contains("dev_stage")){
									dev_stage = eleNo.getTextContent();
								}
								if(Arrays.asList(names).contains("leaf number")){
									leaf_number = eleNo.getTextContent();
								}
								if(Arrays.asList(names).contains("Stage")){
									stage = eleNo.getTextContent();
								}
								if(Arrays.asList(names).contains("source name")){
									source_name = eleNo.getTextContent();
								}
								if(Arrays.asList(names).contains("maize cultivar")){
									maize_cultivar = eleNo.getTextContent();
								}

								
							}
						
						}
						
					}
					// print interesting fields
					outFile.print(cultivar+"\t"+genotype+"\t"+ecotype+"\t"+tissue+"\t"+dev_stage+"\t"+leaf_number+"\t"+stage+"\t"+source_name+"\t"+maize_cultivar+"\t");
					//print numer of attr
					outFile.print(numAtt+"\t");
					//print all atribs
					outFile.print(allAttris);

				}
				//reset vars
				allAttris="";
				cultivar=null;genotype=null;ecotype=null;tissue=null;dev_stage=null;leaf_number=null;stage=null;source_name=null;maize_cultivar=null;
				numAtt=0;
				
			}
			
			outFile.println();
		}
	}

}
