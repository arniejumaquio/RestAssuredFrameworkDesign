package rahulshettyacademy.pojo_classes.jira_apis;

public class JiraBugAttachmentResponse {

    private String self;
    private String id;
    private String filename;
    private Author author;
    private String created;
    private int size;
    private String mimeType;
    private String content;
    private String thumbnail;

    public String getSelf() {
        return self;
    }

    public String getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public Author getAuthor() {
        return author;
    }

    public String getCreated() {
        return created;
    }

    public int getSize() {
        return size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getContent() {
        return content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

}
