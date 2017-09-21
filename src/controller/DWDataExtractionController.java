package controller;

import model.Album;
import model.sources.Avax;
import model.sources.InternetArchive;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class DWDataExtractionController {

	private String artist;
	private String albumTitle;
	private Album album;
	private String description;
	private List<String> tracklist;
	private String coverLink;
	private List<String> links;

	public String searchAlbum() throws IOException {
		Avax albumSource = new Avax();
		FacesContext facesContext = FacesContext. getCurrentInstance();
		try {
			String query = artist + "\t" + albumTitle;
			com.jaunt.Document queryPage = albumSource.applyQuery(query);
			if(!albumSource.checkAlbumPresence(query,queryPage))
				return "error";
			com.jaunt.Document albumDetails = albumSource.getAlbumDetails(queryPage);
			this.description = albumSource.getDescription(albumDetails);
			this.tracklist = albumSource.getTracklist(albumDetails);
			this.coverLink = albumSource.getCover(albumDetails);
			this.album = new Album(this.artist,this.albumTitle,this.description,this.tracklist)
			;
			if(this.tracklist.size()==0 || this.description==null)
				return "error";
			HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(true);
			session.setAttribute("album",album);
			session.setAttribute("cover",coverLink);

		} catch (MalformedURLException e) {
			return "error";
		}
		return "albumDetails?faces-redirect=true";
	}

	public String searchLinks(String track) {
		InternetArchive linksSource =  new InternetArchive();
		try {
			org.jsoup.nodes.Document linksPage = linksSource.applyQuery(this.artist + " " + track);
			this.links = linksSource.getLinksTrack(linksPage);
			if (this.links.size()== 0)
				return "linkNotFound";
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return "links?faces-redirect=true";
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {

		this.artist = artist;
	}

	public String getAlbumTitle() {

		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {

		this.albumTitle = albumTitle;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {

		this.album = album;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public List<String> getTracklist() {

		return tracklist;
	}

	public void setTracklist(List<String> tracklist) {

		this.tracklist = tracklist;
	}

	public String getCoverLink() {
		return coverLink;
	}

	public void setCoverLink(String coverLink) {
		this.coverLink = coverLink;
	}

	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}
}
