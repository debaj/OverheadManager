package com.debaj.overheadmanager.db.statements;

public enum Tables {
    PROFILES("profiles"),
    READINGS("readings"),
    READING_KINDS("reading_kinds"),
    BILLS("bills");
    
    private String tableName;
    
    private Tables(String tableName) {
        this.tableName = tableName;
    }
    
    public String getTableName() {
        return this.tableName;
    }
}
