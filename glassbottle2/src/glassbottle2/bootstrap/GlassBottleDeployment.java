package glassbottle2.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.jboss.webbeans.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.webbeans.bootstrap.spi.Deployment;

public class GlassBottleDeployment implements Deployment
{

   BeanModuleBeanDeploymentArchive disc;

   public GlassBottleDeployment(BeanModuleBeanDeploymentArchive disc)
   {
      this.disc = disc;
   }

   @Override
   public List<BeanDeploymentArchive> getBeanDeploymentArchives()
   {
      List<BeanDeploymentArchive> a = new ArrayList<BeanDeploymentArchive>();
      a.add(disc);
      return a;
   }

   @Override
   public BeanDeploymentArchive loadBeanDeploymentArchive(Class<?> beanClass)
   {
      return disc;
   }

}
