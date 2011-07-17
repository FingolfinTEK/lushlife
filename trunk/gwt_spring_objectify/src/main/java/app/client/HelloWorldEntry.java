package app.client;

import static com.google.gwt.query.client.GQuery.$;
import static ex.gtwitter.client.GTwitter.GTwitter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.GQuery;

import ex.gtwitter.client.GTwitter;
import ex.gtwitter.client.TwitterPlugin;

public class HelloWorldEntry implements EntryPoint {

	public void onModuleLoad() {
		GQuery.registerPlugin(GTwitter.class, new TwitterPlugin());
		
		$(".twitter-status").as(GTwitter).twitterLink();
	}
}
