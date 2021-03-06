package model.bp;

import java.util.*;

public class SchemaJoinGraph {
    private String name;
    private List<Node> nodes;
    private Map<String,Node> nodesMap;
    private List<Arc> arcs;
    private Map<String,Arc> arcsMap;
    private Map<Node,List<Node>> nodeDestinations;
    private Map<Node,List<String>> nodeArcs;

    public SchemaJoinGraph(List<Relation> relations) {
        this.nodes = new ArrayList<>();
        this.arcs = new ArrayList<>();
        this.nodesMap = new HashMap<>();
        this.arcsMap = new HashMap<>();
        this.nodeDestinations = new HashMap<>();
        this.nodeArcs = new HashMap<>();
        addNodes(relations);
        addArcs();

    }

    public SchemaJoinGraph(Schema s) {
        this.nodes = new ArrayList<>();
        this.arcs = new ArrayList<>();
        this.nodesMap = new HashMap<>();
        this.arcsMap = new HashMap<>();
        this.nodeDestinations = new HashMap<>();
        this.nodeArcs = new HashMap<>();
        addNodes(s.getRelations());
        addArcs();
    }


    public void addNodes(List<Relation> relations) {
        for(Relation r:relations)
            if(r.getArity()>1) {
                Node n = new Node(r.getName(), r);
                this.nodes.add(n);
                this.nodesMap.put(r.getName(),n);
                this.nodeDestinations.put(n,new ArrayList<>());
                this.nodeArcs.put(n,new ArrayList<>());
            }
    }

    public void addArcs() {
        for(int i=0; i<this.nodes.size()-1;i++) {
            Node source = this.nodes.get(i);
            for(int j=i+1;j<this.nodes.size();j++) {
                Node destination = this.nodes.get(j);
                if(source.getRelation().hasSimilarDomains(destination.getRelation())) {
                    Arc direct = new Arc(source, destination);
                    Arc reverse = new Arc(destination,source);
                    this.arcs.add(direct);
                    this.arcs.add(reverse);
                    this.nodeDestinations.get(source).add(destination);
                    this.nodeDestinations.get(destination).add(source);
                    String directArc = "(" + source.getId()+","+destination.getId()+")";
                    String reverseArc = "(" + destination.getId()+","+source.getId()+")";
                    this.arcsMap.put(directArc,direct);
                    this.arcsMap.put(reverseArc,reverse);
                    this.nodeArcs.get(source).add(directArc);
                    this.nodeArcs.get(destination).add(reverseArc);
                }

            }

        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    public void setArcs(List<Arc> arcs) {
        this.arcs = arcs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPresent(Node n) {
        return this.nodesMap.containsKey(n.getRelation().getName());
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Schema Join Graph");
        sb.append("\nNodes: " + this.nodes.toString());
        sb.append("\nArcs: " + this.arcs.toString());
        return sb.toString();
    }

    public Map<String, Node> getNodesMap() {
        return nodesMap;
    }

    public void setNodesMap(Map<String, Node> nodesMap) {
        this.nodesMap = nodesMap;
    }

    public Map<String, Arc> getArcsMap() {
        return arcsMap;
    }

    public void setArcsMap(Map<String, Arc> arcsMap) {
        this.arcsMap = arcsMap;
    }

    public Map<Node, List<Node>> getNodeDestinations() {
        return nodeDestinations;
    }

    public void setNodeDestinations(Map<Node, List<Node>> nodeDestinations) {
        this.nodeDestinations = nodeDestinations;
    }

    public Map<Node, List<String>> getNodeArcs() {
        return nodeArcs;
    }

    public void setNodeArcs(Map<Node, List<String>> nodeArcs) {
        this.nodeArcs = nodeArcs;
    }
}
