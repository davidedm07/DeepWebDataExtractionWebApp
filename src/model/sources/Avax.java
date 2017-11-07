package model.sources;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jaunt.*;
import com.jaunt.component.Form;
import org.jsoup.Jsoup;

public class Avax extends Source {

	private List<String> albumLinks = new ArrayList<>();


	public Avax() {
		super("Avax","http://avaxhome5lcpcok5.onion/");
	}

	public Avax(String url) {
		super("Avax",url);
		super.setSchemaDescription("AVAX\nAttributes:\nAlbumTitle:Input, Artist:Input,Album:Output");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void printQueryResult(String query) throws MalformedURLException {
		// TODO Auto-generated method stub
		UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser)
		userAgent.setProxyHost("127.0.0.1");
		userAgent.setProxyPort(8118);
		com.jaunt.Document answer;
		try {
			userAgent.visit(this.getUrl());
		} catch (ResponseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Form> forms = userAgent.doc.getForms();
		Form form = forms.get(3);
		try {
			answer = form.setTextField("q", query).submit();
			getAlbumDetails(answer);
		} catch (NotFound | ResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(answer.innerHTML());

	}

	public com.jaunt.Document applyQuery(String query) throws MalformedURLException {
		UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser)
		userAgent.setProxyHost("127.0.0.1");
		userAgent.setProxyPort(8118);
		com.jaunt.Document answer = null;
		try {
			userAgent.visit(this.getUrl());
		} catch (ResponseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Form> forms = userAgent.doc.getForms();
		Form form = forms.get(3);
		try {
			answer = form.setTextField("q", query).submit();
		} catch (NotFound | ResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return answer;
	}

	public boolean checkAlbumPresence(String query,com.jaunt.Document page) {
		String artist,album;
		boolean found =false;
			artist = query.split("\t")[0];
			album = query.split("\t")[1];
			org.jsoup.nodes.Document doc = Jsoup.parse(page.outerHTML());
			org.jsoup.select.Elements links = doc.select("a[href]");
			for(int i=0;i<links.size();i++) {
				String link = links.get(i).text().toLowerCase();
				if(link.contains(artist) && link.contains(album)) {
					found = true;
					String testLink = links.get(i).attr("href");
					this.albumLinks.add(testLink);
				}
			}

		return found;
	}

	// albumLinks contiene tutti i link rilevanti per la query
	// possibile ricerca dei dati dell'album in più link
	// modificare la parte della ricerca dei dettagli utilizzando la lista
	public com.jaunt.Document getAlbumDetails(com.jaunt.Document queryResult) {
		UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser)
		userAgent.setProxyHost("127.0.0.1");
		userAgent.setProxyPort(8118);
		com.jaunt.Document answer = null;
		try {
			Element details = queryResult.findFirst("<span class=\"glyphicon glyphicon-link\">");
			String albumDetailsLink = details.getParent().getAt("href");
			userAgent.visit(albumDetailsLink);
			answer = userAgent.doc;
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answer;
	}

	public com.jaunt.Document checkAllLinks(com.jaunt.Document queryResult) {
		UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser)
		userAgent.setProxyHost("127.0.0.1");
		userAgent.setProxyPort(8118);
		com.jaunt.Document answer = null;
		try {
			for (String link:albumLinks) {
				System.out.println(link);
				userAgent.visit(link);
				answer = userAgent.doc;
				List<String> tracklist = getTracklist(answer);
				String description = getDescription(answer);
				if (tracklist!=null && !description.isEmpty())
					break;

			}
		} catch (ResponseException e) {
			e.printStackTrace();
		}
		return answer;
	}

	// CLEAN TRACKS REGEX [^0-9][^\. ][a-z]+[^0-9\(]
	public List<String> getTracklist(com.jaunt.Document albumDetails) {
		Element tracklist;
		List<String> tracks = new ArrayList<String>();
		try {
			tracklist = albumDetails.findFirst("<b>Tracklist");
			Element tmp = tracklist.nextSiblingElement();
			Element cdElement = tmp.nextSiblingElement().nextSiblingElement();
			ArrayList<String> listTracks;
			if (cdElement.toString().equals("<i>"))
				listTracks = new ArrayList<>(Arrays.asList(cdElement.innerHTML().split("<br>")));
			else {
				tmp = tracklist;
				String rawText = tmp.getParent().getText();
				// [0-9][0-9](.)*( -)*
				listTracks = new ArrayList<>(Arrays.asList(rawText.trim().split("[0-9][0-9] -")));
			}
			// PATTERN FUNZIONANTE PRECEDENTE A-Za-z_]+([a-zA-Z_.0-9' ])*
			Pattern p = Pattern.compile("[A-Za-z_]+([a-zA-Z_.?!' ])*");
			Matcher m;
			for (String rawTrack : listTracks)
				if (!rawTrack.isEmpty() && !rawTrack.equals(" ") && !rawTrack.contains("CD")) {
					m = p.matcher(rawTrack.trim());
					m.find();
					String cleanedTrack = m.group();
					tracks.add(cleanedTrack);
				}

		} catch (NotFound e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return getTracklistP2(albumDetails);
		}

		return tracks;
	}

	private List<String> getTracklistP2(com.jaunt.Document albumDetails) {
		Element tracklist = null;
		List<String> tracks = new ArrayList<String>();

		for (Element x : albumDetails.findEvery("<b>")) {
			if (x.getText().contains("Track") || x.getText().contains("track")) {
				tracklist = x;
				break;
			}
		}
		if (tracklist!= null) {
			List<com.jaunt.Node> tchildren = tracklist.getParent().getChildNodes();
			boolean tracklistNodeFound = false;
			for (com.jaunt.Node child : tchildren) {
				if (child.toString().equals("<b>"))
					tracklistNodeFound = true;
				else if (tracklistNodeFound && !child.toString().equals("<b>")
						&& !child.toString().contains("track") && !child.toString().contains("Track")
						&& !child.toString().equals("<br>"))
					tracks.add(child.toString().replaceAll("([0-9]+[.]?[-]?[ ]?)", ""));
			}
		}
		else {
			try {
				tracklist = albumDetails.findFirst("<br>TRACKLIST");
				System.out.println(tracklist.nextSiblingElement().getText());
			} catch (NotFound notFound) {
				notFound.printStackTrace();
			}
		}
		return tracks;
	}


	public String getDescription(com.jaunt.Document albumDetails) {
		String description = "";
		try {
			description = albumDetails.findFirst("<blockquote>").getText();
		} catch (NotFound e) {
			return getDescriptionP2(albumDetails);
		}
		return description.replaceAll("&quot","");
	}

	private String getDescriptionP2(com.jaunt.Document albumDetails) {
		String description= "";
		try {
			description= albumDetails.findFirst("<meta name=\"description\">").getAt("content");
		} catch (NotFound notFound) {
			notFound.printStackTrace();
		}
		return description.replaceAll("&quot","");
	}
	
	public String getCover(com.jaunt.Document albumDetails) {
		String imageSource;
		try {
			Element htmlPin = albumDetails.findFirst("<img class=\"img-responsive\"");
			imageSource = htmlPin.getAt("src");
		} catch (NotFound e) {
			imageSource = "Image Not Found!";
			e.printStackTrace();
		}
		return imageSource;
		
	}


}
