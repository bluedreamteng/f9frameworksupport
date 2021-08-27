package com.tengfei.f9framework.postcompletetemplate.templatelibray;

/**
 * @author ztf
 */
public class PostfixTemplateInfo {
    private String name;
    private String template;
    private String example;

    public String getName() {
        if(name == null) {
            return "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        if(template == null) {
            return "";
        }
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getExample() {
        if(example == null) {
            return "";
        }
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
