package model.bp;


import java.util.*;

public class DeepWeb {

    public static boolean checkCompatibility(KeywordQuery query, Schema s) {

        if (query.getAttributeList().size() < 2)
            return false;
        Map<String, List<Attribute>> schemaDomains = s.getDomainsMap();
        for (Attribute a : query.getAttributeList()) {
            if (!schemaDomains.containsKey(a.getDomain()))
                return false;
        }

        SchemaJoinGraph sj = new SchemaJoinGraph(s);
        for (int i = 0; i < query.getAttributeList().size() - 1; i++) {
            // reset at every iteration
            boolean found = false; //true if found at least one path for two domains of the query attributes
            String domainA1 = query.getAttributeList().get(i).getDomain();
            String domainA2 = query.getAttributeList().get(i + 1).getDomain();
            found = checkPath(domainA1,domainA2,sj);
            if (!found)
                return false;
            }
        return true;

    }

    private static boolean checkPath(String d1,String d2, SchemaJoinGraph s) {
        boolean found = false;
        List<Node> visited = new ArrayList<>();
        for(Node n: s.getNodes())
            if(n.getRelation().getDomainsMap().containsKey(d1)) {
                for (Node dest : s.getNodeDestinations().get(n))
                    found = checkPathRec(d2, dest,s,visited);
                if (found)
                    return  true;
            }

        return false;
    }
    private static boolean checkPathRec(String d2, Node start,SchemaJoinGraph sj,List<Node> visited) {
        if(start.getRelation().getDomainsMap().containsKey(d2))
            return true;
        boolean found;
        visited.add(start);
        for(Node n:sj.getNodeDestinations().get(start)) {
            if(!visited.contains(n)) {
                found = checkPathRec(d2, n, sj, visited);
                if (found)
                    return true;
            }
        }
        return false;



    }
    public static boolean checkAnswerability(Schema s, KeywordQuery q) {
        DependencyGraph dg = new DependencyGraph(s, q);
        List<Relation> visibleRelations = getAllVisibleRelations(s, dg);
        Schema subset = new Schema("subset of schema s: only visible relations", visibleRelations);
        return checkCompatibility(q, subset);
    }

    public static List<Relation> getAllVisibleRelations(Schema s, DependencyGraph dg) {
        List<Relation> visibleRelations = new ArrayList<>();
        for (Relation r : s.getRelations())
            if (isRelationVisible(r, dg))
                visibleRelations.add(r);
        return visibleRelations;
    }

    public static boolean isRelationVisible(Relation r, DependencyGraph dg) {
        String nodeID;
        boolean attrVisible;
        boolean inputPresent = false; // at least one input attribute present
        for (Attribute a : r.getAttributes()) {
            nodeID = a.getName() + ":" + a.getDomain() + ":" + r.getName();
            if (a.getAccessLimitation() == Attribute.AccessLimitation.INPUT) {
                inputPresent = true;
                attrVisible = dg.checkVisibility(dg.getNodesMap().get(nodeID));
                if (!attrVisible)
                    return false;
            }
        }
        return inputPresent;

    }

    public static boolean isUseful(KeywordQuery q, Schema s, Node n) {
        List<String> visitedRelations = new ArrayList<>();
        SchemaJoinGraph sj = new SchemaJoinGraph(s);
        DependencyGraph dg = new DependencyGraph(s,q);
        Map<String,List<Attribute>> queryDomains = q.getDomainsMap();
        Map<String,Node> sjNodes = sj.getNodesMap();
        Map<Node,List<Node>> dgNodesDestinations = dg.getNodeDestinations();
        return isUsefulRec(queryDomains,sjNodes,dg,dgNodesDestinations,n,visitedRelations);
    }

