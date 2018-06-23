package leftovers.datahelper.xueqiuspider.helper;

/**
 * Created by Hiki on 2017/5/13.
 */
public class UrlBuilder {

    private StringBuilder url;

    public UrlBuilder(String originUrl){
        this.url = new StringBuilder(originUrl);
        this.url.append("?");
    }

    public UrlBuilder addParam(String key, String value){
        this.url.append(key).append("=").append(value).append("&");
        return this;
    }

    public UrlBuilder addParam(String key, int value){
        this.url.append(key).append("=").append(value).append("&");
        return this;
    }

    public UrlBuilder addParam(String key, long value){
        this.url.append(key).append("=").append(value).append("&");
        return this;
    }

    public String build() {
        return this.url.substring(0, url.length() - 1);
    }

}
