package org.lushlife.negroni.extension.jmx;

import javax.annotation.PostConstruct;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.lushlife.negroni.annotation.MixinImplementedBy;
import org.lushlife.negroni.annotation.MustImplement;
import org.lushlife.negroni.extension.jmx.impl.MBeanClientMixinImpl;


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
