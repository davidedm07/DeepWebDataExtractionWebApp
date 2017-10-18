
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import model.bp.*;
import model.sources.*;

// USER AGENT STRING TOR : Mozilla/5.0 (Windows NT 6.1; rv:52.0) Gecko/20100101 Firefox/52.0


public class DeepWebExtraction {

	public static void main(String[] args) {
		InternetArchive source1 = new InternetArchive("https://archive.org");
		Avax source2 = new Avax("http://avaxhome5lcpcok5.onion/");

		try { 
			String artist = "linkin park";
			String albumName = "meteora";

			com.jaunt.Document album = source2.applyQuery(artist + "\t" + albumName);
			System.out.println(source2.checkAlbumPresence(artist + "\t" + albumName,album));
			com.jaunt.Document albumDetails = source2.checkAllLinks(album);
			//com.jaunt.Document albumDetails = source2.getAlbumDetails(album);
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

		/*
		List<Attribute> r1Attributes = new ArrayList<>();
		Attribute name = new Attribute("Name", "Person",Attribute.AccessLimitation.INPUT);
		Attribute age = new Attribute("Age","Number",Attribute.AccessLimitation.OUTPUT);
		r1Attributes.add(name);
		r1Attributes.add(age);
		Relation r1 = new Relation("Actor",r1Attributes);

		List<Attribute> r2Attributes = new ArrayList<>();
		Attribute wife = new Attribute("Wife", "Person",Attribute.AccessLimitation.INPUT);
		Attribute husband = new Attribute("Husband", "Person",Attribute.AccessLimitation.OUTPUT);
		Attribute ring = new Attribute("Ring","Object", Attribute.AccessLimitation.INPUT);
		r2Attributes.add(wife);
		r2Attributes.add(husband);
		//r2Attributes.add(ring);
		Relation r2 = new Relation("WifeOf",r2Attributes);
		//System.out.println(r1.hasSimilarDomains(r2));
		List<Attribute> r3Attributes = new ArrayList<>();
		r3Attributes.add(age);
		Attribute year = new Attribute("year","Number", Attribute.AccessLimitation.OUTPUT);
		r3Attributes.add(year);
		//r3Attributes.add(husband);
		Relation r3 = new Relation("year",r3Attributes);
		ArrayList<Relation> relations = new ArrayList<>();
		relations.add(r1);
		relations.add(r2);
		relations.add(r3);
		Schema schema = new Schema("Test",relations);
		//System.out.println(schema.getDescription());
		KeywordQuery query = new KeywordQuery("testQueryString\tActor:Person\tOscar:Number");
		//System.out.println(query.toString());
		//System.out.println(DeepWeb.checkCompatibility(query,schema));
		SchemaJoinGraph sj = new SchemaJoinGraph(relations);
		//System.out.println(sj.toString());
		DependencyGraph dgr = new DependencyGraph(schema,query);
		//System.out.println(dgr.toString());
		System.out.println(DeepWeb.checkAnswerability(schema,query));
*/

	}

}
