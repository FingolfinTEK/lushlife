package negroni.sample;

import negroni.annotation.MixinImplementedBy;

@MixinImplementedBy(WritableMixin.class)
public interface Writable
{
   public void write(String str);

}
