package controller;

import model.bp.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;


@ManagedBean
@SessionScoped
public class UserController {
    private KeywordQuery query;
    private List<Source> sources;
    private List<Relation> relations;
    private Schema schema;
    private KeywordQuery q;
    private String queryString;

    public String retrieveData() {
        this.q = new KeywordQuery(this.queryString);
        if(!DeepWeb.checkCompatibility(q,this.schema))
            return "notCompatible?faces-redirect=true";
        if(!DeepWeb.checkAnswerability(this.schema,q))
            return "notAnswerable?faces-redirect=true";

        //extract data with algorithm
        return "";

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

    public KeywordQuery getQ() {
        return q;
    }

    public void setQ(KeywordQuery q) {
        this.q = q;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
