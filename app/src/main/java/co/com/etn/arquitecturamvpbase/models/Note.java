package co.com.etn.arquitecturamvpbase.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Erika on 7/11/2017.
 */
@Root(name = "note")
public class Note implements Serializable{

    @Element(name = "to")
    private String to;

    @Element(name = "from")
    private String from;

    @Element(name = "heading")
    private String header;

    @Element(name = "body")
    private String body;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHeading() {
        return header;
    }

    public void setHeading(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
