package ex.gtwitter.share;

abstract public class AbstractTwitterLexer {

	protected enum Status {
		NORMAL, AT_MARK, URL, HUSHTAG, TAG
	}

	public AbstractTwitterLexer() {
		super();
	}

	public String addLink(String tweet) {
		tweet += " ";
		Status s = Status.NORMAL;
		int i = -1;
		char[] v = tweet.toCharArray();
		StringBuilder sb = new StringBuilder();
		StringBuilder userName = new StringBuilder();
		StringBuilder tagName = new StringBuilder();
		StringBuilder url = new StringBuilder();

		ROOP: while (i < v.length - 1) {
			i++;
			switch (s) {
			case NORMAL:
				if (v[i] == '<') {
					s = Status.TAG;
					sb.append(v[i]);
					continue ROOP;
				}
				if (v[i] == '@') {
					s = Status.AT_MARK;
					continue ROOP;
				}
				if (v[i] == '#') {
					s = Status.HUSHTAG;
					continue ROOP;
				}
				if (v[i] == 'h') {
					if (v.length >= i + 8) {
						String tmp = new String(v, i, 8);
						if (tmp.startsWith("http://")) {
							s = Status.URL;
							url = new StringBuilder();
							url.append("http://");
							i += url.length() - 1;
							continue ROOP;
						}
						if (tmp.startsWith("https://")) {
							s = Status.URL;
							url = new StringBuilder();
							url.append("https://");
							i += url.length() - 1;
							continue ROOP;
						}
					}
				}
				sb.append(v[i]);
				break;
			case TAG:
				sb.append(v[i]);
				if (v[i] == '>') {
					s = Status.NORMAL;
					continue ROOP;
				}
				break;
			case AT_MARK:
				if (!isLetterOrDigit(v[i])) {
					sb.append(user(userName.toString()));
					s = Status.NORMAL;
					continue ROOP;
				}
				userName.append(v[i]);
				break;
			case HUSHTAG:
				if (!isLetterOrDigit(v[i])) {
					sb.append(hashTag(tagName.toString()));
					s = Status.NORMAL;
					continue ROOP;
				}
				tagName.append(v[i]);
				break;
			case URL:
				if (isWhitespace(v[i])) {
					sb.append(url(url.toString()));
					s = Status.NORMAL;
					continue ROOP;
				}
				url.append(v[i]);
				break;
			}
		}
		// 追加したスペースを消す
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	private boolean isLetterOrDigit(char c) {
		if ('a' <= c && c < 'z') {
			return true;
		}
		if ('A' <= c && c < 'Z') {
			return true;
		}
		if ('0' <= c && c < '9') {
			return true;
		}
		return false;
	}

	private boolean isWhitespace(char c) {
		if (c == ' ') {
			return true;
		}
		if (c == '\t') {
			return true;
		}
		if (c == '\n') {
			return true;
		}
		if (c == '\r') {
			return true;
		}
		return false;
	}

	abstract protected String url(String string);

	abstract protected String hashTag(String string);

	abstract protected String user(String string);

}