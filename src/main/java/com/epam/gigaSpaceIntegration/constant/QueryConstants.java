package com.epam.gigaSpaceIntegration.constant;

public enum QueryConstants {

    GT(">", "greater"),
    LT("<", "less"),
    EQ("=?", "equals"),
    AND("AND", "logical and operation"),
    OR("OR", "logical or operation");

    QueryConstants(String value, String description){
        this.value=value;
        this.description=description;
    }

    @Override
    public String toString(){
        return value;
    }

    private String value;
    private String description ;

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
