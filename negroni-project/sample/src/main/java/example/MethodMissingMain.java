package example;

import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;

public class MethodMissingMain {
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		// Enhancerの取得
		Enhancer enhancer = Negroni.create();
		// クラスの拡張
		Class<? extends MethodMissingSample> enhacedClass = enhancer
				.enhace(MethodMissingSample.class);
		// インスタンスの生成
		MethodMissingSample methodMissingSample = enhacedClass.newInstance();
		// メソッドの実行
		methodMissingSample.invoke();
	}
}
