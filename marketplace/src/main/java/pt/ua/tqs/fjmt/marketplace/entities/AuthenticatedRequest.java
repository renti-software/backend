package pt.ua.tqs.fjmt.marketplace.entities;

public class AuthenticatedRequest {

    private Authenticator authenticator;

    public AuthenticatedRequest(Authenticator authenticator) {
        this.setAuthenticator(authenticator);
    }

    public AuthenticatedRequest() {
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }
    
}