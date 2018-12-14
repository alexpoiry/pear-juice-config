package me.alex.tryitout;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class App {

    public static void main( String... args ) {
        Injector injector = Guice.createInjector(new ConfiguratorModule());
        Configurator config = injector.getInstance(Configurator.class);

        System.out.println(config.getFirstName());
        System.out.println(config.getMiddleName());
        System.out.println(config.getLastName());
        System.out.println(config.getDrink());
        System.out.println(config.getEat());
    }
}
