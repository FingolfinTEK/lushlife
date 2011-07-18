package gquery.sample;

import gquery.sample.shared.TwitterLexer;
import junit.framework.Assert;

import org.junit.Test;

public class LexcerTest {

	@Test
	public void lexerTest() {
		TwitterLexer lexer = new TwitterLexer();
		String v1 = lexer.addAnchor(" @user");
		Assert.assertEquals(
		      " <a class=\"twitter-atreply\" href=\"http://twitter.com/user\">@user</a>",
		      v1);
		String v2 = lexer.addAnchor(" #sample");
		Assert.assertEquals(
		      " <a class=\"twitter-hashtag\" href=\"http://twitter.com/#!/search?q=%23sample\">#sample</a>",
		      v2);
		String v3 = lexer.addAnchor(" http://localhost");
		Assert.assertEquals(
		      " <a class=\"twitter-timeline-link\" href=\"http://localhost\">http://localhost</a>",
		      v3);
		String v4 = lexer.addAnchor(" https://localhost");
		Assert.assertEquals(
		      " <a class=\"twitter-timeline-link\" href=\"https://localhost\">https://localhost</a>",
		      v4);

		String v5 = lexer.addAnchor(" <a href=\"@user\"></a> ");

		Assert.assertEquals(" <a href=\"@user\"></a> ", v5);

	}
}
