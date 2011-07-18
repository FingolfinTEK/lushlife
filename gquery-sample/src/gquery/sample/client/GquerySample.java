package gquery.sample.client;

import static com.google.gwt.query.client.GQuery.$;
import static gquery.sample.client.GTwitter.GTwitter;
import gquery.sample.client.json.TwitterTimeline;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GquerySample implements EntryPoint {
	public void onModuleLoad() {
		// はてブのTimeLineを取得する
		new JsonpRequestBuilder().requestObject(
		      "http://api.twitter.com/1/statuses/user_timeline/hatebu.json",
		      new AsyncCallback<JsArray<TwitterTimeline>>() {
			      @Override
			      public void onSuccess(JsArray<TwitterTimeline> result) {
			      	
			      	// Listを組み立てる
				      StringBuilder sb = new StringBuilder();
				      sb.append("<ol>");
				      for (int i = 0; i < result.length(); i++) {
					      sb.append("<li class=\"twitter-status\">")
					            .append(result.get(i).getText()).append("</li>");
				      }
				      sb.append("</ol>");

				      $("#timeline").html(sb.toString()) // <div id="timeline">にTimelineのリストを追加する
				      	.as(GTwitter).twitterLink();	  // Twitterのリンクを追加する

			      }

			      @Override
			      public void onFailure(Throwable caught) {
				      GWT.log("onFailure", caught);
			      }

		      });

	}
}
