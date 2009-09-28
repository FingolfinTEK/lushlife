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

import org.lushlife.stla.Debug;
import org.lushlife.stla.Error;
import org.lushlife.stla.Trace;

/**
 * @author Takeshi Kondo
 */
public enum LogMsgNGLN {
	@Error("delegete method not found {0}")
	NGLN00001,

	@Debug("accept [{0},{1}] [{2}]")
	NGLN00002,

	@Debug("Mixin class owner[{0}] mixined[{1}]")
	NGLN00003,

	@Trace("call method [{0}]: return [{1}]")
	NGLN00004,

	@Error("Illegal Annotation @MissingMethod method[{0}],args[{1}]")
	NGLN00005

}
