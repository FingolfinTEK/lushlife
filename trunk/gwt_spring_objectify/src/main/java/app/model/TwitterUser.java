package app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TwitterUser {

	@Id
	private String name;

	private String token;

	private String tokenSecret;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