    private static boolean isUsefulRec( Map<String,List<Attribute>> queryDomains,Map<String,
            Node> sjNodes,DependencyGraph dg,Map<Node,List<Node>> dgNodesDestinations,Node n, List<String> visitedRelations) {

        Relation nodeRelation = n.getRelation();
        if(!sjNodes.containsKey(nodeRelation.getName())|| !isRelationVisible(nodeRelation,dg))
            return false;
        //if dom(A) = dom(K) for k belonging to q N is useful
        for (Attribute a:nodeRelation.getAttributes())
            if(queryDomains.containsKey(a.getDomain()))
                return true;
        //for every output attribute of the current relation
        visitedRelations.add(nodeRelation.getName());
        for (Attribute a:nodeRelation.getAttributes()) {
            if(a.getAccessLimitation() == Attribute.AccessLimitation.OUTPUT) {
                //ID of the current node in the map derived from the dependency graph
                String nodeID = a.getName() + ":" + a.getDomain() + ":" + nodeRelation.getName();
                Node dgCurrent = dg.getNodesMap().get(nodeID);
                for(Node next:dgNodesDestinations.get(dgCurrent)) {
                    if (!visitedRelations.contains(next.getRelation().getName())) {
                        Node nextVisited = sjNodes.get(next.getRelation().getName());
                        boolean useful = isUsefulRec(queryDomains,sjNodes,dg,dgNodesDestinations,nextVisited,visitedRelations);
                        if(useful)
                            return true;
                    }

                }
            }
        }

        return false;
    }

    public static Map<List<Node>,Integer> findWitnesses(Schema s, KeywordQuery q) {
        SchemaJoinGraph sj = new SchemaJoinGraph(s);
        DependencyGraph dg = new DependencyGraph(s,q);
        List<String> queryDomains = new ArrayList<>(q.getDomainsMap().keySet());
        List<Node> witnessNodes  = findWitnessProbableNodes(sj,queryDomains);
        Map<List<Node>,Integer> witnesses = new HashMap<>();
        if(!witnessNodes.isEmpty()) {
            for(int i =0; i<witnessNodes.size();i++) {
                Node startingNode = witnessNodes.get(i);
                List<Node> visitedNodes = new ArrayList<>();
                List<String> visitedArcs = new ArrayList<>();
                List<Node> tempPath = new ArrayList<>();
                findWitnessesRec(startingNode, sj, witnessNodes, visitedNodes,visitedArcs, witnesses, tempPath);

            }
            evaluateAccesses(witnesses, dg, sj);

        }
        return witnesses;
    }

    public static Map<List<Node>,Integer> findWitness(Schema s, KeywordQuery q) {
        SchemaJoinGraph sj = new SchemaJoinGraph(s);
        DependencyGraph dg = new DependencyGraph(s,q);
        List<String> queryDomains =  new ArrayList<>(q.getDomainsMap().keySet());
        List<Node> witnessNodes  = findWitnessProbableNodes(sj,queryDomains);
        Map<List<Node>,Integer> witnessesSingleStart = new HashMap<>();
        if(!witnessNodes.isEmpty()) {
            Node startingNode = witnessNodes.get(0);
            List<Node> visitedNodes = new ArrayList<>();
            List<String> visitedArcs = new ArrayList<>();
            List<Node> tempPath = new ArrayList<>();
            findWitnessesRec(startingNode,sj,witnessNodes,visitedNodes,visitedArcs,witnessesSingleStart,tempPath);
            evaluateAccesses(witnessesSingleStart,dg,sj);

        }
        return witnessesSingleStart;
    }

    private static void findWitnessesRec(Node start,SchemaJoinGraph sj,List<Node> witnessNodes,
                                         List<Node> visitedNodes,List<String> visitedArcs,Map<List<Node>,Integer> witnessSingleStart, List<Node> tempPath) {
        if(!visitedArcs.containsAll(sj.getNodeArcs().get(start))) {
            visitedNodes.add(start);
            tempPath.add(start);

            if (!visitedNodes.containsAll(witnessNodes)) {
                for (Node destination : sj.getNodeDestinations().get(start)) {
                    String currentArc = "(" + start.getRelation().getName() + "," + destination.getRelation().getName() + ")";
                    if (!visitedArcs.contains(currentArc)) {
                        visitedArcs.add(currentArc);
                        findWitnessesRec(destination, sj, witnessNodes, visitedNodes, visitedArcs, witnessSingleStart, tempPath);
                    }

                }
            } else {
                List<Node> path = new ArrayList<>();
                path.addAll(tempPath);
                witnessSingleStart.putIfAbsent(path, -1);
            }
            if(tempPath.size()>=1)
                tempPath.remove(tempPath.size()-1);
            if(visitedArcs.size()>=1)
                visitedArcs.remove(visitedArcs.size()-1);
            visitedNodes.remove(start);


        }
    }

