package be.ugent.psb.network;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import be.ugent.psb.network.NetworkCreator;

public class NetworkCreatorTest {
/*
 * /home/dfcruz/Midas/research/projects/project_syngenta_c4/single_maize_plants/MoreDataSets/Networks/RandomNetOut/Net1_Norm_Log2.txt
0.7
/home/dfcruz/Midas/research/projects/project_syngenta_c4/single_maize_plants/MoreDataSets/Networks/Net1_Norm_Log2
 */
	@Test
	public void testExceptionIsThrown() throws FileNotFoundException, IOException {
		NetworkCreator netwo = new NetworkCreator();
		String[] arr = { "/home/dfcruz/Midas/research/projects/project_syngenta_c4/single_maize_plants/MoreDataSets/Networks/RandomNetOut/Net1_Norm_Log2.txt",
				"0.7", "/home/dfcruz/Midas/research/projects/project_syngenta_c4/single_maize_plants/MoreDataSets/Networks/Net1_Norm_Log22"};
		
		NetworkCreator.main(arr);
		
		
		
		
	}

}
