package w2.extensions.gae.spring;

public class ObjectifyTransaction {
	int transactionId;

	public ObjectifyTransaction(int transactionId) {
		this.transactionId = transactionId;
	}

	public String toString() {
		return "ObjectifyTransaction:" + transactionId;
	}
}
