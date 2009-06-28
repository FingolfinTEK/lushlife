package negroni.extension.jmx;

import javax.annotation.PostConstruct;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import negroni.annotation.MixinImplementedBy;
import negroni.annotation.MustImplement;
import negroni.extension.jmx.impl.MBeanClientMixinImpl;

@MixinImplementedBy(MBeanClientMixinImpl.class)
public interface MBeanClientMixin
{

   @PostConstruct
   public void init();

   @MustImplement
   MBeanServerConnection getServer();

   @MustImplement
   ObjectName getObjectName();
}