    private static void evaluateAccesses(Map<List<Node>,Integer> witnessPaths,DependencyGraph dg,SchemaJoinGraph sj) {
        List<List<Node>> paths = new ArrayList<>(witnessPaths.keySet());
        int accesses= 1;
        for(int i=0;i<paths.size();i++) {
            List<Node> currentPath = paths.get(i);
            evaluateAccessesRec(currentPath.get(0),currentPath,dg,sj,accesses,0,witnessPaths);
        }

    }

    private static void evaluateAccessesRec(Node current,List<Node> path,DependencyGraph dg,SchemaJoinGraph sj,int accesses,int currentPosition,Map<List<Node>,Integer> witnessPaths) {
        if (currentPosition<path.size()-1) {
            for (Attribute a : current.getRelation().getAttributes()) {
                if (a.getAccessLimitation() == Attribute.AccessLimitation.OUTPUT) {
                    Node currentDgNode = dg.getNodesMap().get(a.getName() + ":" + a.getDomain() + ":" + current.getRelation().getName());
                    List<Node> aDestinations = dg.getNodeDestinations().get(currentDgNode);
                    for (Node n : aDestinations) {
                        if (n.getRelation().equals(path.get(currentPosition + 1).getRelation())) {
                            Node next = sj.getNodesMap().get(n.getRelation().getName());
                            evaluateAccessesRec(next, path, dg, sj,accesses + 1, currentPosition + 1,witnessPaths);
                        }
                    }
                }
            }
        }
        if(currentPosition+1==path.size())
            witnessPaths.put(path,accesses);

    }

    private static List<Node> findWitnessProbableNodes(SchemaJoinGraph sj, List<String> queryDomains) {
        Map<String,String> queryDomainsFound = new HashMap<>();
        List<Node> result = new ArrayList<>();
        for(Node n:sj.getNodes()) {
            boolean usefulNode = false;
            for(Attribute a:n.getRelation().getAttributes()) {
                if(queryDomains.contains(a.getDomain())) {
                    usefulNode = true;
                    queryDomainsFound.putIfAbsent(a.getDomain(),a.getDomain());
                }
            }

            if(usefulNode)
                result.add(n);
        }
        //if The nodes contain all the domains of the query then return the nodes
        // else there are missing query domains values
        if(queryDomainsFound.keySet().size() == queryDomains.size())
            return result;
        else
            return new ArrayList<>();
    }

    public static TreeMap<Integer,List<List<Node>>> filterAndSortWitnesses(Map<List<Node>,Integer> witnesses) {
        Map<Integer,List<List<Node>>> sortedWitnesses = new HashMap<>();
        for(List<Node> list:new ArrayList<>(witnesses.keySet())) {
            Integer current = witnesses.get(list);
            if(current==-1)
                witnesses.remove(list);
            else {
                if(sortedWitnesses.containsKey(current))
                    sortedWitnesses.get(current).add(list);
                else {
                    List<List<Node>> sWList = new ArrayList<>();
                    sWList.add(list);
                    sortedWitnesses.put(current,sWList);
                }
            }
        }

        return new TreeMap<>(sortedWitnesses);
    }

    public static List<Map<Attribute,String>> queryAnswerExtraction(KeywordQuery q, Schema s) {
        if (!checkAnswerability(s,q))
            return null;
        Map<List<Node>,Integer> witnesses = findWitnesses(s,q);
        TreeMap<Integer,List<List<Node>>> sortedWitnesses = filterAndSortWitnesses(witnesses);
        List<Integer> keys = new ArrayList<>(sortedWitnesses.keySet());
        int currentKey = 0;
        List<Map<Attribute,String>> result = new ArrayList<>();
        while(!keys.isEmpty()) {
            List<List<Node>> currentWitnesses = sortedWitnesses.get(keys.get(currentKey));
            keys.remove(currentKey);
            for(List<Node> witness:currentWitnesses) {
                List<Node> tempPath = new ArrayList<>();
                tempPath.addAll(witness);
                  visitWitness(tempPath,result,q);
                  if(isResultExtracted(result,q)) {
                      return result;
                   }
            }
            currentKey++;

        }

        return null;

    }

