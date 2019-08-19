package com.epam.gigaspaceintegration.constant;

/**
 * Define the Query to write services independent of these string defination
 */
public enum QueryParameters {

    GT(">", "greater"),
    LT("<", "less"),
    EQ("=?", "equals"),
    AND("AND", "logical and operation"),
    OR("OR", "logical or operation");

    QueryParameters(String value, String description){
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
