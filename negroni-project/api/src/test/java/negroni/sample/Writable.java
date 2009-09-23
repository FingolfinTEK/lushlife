package negroni.sample;

import org.lushlife.negroni.annotation.MixinImplementedBy;

@MixinImplementedBy(WritableMixin.class)
public interface Writable
{
   public void write(String str);

}
