package model.bp;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeepWeb {

    public static boolean checkCompatibility(KeywordQuery query,Schema s) {

        if(query.getAttributeList().size()<2)
            return false;

        List<Arc> visited = new ArrayList<>();
        Map<String,List<Attribute>> schemaDomains = s.getDomainsMap();
        for(Attribute a:query.getAttributeList()){
            if(!schemaDomains.containsKey(a.getDomain()))
                return false;
        }

        SchemaJoinGraph sj = new SchemaJoinGraph(s);
        for (int i=0; i<query.getAttributeList().size()-1;i++) {
            // reset at every iteration
            boolean found = false; //true if found at least one path for two domains of the query attributes
            String domainA1 = query.getAttributeList().get(i).getDomain();
            String domainA2 = query.getAttributeList().get(i+1).getDomain();
            for (int j=0;i<sj.getArcs().size() && !found;j++) {
                Arc current = sj.getArcs().get(j);
                if(!visited.contains(current)) {
                    Map<String,List<Attribute>> sourceDomains = current.getFrom().getRelation().getDomainsMap();
                    Map<String,List<Attribute>> destinationDomains = current.getTo().getRelation().getDomainsMap();
                    if(sourceDomains.containsKey(domainA1) && destinationDomains.containsKey(domainA2)
                            || sourceDomains.containsKey(domainA2) && destinationDomains.containsKey(domainA1))
                        found = true;
                    visited.add(current);
                    //adding to visited also the reverse arc to not make the check twice on the same nodes
                    visited.add(new Arc(current.getTo(),current.getFrom()));

                }
                // if all the arcs have been checked and found is still false then there is no path
                // for 2 domains of the query attributes --> not compatible query
                if(!found)
                    return false;
                visited = new ArrayList<>(); //empty visited list for next couples of domains

            }
        }

        return true;

    }
}