package zaza.techblog.global.auth.response;

public interface SocialOAuthResponse {

    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}
