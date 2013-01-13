package com.debaj.overheadmanager.db.statements;

public enum Statements {
    
    // Reading statements
    DB_READINGS_UPDATE_READING("UPDATE readings SET value = ? WHERE _id = ?"),
    DB_READINGS_DELETE_READING("DELETE FROM readings WHERE _id = ?"),
    DB_READINGS_GET_READINGS("SELECT * FROM readings WHERE time >= ? AND time < ? AND kind = ? AND profile = ?"),
    DB_READINGS_GET_READING_VALUE("SELECT value FROM readings WHERE id = ?"),
    DB_READINGS_GET_LAST_READING("SELECT value FROM readings WHERE kind = ? AND profile = ? ORDER BY time DESC LIMIT 1"),
    
    // Profile statements
    DB_PROFILES_UPDATE_PROFILES("UPDATE profiles SET name = ?, address = ?, kind = ?, WHERE _id = ?"),
    DB_PROFILES_DELETE_PROFILE("DELETE FROM profiles WHERE _id = ?"),
    DB_PROFILES_GET_PROFILE("SELECT * FROM profiles WHERE _id = ?"),
    DB_PROFILES_GET_ALL_PROFILES("SELECT * FROM profiles"),
    
    // Bills statements
    DB_BILLS_GET_ALL_BILLS("SELECT * FROM bills"),
    DB_BILLS_GET_SETTLED_BILLS("SELECT * FROM bills WHERE paid = 1"),
    DB_BILLS_GET_DUE_BILLS("SELECT * FROM bills WHERE due_date <= ? AND paid = 0"),
    DB_BILLS_PROFILE_FRAGMENT(" AND profile = ?"),
    
    // Reading kind statements
    DB_READING_KINDS_UPDATE_READING_KIND("UPDATE reading_kinds SET name = ?, unit = ?, price = ?, discount_price = ?, enabled = ? WHERE _id = ?"),
    DB_READING_KINDS_DELETE_READING_KIND("DELETE FROM reading_kinds WHERE _id = ?"),
    DB_READING_KINDS_GET_READING_KIND("SELECT * FROM reading_kinds WHERE _id = ?");
    
    private String statement;
    
    private Statements(String statement) {
        this.statement = statement;
    }
    
    public String getStatement() {
        return this.statement;
    }
}
