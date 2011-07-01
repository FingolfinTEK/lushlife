package w2.app.client.index.activity;

import java.util.List;

import w2.app.client.index.view.EditRoundForm;
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

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EditRoundActivity extends AbstractActivity {
	private CourseRcpAsync courseRcp = GWT.create(CourseRcp.class);

	private GolferRcpAsync golferRcp = GWT.create(GolferRcp.class);

	private RoundRcpAsync roundRcp = GWT.create(RoundRcp.class);

	private AcceptsOneWidget panel;
	private EventBus eventBus;
	private Long courseId;
	private EditRoundForm form;

	public EditRoundActivity(Long courseId) {
		this.courseId = courseId;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.panel = panel;
		this.eventBus = eventBus;
		form = new EditRoundForm(this);
		form.setVisible(false);
		panel.setWidget(form);
		courseRcp.findAll(0, 1000, new AsyncCallback<List<Course>>() {

			@Override
			public void onSuccess(List<Course> result) {
				form.loadCourses(result);
				if (courseId != null) {
					find(courseId);
				} else {
					form.setVisible(true);
					loadGolfers();
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	private void loadGolfers() {
		golferRcp.findAll(0, 1000, new AsyncCallback<List<Golfer>>() {

			@Override
			public void onSuccess(List<Golfer> result) {
				form.loadGolfers(result);
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
				form.setCourse(result);
				form.setVisible(true);
				loadGolfers();
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

	public void update(final Round course) {
		roundRcp.update(course, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				eventBus.fireEvent(new PlaceChangeEvent(new ShowRoundPlace(
						course.getId())));
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void delete(Round course) {
	}
}
