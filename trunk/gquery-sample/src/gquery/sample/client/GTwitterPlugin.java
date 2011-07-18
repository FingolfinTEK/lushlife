package gquery.sample.client;

import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.Plugin;

public class GTwitterPlugin implements Plugin<GTwitter> {

	@Override
	public GTwitter init(GQuery gQuery) {
		return new GTwitter(gQuery);
	}

}
