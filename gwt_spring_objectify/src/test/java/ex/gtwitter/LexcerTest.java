package ex.gtwitter;

import junit.framework.Assert;

import org.junit.Test;

import ex.gtwitter.share.AbstractTwitterLexer;
import ex.gtwitter.share.TwitterLexer;

public class LexcerTest {

	@Test
	public void test() {
		AbstractTwitterLexer lexer = new TwitterLexer();
		String v1 = lexer.addLink(" @user ");
		Assert.assertEquals(
				" <a class=\"twitter-atreply\" href=\"http://twitter.com/user\">@user</a>",
				v1);
		String v2 = lexer.addLink(" #sample ");
		Assert.assertEquals(
				" <a class=\"twitter-hashtag\" href=\"http://twitter.com/#!/search?q=%23sample\">#sample</a>",
				v2);
		String v3 = lexer.addLink(" http://localhost ");
		Assert.assertEquals(
				" <a class=\"twitter-timeline-link\" href=\"http://localhost\">http://localhost</a>",
				v3);
		String v4 = lexer.addLink(" https://localhost ");
		Assert.assertEquals(
				" <a class=\"twitter-timeline-link\" href=\"https://localhost\">https://localhost</a>",
				v4);

		lexer.addLink(" https: ");
		lexer.addLink(" http: ");

		String v5 = lexer.addLink(" <a href=\"@user\"></a> ");

		Assert.assertEquals(" <a href=\"@user\"></a> ", v5);

	}
}
