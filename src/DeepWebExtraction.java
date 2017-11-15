
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.regexp.internal.RE;
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

		Attribute name = new Attribute("Name", "Person",Attribute.AccessLimitation.INPUT);
		Attribute age = new Attribute("Age","Number",Attribute.AccessLimitation.OUTPUT);
		Attribute wife = new Attribute("Wife", "Person",Attribute.AccessLimitation.INPUT);
		Attribute husband = new Attribute("Husband", "Person",Attribute.AccessLimitation.OUTPUT);
		Attribute ring = new Attribute("Ring","Object", Attribute.AccessLimitation.OUTPUT); //Originally INPUT
		Attribute marriageYear = new Attribute("MarriageYear","Number", Attribute.AccessLimitation.INPUT);
		Attribute lucky = new Attribute("Lucky","Dog", Attribute.AccessLimitation.OUTPUT);
		Attribute dog = new Attribute("Dog","Dog", Attribute.AccessLimitation.INPUT);
		Attribute year = new Attribute("year","Number", Attribute.AccessLimitation.OUTPUT);
		Attribute toy = new Attribute("Toy","Object", Attribute.AccessLimitation.INPUT);

		List<Attribute> r1Attributes = new ArrayList<>();
		List<Attribute> r2Attributes = new ArrayList<>();
		List<Attribute> r3Attributes = new ArrayList<>();

		r1Attributes.add(husband);
		//r1Attributes.add(dog);
		r1Attributes.add(marriageYear);

		r2Attributes.add(wife);
		//r2Attributes.add(husband);
		r2Attributes.add(ring);
		//r2Attributes.add(year);
		//r2Attributes.add(marriageYear);
		//r2Attributes.add(lucky);

		//r3Attributes.add(age);
		//r3Attributes.add(year);
		r3Attributes.add(toy);
		r3Attributes.add(dog);
		//r3Attributes.add(husband);

		Relation r1 = new Relation("Actor",r1Attributes);
		Relation r2 = new Relation("WifeOf",r2Attributes);
		Relation r3 = new Relation("Year",r3Attributes);

		ArrayList<Relation> relations = new ArrayList<>();
		relations.add(r1);
		relations.add(r2);
		relations.add(r3);
		Schema schema = new Schema("Test",relations);
		//System.out.println(schema.getDescription());
		KeywordQuery query = new KeywordQuery("testQueryString\tPerson:Person\tDog:Dog\tToy:Object");
		System.out.println(query.getDomainsMap());
		//System.out.println(query.toString());
		//System.out.println(DeepWeb.checkCompatibility(query,schema));
		SchemaJoinGraph sj = new SchemaJoinGraph(relations);
		//System.out.println(sj.toString());
		DependencyGraph dgr = new DependencyGraph(schema,query);
		System.out.println(dgr.toString());
		//Node n = sj.getNodesMap().get("Actor");
		*/
		/*
		Relation department = Relation.createRelation("Department.txt");
		Relation employee = Relation.createRelation("Employee.txt");
		Relation project = Relation.createRelation("Project.txt");
		List<Relation> schemaRelations = new ArrayList<>();
		schemaRelations.add(department);
		schemaRelations.add(employee);
		schemaRelations.add(project);
		Schema s = new Schema("ExtractionTest",schemaRelations);
		KeywordQuery q = new KeywordQuery("Query\tIT:Department\tDBA:Role");
		DependencyGraph dg = new DependencyGraph(s,q);
		System.out.println(new SchemaJoinGraph(s));
		System.out.println(DeepWeb.checkCompatibility(q,s));
		//System.out.println(DeepWeb.findWitnesses(s,q));
		System.out.println(DeepWeb.queryAnswerExtraction(q,s));
		*/
		//System.out.println(r3.getName());
		//System.out.println(DeepWeb.isUseful(query,schema,n));
		//System.out.println(sj.getNodeArcs().toString());
		//Map<List<Node>,Integer> result = DeepWeb.findWitnesses(schema,query);
		//System.out.println(result.toString());
		//System.out.println(DeepWeb.filterAndSortWitnesses(result));
		//System.out.println(DeepWeb.isRelationVisible(r2,dgr));
		//System.out.println(DeepWeb.checkAnswerability(schema,query));
//*/

	}

}
