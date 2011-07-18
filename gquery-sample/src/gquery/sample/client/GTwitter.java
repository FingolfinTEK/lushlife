package gquery.sample.client;

import gquery.sample.shared.AbstractTwitterLexer;
import gquery.sample.shared.TwitterLexer;

import com.google.gwt.query.client.GQuery;

public class GTwitter extends GQuery {
	/**
	 * フィルター変数に持っておくと、コードをきれいに書ける
	 */
	static public final Class<GTwitter> GTwitter = GTwitter.class;

	static {
		// プラグインを登録する
		GQuery.registerPlugin(GTwitter.class, new GTwitterPlugin());
	}

	protected GTwitter(GQuery gq) {
		super(gq);
	}

	/**
	 * TwitterのLinkを追加する
	 * 
	 * @return
	 */
	public GTwitter twitterLink() {
		AbstractTwitterLexer lexer = new TwitterLexer();
		String values = lexer.addTwitterLink(this.html());
		this.html(values);
		return this;
	}
}
