package model.bp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Relation {
    private String name;
    private List<Attribute> attributes;
    private int arity;
    private Map<Attribute,List<String>> data;

    public Relation(String name,List<Attribute> attributes) {
        this.name = name;
        this.attributes = attributes;
        this.arity = attributes.size();
        this.data = new LinkedHashMap<>();
        for(Attribute a:this.attributes)
            data.put(a,new ArrayList<>());
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

    public Map<Attribute, List<String>> getData() {
        return data;
    }

    public void setData(Map<Attribute, List<String>> data) {
        this.data = data;
    }

    public static Relation createRelation(String pathToFile) {
        Relation r = null;
        try {
            FileReader fr = new FileReader(pathToFile);
            BufferedReader br = new BufferedReader(fr);
            String currentLine;
            int index = 0;
            String relationName="";
            String relationSchema;
            while((currentLine = br.readLine())!=null) {
                if(index == 0)
                    relationName = currentLine;
                else if(index==1) {
                    relationSchema = currentLine;
                    List<Attribute> attributes = createSchema(relationSchema);
                    r = new Relation(relationName,attributes);

                }
                else {
                    String[] data = currentLine.split(";");
                    int i = 0;
                    for(Attribute a: r.getData().keySet()) {
                        r.getData().get(a).add(data[i]);
                        i++;
                    }
                }
                index++;

            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    public static List<Attribute> createSchema(String relationSchema) {
        List<Attribute> attributes = new ArrayList<>();
        String[] attributeDescription = relationSchema.split(";");
        for(String attribute:attributeDescription) {
            String[] attrInfo = attribute.split(":");
            Attribute.AccessLimitation accessLimitation;
            if(attrInfo[2].equals("Input"))
                accessLimitation = Attribute.AccessLimitation.INPUT;
            else
                accessLimitation = Attribute.AccessLimitation.OUTPUT;

            Attribute current = new Attribute(attrInfo[0],attrInfo[1],accessLimitation);
            attributes.add(current);
        }
        return attributes;
    }
}
