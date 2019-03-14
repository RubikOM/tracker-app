package api;

import java.util.List;

public class DataType {

    private String title;
    private List<ArticleNode> titleMarkup;
    private String Dictionary;
    private String articleId;
    private List<ArticleNode> body;

    public DataType() {
    }

    public DataType(String title, List<ArticleNode> titleMarkup, String dictionary,
                    String articleId, List<ArticleNode> body) {
        this.title = title;
        this.titleMarkup = titleMarkup;
        Dictionary = dictionary;
        this.articleId = articleId;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ArticleNode> getTitleMarkup() {
        return titleMarkup;
    }

    public void setTitleMarkup(List<ArticleNode> titleMarkup) {
        this.titleMarkup = titleMarkup;
    }

    public String getDictionary() {
        return Dictionary;
    }

    public void setDictionary(String dictionary) {
        Dictionary = dictionary;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public List<ArticleNode> getBody() {
        return body;
    }

    public void setBody(List<ArticleNode> body) {
        this.body = body;
    }
}
