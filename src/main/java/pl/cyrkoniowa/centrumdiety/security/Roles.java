package pl.cyrkoniowa.centrumdiety.security;

/**
 * Enum definiujący role użytkowników w systemie.
 * Zawiera podstawowe role: PATIENT (Pacjent), DIETITIAN (Dietetyk) i ADMIN (Administrator).
 */
public enum Roles {
    /** Rola pacjenta */
    PATIENT,
    /** Rola dietetyka */
    DIETITIAN,
    /** Rola administratora */
    ADMIN;

    /**
     * Zwraca nazwę roli bez prefiksu.
     *
     * @return nazwa roli (np. "PATIENT", "DIETITIAN", "ADMIN")
     */
    public String getRoleName() {
        return this.name();
    }

    /**
     * Zwraca nazwę roli z prefiksem "ROLE_" wymaganym przez Spring Security.
     *
     * @return nazwa roli z prefiksem (np. "ROLE_PATIENT", "ROLE_DIETITIAN", "ROLE_ADMIN")
     */
    public String getRoleNameWithPrefix() {
        return "ROLE_" + this.name();
    }
}
