package gquery.sample.shared;

public class TwitterLexer extends AbstractTwitterLexer {
	protected static final String URL_LINK = "<a class=\"twitter-timeline-link\" href=\"@url@\">@url@</a>";
	protected static final String USER_URL_LINK = "<a class=\"twitter-atreply\" href=\"http://twitter.com/@user@\">@@user@</a>";
	protected static final String HASH_TAG_URL_LINK = "<a class=\"twitter-hashtag\" href=\"http://twitter.com/#!/search?q=%23@tag_name@\">#@tag_name@</a>";

	public static String twitterLink(String html) {
		TwitterLexer lexer = new TwitterLexer();
		return lexer.addTwitterLink(html);
	}

	protected String appendAnchorToUrl(String url) {
		return URL_LINK.replace("@url@", url);
	}

	protected String appendAnchorToUser(String userName) {
		return USER_URL_LINK.replace("@user@", userName);
	}

	protected String appendAnchorToHashtag(String tagName) {
		return HASH_TAG_URL_LINK.replace("@tag_name@", tagName);
	}
}
