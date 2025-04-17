package pl.cyrkoniowa.centrumdiety.security;

public enum Roles {
    PATIENT,
    DIETITIAN,
    ADMIN;

    public String getRoleName() {
        return this.name();
    }

    public String getRoleNameWithPrefix() {
        return "ROLE_" + this.name();
    }
}
