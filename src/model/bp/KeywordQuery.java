package model.bp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeywordQuery extends Query {
    private String name;
    private List<Attribute> attributeList;

    //Using Attributes also for the query fields, probably it needs to be changed
    //query fields are not proper attributes
    public KeywordQuery(String name,List<Attribute> attributes) {
        this.name = name;
        this.attributeList =attributes;
    }

    public KeywordQuery(String query) {
        String[] parts = query.split("\t");
        if(parts.length == 0)
            System.out.println("Error: Query not well formed");
        else if(parts.length==1) {
            System.out.println("Error: missing query attributes");
            this.name = parts[0];
        }
        else {
            this.name = parts[0];
            this.attributeList = new ArrayList<>();
            for (int i=1; i<parts.length;i++) {
                String currentAttribute = parts[i];
                String[] attributeInfo = currentAttribute.split(":");
                if(attributeInfo.length!=2)
                    System.out.println("Error: attribute num. " + i + "not well formed: missing fields");
                else {
                    String attributeName = attributeInfo[0];
                    String domain = attributeInfo[1];
                    Attribute current = new Attribute(attributeName,domain, Attribute.AccessLimitation.OUTPUT);
                    this.attributeList.add(current);
                }
            }

        }


    }

    public HashMap<String,List<Attribute>> getDomainsMap() {
        HashMap<String,List<Attribute>> result = new HashMap<>();
        for(Attribute a:this.attributeList) {
            String aDomain = a.getDomain();
            if(result.containsKey(aDomain))
                result.get(aDomain).add(a);
            else {
                List<Attribute> list = new ArrayList<>();
                list.add(a);
                result.put(aDomain,list);
            }

        }

        return result;

    }


    @Override
    public CQ getCQ() {
        return null;
    }

    @Override
    public int getArity() {
        return 0;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Name: " + this.name + "\nAttributes: " + this.attributeList.toString();
    }
}
