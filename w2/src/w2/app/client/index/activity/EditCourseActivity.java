package w2.app.client.index.activity;

import java.util.Arrays;
import java.util.List;

import w2.app.client.W2;
import w2.app.client.index.view.EditCourseForm;
import w2.app.client.place.ListCoursePlace;
import w2.app.client.rcp.CourseRcp;
import w2.app.client.rcp.CourseRcpAsync;
import w2.app.model.Course;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EditCourseActivity extends AbstractActivity {
	private CourseRcpAsync courseRcp = GWT.create(CourseRcp.class);

	private AcceptsOneWidget panel;
	private EventBus eventBus;
	private Long courseId;
	private EditCourseForm form;

	public EditCourseActivity(Long courseId) {
		this.courseId = courseId;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.panel = panel;
		this.eventBus = eventBus;
		form = new EditCourseForm(this);
		form.setVisible(false);
		panel.setWidget(form);
		if (courseId != null) {
			find(courseId);
		} else {
			form.setVisible(true);
		}
	}

	private void find(Long courseId) {
		courseRcp.find(courseId, new AsyncCallback<Course>() {

			@Override
			public void onSuccess(Course course) {
				form.setCourse(course);
				form.setVisible(true);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void add(Course course) {
		courseRcp.add(course, new AsyncCallback<Long>() {

			@Override
			public void onSuccess(Long result) {
				W2.removeMessage();
				W2.showMessage("Course " + result + "を登録しました。");
				eventBus.fireEvent(new PlaceChangeEvent(new ListCoursePlace()));
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void update(Course course) {
		courseRcp.update(course, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				eventBus.fireEvent(new PlaceChangeEvent(new ListCoursePlace()));
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void delete(Course course) {
		courseRcp.delete(Arrays.asList(course),
				new AsyncCallback<List<Long>>() {

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("onFailure", caught);
					}

					@Override
					public void onSuccess(List<Long> result) {
						W2.removeMessage();
						W2.showMessage("Course " + result + "を削除しました。");
						eventBus.fireEvent(new PlaceChangeEvent(
								new ListCoursePlace()));
					}
				});
	}
}
