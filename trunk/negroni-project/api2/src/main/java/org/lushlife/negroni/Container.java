package org.lushlife.negroni;

/**
 * mix-inのクラスをインスタンス化の役割を担う。 DIコンテナと連携させたい場合には継承する。
 * 
 * @author Takeshi Kondo
 */
public interface Container {
	public <T> T getInstance(Class<T> clazz);
}
