package com.tengfei.f9framework.completioncontributor.dictionary;

public class F9HtmlAttributeValueDocumentation {
    private String attributeValue;
    private String description;

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getDescription() {
        if(description == null) {
            return "";
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
