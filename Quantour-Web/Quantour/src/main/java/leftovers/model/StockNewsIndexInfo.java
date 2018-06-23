package leftovers.model;

/**
 * Created by Hiki on 2017/6/11.
 */

public class StockNewsIndexInfo {

    private double relatedScore;

    private double sentimentScore;

    private String newsTitle;

    private String newsPublishSite;

    private String newsPublishTime;

    public StockNewsIndexInfo() {
    }

    public StockNewsIndexInfo(double relatedScore, double sentimentScore, String newsTitle, String newsPublishSite, String newsPublishTime) {
        this.relatedScore = relatedScore;
        this.sentimentScore = sentimentScore;
        this.newsTitle = newsTitle;
        this.newsPublishSite = newsPublishSite;
        this.newsPublishTime = newsPublishTime;
    }

    public double getRelatedScore() {
        return relatedScore;
    }

    public void setRelatedScore(double relatedScore) {
        this.relatedScore = relatedScore;
    }

    public double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsPublishSite() {
        return newsPublishSite;
    }

    public void setNewsPublishSite(String newsPublishSite) {
        this.newsPublishSite = newsPublishSite;
    }

    public String getNewsPublishTime() {
        return newsPublishTime;
    }

    public void setNewsPublishTime(String newsPublishTime) {
        this.newsPublishTime = newsPublishTime;
    }
}
