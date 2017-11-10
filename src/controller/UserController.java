package controller;

import model.bp.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;
import java.util.Map;


@ManagedBean
@SessionScoped
public class UserController {
    private KeywordQuery query;
    private List<Source> sources;
    private List<Relation> relations;
    private Schema schema;
    private String queryString;
    private List<Map<Attribute,String>> result;

    public String retrieveData(List<Relation> relations) {
        String[] queryParts =this.queryString.split(";");
        String tabString ="";
        if(queryParts.length==0)
            return "keywordQueryError";
        for (int i=0;i<queryParts.length-1;i++)
            tabString += queryParts[i] +"\t";
        tabString +=queryParts[queryParts.length-1];
        this.query = new KeywordQuery(tabString);
        this.relations = relations;
        this.schema = new Schema("Schema",this.relations);
        if(!DeepWeb.checkCompatibility(this.query,this.schema))
            return "notCompatible?faces-redirect=true";
        if(!DeepWeb.checkAnswerability(this.schema,query))
            return "notAnswerable?faces-redirect=true";
        this.result = DeepWeb.queryAnswerExtraction(this.query,this.schema);
        return "result?faces-redirect=true";

    }

    public KeywordQuery getQuery() {
        return query;
    }

    public void setQuery(KeywordQuery query) {
        this.query = query;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public List<Map<Attribute, String>> getResult() {
        return result;
    }

    public void setResult(List<Map<Attribute, String>> result) {
        this.result = result;
    }
}
