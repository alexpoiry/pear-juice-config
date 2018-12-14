package me.alex.tryitout;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class ConfiguratorModule extends AbstractModule {
  protected void configure() {
    //bind(Configurator.class)
    //        .toInstance(new Configurator("https://github.com/alexpoiry/pear-config.git"));

    bind(String.class)
            .annotatedWith(Names.named("configRepo"))
            .toInstance("https://github.com/alexpoiry/pear-config.git");
  }
}
