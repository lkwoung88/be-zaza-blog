package zaza.techblog.global.auth.dto;

public interface SocialOAuthResponse {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}
