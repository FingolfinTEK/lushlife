package ex.gtwitter.client;

import com.google.gwt.query.client.GQuery;

import ex.gtwitter.share.AbstractTwitterLexer;
import ex.gtwitter.share.TwitterLexer;

public class GTwitter extends GQuery {
	static public final Class<GTwitter> GTwitter = GTwitter.class;

	protected GTwitter(GQuery gq) {
		super(gq);
	}

	public GTwitter twitterLink() {
		AbstractTwitterLexer lexer = new TwitterLexer();
		String values = lexer.addLink(this.html());
		this.html(values);
		return this;
	}
}