     private static void visitWitness(List<Node> path,List<Map<Attribute,String>> tuples,KeywordQuery q) {
        if(path.size()>=1) {
            Node start = path.get(0);
            Relation currentRelation = start.getRelation();
            Map<String,List<Attribute>> relationDomains = currentRelation.getDomainsMap();
            Map<String,List<Attribute>> queryDomains = q.getDomainsMap();
            //check all the possible links possible between the query and the current relation
            for(String d:queryDomains.keySet()) {
                //check if the relation contains one of the domains of the query
                if(relationDomains.containsKey(d)) {
                    //if it does check which attribute is an input one, arc query->relation
                    for(Attribute a: relationDomains.get(d)) {
                        if(a.getAccessLimitation() == Attribute.AccessLimitation.INPUT) {
                            //values related to the attribute "a" of the relation
                            List<String> valuesInRelation = currentRelation.getData().get(a);
                            //if the values contains the query's one then extract the data
                            for(Attribute inQuery:queryDomains.get(d)) {
                                if(valuesInRelation.contains(inQuery.getName())) {
                                    int index = valuesInRelation.indexOf(inQuery.getName());
                                    extractTuples(tuples,index,currentRelation);
                                    continueExtraction(tuples,0, path,q,1);

                                }
                            }
                        }
                    }
                }
            }
        }
     }

     private static void extractTuples(List<Map<Attribute,String>> tuples,int index, Relation r) {
        Map<Attribute,List<String>> rValues = r.getData();
        Map<Attribute,String> tuple = new HashMap<>();
        for(Attribute attribute:rValues.keySet()) {
            List<String> valuesForAttribute = rValues.get(attribute);
            tuple.put(attribute,valuesForAttribute.get(index));
        }
        tuples.add(tuple);
     }


     private static void continueExtraction(List<Map<Attribute,String>> tuples,int currentTupleIndex, List<Node> path, KeywordQuery q,int currentNodeIndex) {

        //check result extracted method
        if(!isResultExtracted(tuples,q)) {
            //if the end of the path is not reached
            if (currentNodeIndex <= path.size()) {
                //get the current tuple that can be used for the next extraction
                Map<Attribute, String> currentTuple = tuples.get(currentTupleIndex);
                //get the current node of the path
                Node currentNode = path.get(currentNodeIndex);
                //check which of the values in the tuple is an output one useful to retrieve more data
                for (Attribute a : currentTuple.keySet())
                    if (a.getAccessLimitation() == Attribute.AccessLimitation.OUTPUT)
                        //check if there is an attribute in the relation that is an input one and it has the same domain
                        for (Attribute x : currentNode.getRelation().getAttributes()) {
                            if (x.getAccessLimitation() == Attribute.AccessLimitation.INPUT && x.getDomain().equals(a.getDomain())) {
                                // get the tuple related to the attribute with the same domain
                                List<String> valuesInRelation = currentNode.getRelation().getData().get(x);
                                //check if the relation of the node contains the same value
                                String value = currentTuple.get(a);
                                if (valuesInRelation.contains(value)) {
                                    // extract the tuple from the current node
                                    int index = valuesInRelation.indexOf(value);
                                    extractTuples(tuples, index, currentNode.getRelation());
                                    //continue the extraction process
                                    continueExtraction(tuples, currentTupleIndex+1, path, q, currentNodeIndex+1);
                                }
                            }
                        }
            }
        }
     }

     private static boolean isResultExtracted(List<Map<Attribute,String>> tuples,KeywordQuery q) {
        List<String> queryValues = new ArrayList<>();
        for(Attribute a: q.getAttributeList())
            queryValues.add(a.getName());

        List<String> tuplesValues = new ArrayList<>();
        for(Map<Attribute,String> temp: tuples)
            for(Attribute a:temp.keySet())
                tuplesValues.add(temp.get(a));

        if(tuplesValues.containsAll(queryValues))
            return true;
        else
            return false;
     }
}

