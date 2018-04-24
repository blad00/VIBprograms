package be.ugent.psb.galaxy;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;

public class ExampleJsoup {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filePath = "D:/DanielVIB/Maize/MoreDataSets/subFWQCTMP/Trimmomatic_on_SRR5748718_1_fastq__R1_paired__fastqc.html";
		File input = new File(filePath);
	    org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");
        org.jsoup.select.Elements rows = doc.select("tr");
        for(org.jsoup.nodes.Element row :rows)
        {
            org.jsoup.select.Elements columns = row.select("td");
            for (org.jsoup.nodes.Element column:columns)
            {
                System.out.print(column.text());
            }
            System.out.println();
        }
	}

}
