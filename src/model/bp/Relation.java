package model.bp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Relation {
    private String name;
    private List<Attribute> attributes;
    private int arity;

    public Relation(String name,List<Attribute> attributes) {
        this.name = name;
        this.attributes = attributes;
        this.arity = attributes.size();
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String,List<Attribute>> getDomainsMap() {
        Map<String,List<Attribute>> result = new HashMap<>();
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

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public int getArity() {
        return arity;
    }

    public void setArity(int arity) {
        this.arity = arity;
    }


    public boolean hasSimilarDomains(Relation r) {
        boolean similar = false;
        Map<String,List<Attribute>> thisDomainSet = getDomainsMap();
        Map<String,List<Attribute>> rDomainSet = r.getDomainsMap();
        ArrayList<String> domains =  new ArrayList<>(thisDomainSet.keySet());
        for(int i=0;i<domains.size() && !similar; i++)
            if(rDomainSet.containsKey(domains.get(i)))
                similar = true;

        return similar;

    }

    public String toString() {
        return "Name: " + this.name + "\nAttributes: " + this.attributes + "\nArity: " + this.arity +"\n";
    }
}
