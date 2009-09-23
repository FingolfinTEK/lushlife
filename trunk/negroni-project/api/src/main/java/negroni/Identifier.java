package negroni;

import negroni.core.impl.scope.NegroniScope;

public interface Identifier<T>
{
   Object getKey();

   NegroniScope getScope();

}
