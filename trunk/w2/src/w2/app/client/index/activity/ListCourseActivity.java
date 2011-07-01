package w2.app.client.index.activity;

import java.util.List;

import w2.app.client.W2;
import w2.app.client.index.view.CourseTable;
import w2.app.client.place.EditCoursePlace;
import w2.app.client.place.ListCoursePlace;
import w2.app.client.rcp.CourseRcp;
import w2.app.client.rcp.CourseRcpAsync;
import w2.app.model.Course;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ListCourseActivity extends AbstractActivity {
	private CourseRcpAsync courseRcp = GWT.create(CourseRcp.class);
	private AcceptsOneWidget panel;
	private EventBus eventBus;
	int start;
	int length;
	private CourseTable table;

	public ListCourseActivity(int start, int length) {
		this.start = start;
		this.length = length;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.panel = panel;
		this.eventBus = eventBus;
		this.table = new CourseTable(this);
		panel.setWidget(table);
		find();
	}

	private void find() {
		courseRcp.findAll(start, length, new AsyncCallback<List<Course>>() {

			@Override
			public void onSuccess(List<Course> result) {
				table.loadData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void update(final Course course) {
		courseRcp.update(course, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				W2.removeMessage();
				W2.showMessage("Course " + course.getId() + "を更新しました。");
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void toEdit(Course object) {
		eventBus.fireEvent(new PlaceChangeEvent(new EditCoursePlace(object
				.getId())));
	}

	public void delete(Iterable<Course> selectedSet) {
		courseRcp.delete(selectedSet, new AsyncCallback<List<Long>>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}

			@Override
			public void onSuccess(List<Long> result) {
				W2.removeMessage();
				W2.showMessage("Course " + result + " を削除しました。");
				eventBus.fireEvent(new PlaceChangeEvent(new ListCoursePlace()));
			}
		});
	}

}
