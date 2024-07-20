package com.iac.onboarding;

public class UTMLinkGenerator {

    private final String baseUrl;
    private String source;
    private String medium;
    private String campaign;
    private String term;
    private String content;

    public UTMLinkGenerator(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public UTMLinkGenerator setSource(String source) {
        this.source = source;
        return this;
    }

    public UTMLinkGenerator setMedium(String medium) {
        this.medium = medium;
        return this;
    }

    public UTMLinkGenerator setCampaign(String campaign) {
        this.campaign = campaign;
        return this;
    }

    public UTMLinkGenerator setTerm(String term) {
        this.term = term;
        return this;
    }

    public UTMLinkGenerator setContent(String content) {
        this.content = content;
        return this;
    }

    public String generateLink() {
        StringBuilder utmLink = new StringBuilder(baseUrl);
        utmLink.append("?utm_source=").append(source)
                .append("&utm_medium=").append(medium)
                .append("&utm_campaign=").append(term)
                .append("&utm_term=").append(content)
                .append("&utm_campaign=").append(campaign);

        if (term != null && !term.isEmpty()) {
            utmLink.append("&utm_term=").append(term);
        }

        if (content != null && !content.isEmpty()) {
            utmLink.append("&utm_content=").append(content);
        }

        return utmLink.toString();
    }

    public static void main(String[] args) {
        // Test usage
    }
}
