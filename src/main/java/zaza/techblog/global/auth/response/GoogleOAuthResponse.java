package zaza.techblog.global.auth.response;

import java.util.Map;

public class GoogleOAuthResponse implements SocialOAuthResponse {

    private final Map<String, Object> attribute;

    public GoogleOAuthResponse(Map<String, Object> attribute) {

        this.attribute = attribute;
    }

    @Override
    public String getProvider() {

        return "google";
    }

    @Override
    public String getProviderId() {

        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {

        return attribute.get("email").toString();
    }

    @Override
    public String getName() {

        return attribute.get("name").toString();
    }
}
