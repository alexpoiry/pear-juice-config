package me.alex.tryitout;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.git.GitConfigurationSourceBuilder;
import org.cfg4j.source.reload.strategy.PeriodicalReloadStrategy;

import java.util.concurrent.TimeUnit;

public class App {

    public interface MyConfig {
        String first();
        String middle();
        String last();
    }

    public static void main( String... args ) {
        ConfigurationSource source = new GitConfigurationSourceBuilder()
                .withRepositoryURI("https://github.com/alexpoiry/pear-config.git")
                .build();

        // Create configuration provider backed by the source
        ConfigurationProvider provider = new ConfigurationProviderBuilder()
                .withConfigurationSource(source)
                .withReloadStrategy(new PeriodicalReloadStrategy(1, TimeUnit.MINUTES))
                .build();

        MyConfig myConfig = provider.bind("me", MyConfig.class);

        // Display stuff about me!
        System.out.println(myConfig.first());
        System.out.println(myConfig.middle());
        System.out.println(myConfig.last());

        // Display me favorite drink!
        System.out.println(provider.getProperty("me.favorite.drink", String.class));
    }
}
