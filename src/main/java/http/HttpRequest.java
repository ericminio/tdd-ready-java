package http;

public class HttpRequest {
    public String query;
    public String path;

    public String getValueOf(String key) {
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String keyCandidate = pair.substring(0, pair.indexOf("="));
            if (key.equalsIgnoreCase(keyCandidate)) {
                return pair.substring(pair.indexOf("=") + 1);
            }
        }
        return null;
    }
}
