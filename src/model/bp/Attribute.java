package model.bp;

public class Attribute {
	
	AccessLimitation accessLimitation;
	String name;
	private String domain;
	
	public enum AccessLimitation {
		BOUND, FREE,INPUT,OUTPUT
	}

	public Attribute(String name,String domain, AccessLimitation accessLimitation) {
		this.name = name;
		this.domain = domain;
		this.accessLimitation = accessLimitation;

	}

	public Attribute(Attribute a) {
		this.name = a.name;
		this.accessLimitation = a.accessLimitation;
	}

	public Attribute(String name,AccessLimitation accessLimitation) {
		this.name = name;
		this.accessLimitation = accessLimitation;
	}

	public AccessLimitation getAccessLimitation() {
		return accessLimitation;
	}

	public void setAccessLimitation(AccessLimitation accessLimitation) {
		this.accessLimitation = accessLimitation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isBound() {
		return accessLimitation == AccessLimitation.BOUND;
	}
	public boolean isInput() {return accessLimitation == AccessLimitation.INPUT;}

	public String toString() {
		if (this.domain==null)
			return name +":Unknown"+ "/" + (accessLimitation == AccessLimitation.INPUT ? "Input" : "Output" );
		return name +":"+ this.domain + "/" + (accessLimitation == AccessLimitation.INPUT ? "Input" : "Output");
	}

	public boolean equals(Object o) {
		Attribute a = (Attribute) o;
		return a.name.equals(name) && a.accessLimitation == accessLimitation;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
