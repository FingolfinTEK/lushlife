package lush.application.lushup.action;

import javax.jdo.PersistenceManager;

import lush.application.lushup.model.Member;
import lushfile.core.jdo.Transactional;

import com.google.inject.Inject;

public class MemberAction {

	@Inject
	private Member member;

	@Inject
	private PersistenceManager persistenceManager;

	public String persist() {
		new Transactional(persistenceManager) {
			@Override
			protected void transactional() {
				persistenceManager.makePersistent(member);
			}
		}.invoke();
		return "/success.jsp";
	}
}
