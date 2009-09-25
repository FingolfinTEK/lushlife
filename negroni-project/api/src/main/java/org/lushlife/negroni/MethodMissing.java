/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.lushlife.negroni;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * 実装されていないメソッドを呼び出した場合にハンドリングするメソッド付加する。 第一引数は必ず{@link Method}を引数に取る。
 * 第二引数以降は、ハンドリング対象のメソッドに合わせて実装すします。
 * 
 * 
 * {@link MixinMethod}と共に用いる場合には、 第一引数はハンドリング対象のインスタンス、 第二引数は{@link Method}
 * を引数に取ります。第三引数以降はメソッドの引数となります。
 * 
 * @author Takeshi Kondo
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodMissing {

}
