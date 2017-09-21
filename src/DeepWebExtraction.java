
import java.net.MalformedURLException;
import java.util.ArrayList;

import model.sources.*;

// USER AGENT STRING TOR : Mozilla/5.0 (Windows NT 6.1; rv:52.0) Gecko/20100101 Firefox/52.0


public class DeepWebExtraction {

	public static void main(String[] args) {
		InternetArchive source1 = new InternetArchive("https://archive.org");
		Avax source2 = new Avax("http://avaxhome5lcpcok5.onion/");
		try { 
			String artist = "tyler, the creator";
			String albumName = "flower boy";

			com.jaunt.Document album = source2.applyQuery(artist + "\t" + albumName);
			com.jaunt.Document albumDetails = source2.getAlbumDetails(album);
			String description = source2.getDescription(albumDetails);
			System.out.println("Description: " + description);
			//ArrayList<String> albumTracklist = (ArrayList<String>) source2.getTracklist(albumDetails);
			//System.out.println(albumTracklist.toString());
			//System.out.println(source1.getLinksTrack(source1.applyQuery("linkin park in the end")));
			//for(String track:albumTracklist)
			//	source1.applyQuery(artist + " " + track);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
