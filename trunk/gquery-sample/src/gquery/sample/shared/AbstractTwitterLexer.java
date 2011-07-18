package gquery.sample.shared;

abstract public class AbstractTwitterLexer {

	protected enum ParceContext {
		BASE, USER, URL, HASHTAG, HTML_TAG
	}

	public String addAnchor(String tweet) {
		// 最後を強制的にスペースにして、
		// 終了処理を単純化する
		tweet += " ";
		//
		ParceContext c = ParceContext.BASE;

		// インデクス
		int i = -1;
		char[] v = tweet.toCharArray();

		// テンポラリ領域
		StringBuilder output = new StringBuilder();
		StringBuilder userName = new StringBuilder();
		StringBuilder tagName = new StringBuilder();
		StringBuilder url = new StringBuilder();

		ROOP: while (i < v.length - 1) {
			i++;
			switch (c) {
			case BASE:
				// HTMLのタグの場合
				if (v[i] == '<') {
					c = ParceContext.HTML_TAG;
					output.append(v[i]);
					continue ROOP;
				}
				// ユーザの場合
				if (v[i] == '@') {
					userName = new StringBuilder();
					c = ParceContext.USER;
					continue ROOP;
				}
				// ハッシュタグの場合
				if (v[i] == '#') {
					tagName = new StringBuilder();
					c = ParceContext.HASHTAG;
					continue ROOP;
				}
				// URLのリンクの場合
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

			// HTMLのタグの中では何もしない
			case HTML_TAG:
				output.append(v[i]);
				if (v[i] == '>') {
					c = ParceContext.BASE;
					continue ROOP;
				}
				break;
			case USER:
				// ユーザ名に使えない文字だったら、ユーザ名を確定する
				if (!isLetterOrDigitOrUnderbar(v[i])) {
					output.append(appendAnchorToUser(userName.toString()));
					output.append(v[i]);
					c = ParceContext.BASE;
					continue ROOP;
				}
				userName.append(v[i]);
				break;
			case HASHTAG:
				// ハッシュタグに使えない文字だったら、ハッシュタグを確定する
				if (!isLetterOrDigitOrUnderbar(v[i])) {
					output.append(appendAnchorToHashtag(tagName.toString()));
					output.append(v[i]);
					c = ParceContext.BASE;
					continue ROOP;
				}
				tagName.append(v[i]);
				break;
			case URL:
				// ホワイトスペースがあったら、URLを確定する
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
		// 最初に追加したスペースを消す
		output.delete(output.length() - 1, output.length());
		return output.toString();
	}

	/**
	 * 英数字またはアンダーバーかどうかを判定する
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
	 * URLに使って良い文字列かどうかを判定する
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
	 * URLにアンカーを追加する
	 * 
	 * @param url
	 *           URLの文字列
	 * @return アンカーが追加されたURL
	 */
	abstract protected String appendAnchorToUrl(String url);

	/**
	 * ハッシュタグにアンカーを追加する
	 * 
	 * @param url
	 *           ハッシュタグの文字列
	 * @return アンカーが追加されたハッシュタグ
	 */
	abstract protected String appendAnchorToHashtag(String hashtag);

	/**
	 * ユーザにアンカーを追加する
	 * 
	 * @param url
	 *           ユーザの文字列
	 * @return アンカーが追加されたユーザ
	 */
	abstract protected String appendAnchorToUser(String user);

}