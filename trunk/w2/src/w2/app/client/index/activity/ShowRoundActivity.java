package w2.app.client.index.activity;

import java.util.List;

import w2.app.client.index.view.ShowRound;
import w2.app.client.place.EditRoundPlace;
import w2.app.client.place.ShowRoundPlace;
import w2.app.client.rcp.CourseRcp;
import w2.app.client.rcp.CourseRcpAsync;
import w2.app.client.rcp.GolferRcp;
import w2.app.client.rcp.GolferRcpAsync;
import w2.app.client.rcp.RoundRcp;
import w2.app.client.rcp.RoundRcpAsync;
import w2.app.model.Course;
import w2.app.model.Golfer;
import w2.app.model.Round;
import w2.app.model.ref.CourseRef;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;

public class ShowRoundActivity extends AbstractActivity {
	private CourseRcpAsync courseRcp = GWT.create(CourseRcp.class);

	private GolferRcpAsync golferRcp = GWT.create(GolferRcp.class);

	private RoundRcpAsync roundRcp = GWT.create(RoundRcp.class);

	private AcceptsOneWidget panel;
	private EventBus eventBus;
	private Long courseId;
	private ShowRound showRound;

	public ShowRoundActivity(Long courseId) {
		this.courseId = courseId;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.panel = panel;
		this.eventBus = eventBus;
		showRound = new ShowRound(this);
		showRound.setVisible(false);
		panel.setWidget(showRound);
		if (courseId != null) {
			find(courseId);
		} else {
			showRound.setVisible(true);
		}

		golferRcp.findAll(0, 1000, new AsyncCallback<List<Golfer>>() {

			@Override
			public void onSuccess(List<Golfer> result) {
				showRound.loadGolfers(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	private void find(Long courseId) {
		roundRcp.findById(courseId, new AsyncCallback<Round>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}

			@Override
			public void onSuccess(Round result) {
				showRound.setRound(result);
				showRound.setVisible(true);
			}
		});
	}

	public void add(Round round) {
		roundRcp.add(round, new AsyncCallback<Long>() {

			@Override
			public void onSuccess(Long result) {
				eventBus.fireEvent(new PlaceChangeEvent(new ShowRoundPlace(
						result)));
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void edit(Round course) {
		eventBus.fireEvent(new PlaceChangeEvent(new EditRoundPlace(course
				.getId())));
	}

	public void findCourse(CourseRef course) {
		if (course == null) {
			return;
		}
		courseRcp.find(course.getRefId(), new AsyncCallback<Course>() {

			@Override
			public void onSuccess(Course result) {
				showRound.setCourse(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

}
