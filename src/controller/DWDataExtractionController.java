package controller;

import model.Album;
import model.bp.Attribute;
import model.bp.KeywordQuery;
import model.bp.Relation;
import model.sources.Avax;
import model.sources.InternetArchive;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
	// check compatibility variables
	private KeywordQuery query;
	private List<Relation> relations;
	private Relation currentRelation;
	private String relationName;
	private List<Attribute> relationAttributes;
	private Attribute currentAttribute;
	private String attributeValue;
	private String attributeDomain;
	private String accessLimitation;

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

	public String checkCompatibility() {
		return "";
	}

	public String createAttribute() {
		Attribute.AccessLimitation access=null;
		if (this.accessLimitation.equals("Free"))
			access = Attribute.AccessLimitation.FREE;
		else if(this.accessLimitation.equals("Bound"))
			access = Attribute.AccessLimitation.BOUND;
		this.currentAttribute = new Attribute(this.attributeValue,this.attributeDomain,access);
		if(this.relationAttributes==null)
			this.relationAttributes= new ArrayList<>();
		this.relationAttributes.add(this.currentAttribute);
		return "attributes?faces-redirect=true";
	}

	public String createRelation() {
		if(this.relationName.equals("") || this.relationAttributes==null || this.relationAttributes.size()==0)
			return "relationError";
		this.currentRelation= new Relation(this.relationName,this.relationAttributes);
		return "relation?faces-redirect=true";
	}

	// GETTER AND SETTER
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

	public KeywordQuery getQuery() {
		return query;
	}

	public void setQuery(KeywordQuery query) {
		this.query = query;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	public Relation getCurrentRelation() {
		return currentRelation;
	}

	public void setCurrentRelation(Relation currentRelation) {
		this.currentRelation = currentRelation;
	}

	public List<Attribute> getRelationAttributes() {
		return relationAttributes;
	}

	public void setRelationAttributes(List<Attribute> relationAttributes) {
		this.relationAttributes = relationAttributes;
	}

	public Attribute getCurrentAttribute() {
		return currentAttribute;
	}

	public void setCurrentAttribute(Attribute currentAttribute) {
		this.currentAttribute = currentAttribute;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeDomain() {
		return attributeDomain;
	}

	public void setAttributeDomain(String attributeDomain) {
		this.attributeDomain = attributeDomain;
	}

	public String getAccessLimitation() {
		return accessLimitation;
	}

	public void setAccessLimitation(String accessLimitation) {
		this.accessLimitation = accessLimitation;
	}
}
