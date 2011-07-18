package gquery.sample.client;

import gquery.sample.shared.AbstractTwitterLexer;
import gquery.sample.shared.TwitterLexer;

import com.google.gwt.query.client.GQuery;

public class GTwitter extends GQuery {
	/**
	 * �t�B���^�[�ϐ��Ɏ����Ă����ƁA�R�[�h�����ꂢ�ɏ�����
	 */
	static public final Class<GTwitter> GTwitter = GTwitter.class;

	static {
		// �v���O�C����o�^����
		GQuery.registerPlugin(GTwitter.class, new GTwitterPlugin());
	}

	protected GTwitter(GQuery gq) {
		super(gq);
	}

	/**
	 * Twitter��Link��ǉ�����
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
