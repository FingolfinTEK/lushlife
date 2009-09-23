package org.lushlife.negroni;

import org.lushlife.negroni.core.impl.scope.NegroniScope;

public interface Identifier<T>
{
   Object getKey();

   NegroniScope getScope();

}
