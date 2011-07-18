package gquery.sample.shared;

abstract public class AbstractTwitterLexer {

	protected enum ParceContext {
		BASE, USER, URL, HASHTAG, HTML_TAG
	}

	public String addAnchor(String tweet) {
		// �Ō�������I�ɃX�y�[�X�ɂ��āA
		// �I��������P��������
		tweet += " ";
		//
		ParceContext c = ParceContext.BASE;

		// �C���f�N�X
		int i = -1;
		char[] v = tweet.toCharArray();

		// �e���|�����̈�
		StringBuilder output = new StringBuilder();
		StringBuilder userName = new StringBuilder();
		StringBuilder tagName = new StringBuilder();
		StringBuilder url = new StringBuilder();

		ROOP: while (i < v.length - 1) {
			i++;
			switch (c) {
			case BASE:
				// HTML�̃^�O�̏ꍇ
				if (v[i] == '<') {
					c = ParceContext.HTML_TAG;
					output.append(v[i]);
					continue ROOP;
				}
				// ���[�U�̏ꍇ
				if (v[i] == '@') {
					userName = new StringBuilder();
					c = ParceContext.USER;
					continue ROOP;
				}
				// �n�b�V���^�O�̏ꍇ
				if (v[i] == '#') {
					tagName = new StringBuilder();
					c = ParceContext.HASHTAG;
					continue ROOP;
				}
				// URL�̃����N�̏ꍇ
				if (v[i] == 'h') {
					if (v.length >= i + 8) {
						url = new StringBuilder();
						String tmp = new String(v, i, 8);
						if (tmp.startsWith("http://")) {
							c = ParceContext.URL;
							url.append("http://");
							i += url.length() - 1;
							continue ROOP;
						}
						if (tmp.startsWith("https://")) {
							c = ParceContext.URL;
							url.append("https://");
							i += url.length() - 1;
							continue ROOP;
						}
					}
				}
				output.append(v[i]);
				break;

			// HTML�̃^�O�̒��ł͉������Ȃ�
			case HTML_TAG:
				output.append(v[i]);
				if (v[i] == '>') {
					c = ParceContext.BASE;
					continue ROOP;
				}
				break;
			case USER:
				// ���[�U���Ɏg���Ȃ�������������A���[�U�����m�肷��
				if (!isLetterOrDigitOrUnderbar(v[i])) {
					output.append(appendAnchorToUser(userName.toString()));
					output.append(v[i]);
					c = ParceContext.BASE;
					continue ROOP;
				}
				userName.append(v[i]);
				break;
			case HASHTAG:
				// �n�b�V���^�O�Ɏg���Ȃ�������������A�n�b�V���^�O���m�肷��
				if (!isLetterOrDigitOrUnderbar(v[i])) {
					output.append(appendAnchorToHashtag(tagName.toString()));
					output.append(v[i]);
					c = ParceContext.BASE;
					continue ROOP;
				}
				tagName.append(v[i]);
				break;
			case URL:
				// �z���C�g�X�y�[�X����������AURL���m�肷��
				if (!isURLCharacter(v[i])) {
					output.append(appendAnchorToUrl(url.toString()));
					output.append(v[i]);
					c = ParceContext.BASE;
					continue ROOP;
				}
				url.append(v[i]);
				break;
			}
		}
		// �ŏ��ɒǉ������X�y�[�X������
		output.delete(output.length() - 1, output.length());
		return output.toString();
	}

	/**
	 * �p�����܂��̓A���_�[�o�[���ǂ����𔻒肷��
	 */
	private boolean isLetterOrDigitOrUnderbar(char c) {
		if ('a' <= c && c <= 'z') {
			return true;
		}
		if ('A' <= c && c <= 'Z') {
			return true;
		}
		if ('0' <= c && c <= '9') {
			return true;
		}
		if ('_' == c) {
			return true;
		}
		return false;
	}

	/**
	 * URL�Ɏg���ėǂ������񂩂ǂ����𔻒肷��
	 */
	private boolean isURLCharacter(char c) {
		if (isLetterOrDigitOrUnderbar(c)) {
			return true;
		}
		char[] okList = { '%', '&', '?', '.', '_', ':', '/' };
		for (char ok : okList) {
			if (c == ok) {
				return true;
			}
		}
		return false;
	}

	/**
	 * URL�ɃA���J�[��ǉ�����
	 * 
	 * @param url
	 *           URL�̕�����
	 * @return �A���J�[���ǉ����ꂽURL
	 */
	abstract protected String appendAnchorToUrl(String url);

	/**
	 * �n�b�V���^�O�ɃA���J�[��ǉ�����
	 * 
	 * @param url
	 *           �n�b�V���^�O�̕�����
	 * @return �A���J�[���ǉ����ꂽ�n�b�V���^�O
	 */
	abstract protected String appendAnchorToHashtag(String hashtag);

	/**
	 * ���[�U�ɃA���J�[��ǉ�����
	 * 
	 * @param url
	 *           ���[�U�̕�����
	 * @return �A���J�[���ǉ����ꂽ���[�U
	 */
	abstract protected String appendAnchorToUser(String user);

}