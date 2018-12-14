package me.alex.tryitout;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.git.GitConfigurationSourceBuilder;
import org.cfg4j.source.reload.strategy.PeriodicalReloadStrategy;

import java.util.concurrent.TimeUnit;

public class Configurator {

  private final String firstName;
  private final String middleName;
  private final String lastName;
  private final String drink;
  private final String eat;

  @Inject
  public Configurator(@Named("configRepo") String repo) {
    ConfigurationSource source = new GitConfigurationSourceBuilder()
            .withRepositoryURI(repo)
            .build();

    // Create configuration provider backed by the source
    ConfigurationProvider provider = new ConfigurationProviderBuilder()
            .withConfigurationSource(source)
            .withReloadStrategy(new PeriodicalReloadStrategy(1, TimeUnit.MINUTES))
            .build();

    // Display me favorite drink!
    firstName = provider.getProperty("me.first", String.class);
    middleName = provider.getProperty("me.middle", String.class);
    lastName = provider.getProperty("me.last", String.class);
    drink = provider.getProperty("me.favorite.drink", String.class);
    eat = provider.getProperty("me.favorite.food", String.class);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getDrink() {
    return drink;
  }

  public String getEat() {
    return eat;
  }
}
