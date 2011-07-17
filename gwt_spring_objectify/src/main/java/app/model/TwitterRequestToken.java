package app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import twitter4j.auth.RequestToken;

@Entity
public class TwitterRequestToken {
	@Id
	String token;
	String id;
	String tokenSecret;
	String authenticationUrl;

	public String getId() {
		return id;
	}

	public void setId(String name) {
		this.id = name;
	}

	public String getAuthenticationUrl() {
		return authenticationUrl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public void setAuthenticationUrl(String authenticationURL) {
		this.authenticationUrl = authenticationURL;
	}

	public RequestToken toRequestToken() {
		return new RequestToken(token, tokenSecret);
	}

}
