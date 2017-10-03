package model.bp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schema implements Comparable<Schema> {

	private String name;
	private List<Relation> relations;
	private Attribute[] attributes;
	private List<Relation> unaryRelations;
	protected boolean fake = false;

	public Schema(String name, List<Relation> relations) {
		this.name = name;
		this.relations = relations;
	}

	public Schema(String name, Attribute[] attributes) {
		this.name = name;
		this.attributes = attributes;
	}

	public Attribute[] getAttributes() {
		if(this.attributes!= null)
			return this.attributes;
		else {
			List<Attribute> attributes = new ArrayList<>();
			for(Relation r:this.relations)
				attributes.addAll(r.getAttributes());
			Attribute[] result = new Attribute[attributes.size()];
			result = attributes.toArray(result);
			return result;

		}
	}

	public void setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(name + "(");
		int i = 0;
		for (Attribute a : attributes) {
			sb.append(a);
			if (i++ < attributes.length - 1) sb.append(",");
		}
		return sb.toString() + ")";
	}


	public String getDescription() {
		return this.name + "\n" + this.relations.toString();
	}

	public int compareTo(Schema s) {
		return toString().compareTo(s.toString());
	}

	public boolean equals(Schema source) {
		//Source source = (Source) o;
		if (!name.equals(source.name)) return false;
		if (attributes.length != source.attributes.length) return false;
		for (int i = 0; i < attributes.length; i++) {
			if (!attributes[i].equals(source.attributes[i])) {
				return false;
			}
		}
		return true;
	}
	// return the extended schema linked to a particular keyword query
	public Schema getExtendedSchema(KeywordQuery q) {
		List<Relation> expandedSchemaRelation = new ArrayList<>();
		expandedSchemaRelation.addAll(this.relations);
		this.unaryRelations = new ArrayList<>();
		for(Attribute a:q.getAttributeList()) {
			a.setAccessLimitation(Attribute.AccessLimitation.OUTPUT);
			List<Attribute> unaryRelationAttributes = new ArrayList<>();
			unaryRelationAttributes.add(a);
			Relation unaryRelation = new Relation(a.getName(),unaryRelationAttributes);
			this.unaryRelations.add(unaryRelation);
			expandedSchemaRelation.add(unaryRelation);
		}
		Schema result =new Schema("Expanded Schema",expandedSchemaRelation);
		result.setUnaryRelations(this.unaryRelations);
		return result;
	}

	public Map<String,List<Attribute>> getDomainsMap() {
		Map<String,List<Attribute>> result = new HashMap<>();
		if (this.attributes== null)
			this.attributes = getAttributes();
		for(Attribute a:this.attributes) {
			String domain = a.getDomain();
			if(result.containsKey(domain))
				result.get(domain).add(a);
			else {
				List<Attribute> list = new ArrayList<>();
				list.add(a);
				result.put(domain,list);
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return toString().hashCode();
	}

	public int getArity() {
		return attributes.length;
	}
	
	public boolean isFake() {
		return fake;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	public List<Relation> getUnaryRelations() {
		return unaryRelations;
	}

	public void setUnaryRelations(List<Relation> unaryRelations) {
		this.unaryRelations = unaryRelations;
	}
}
