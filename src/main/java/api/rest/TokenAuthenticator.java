/**
 * 
 */



package api.rest;

public interface TokenAuthenticator {
    
    public TokenInfo.Data authenticate(String token);
    
}
