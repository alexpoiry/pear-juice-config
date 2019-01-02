package me.alex.tryitout;

import com.google.common.collect.Ordering;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.File;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class App {

  /**
   * Regex pattern to match "normal" semantic versioning of numbers and dots.
   */
  static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+\\.\\d+)(\\.\\d+)*");

  public static void main(String... args) {
        /*Injector injector = Guice.createInjector(new ConfiguratorModule());
        Configurator config = injector.getInstance(Configurator.class);

        System.out.println(config.getFirstName());
        System.out.println(config.getMiddleName());
        System.out.println(config.getLastName());
        System.out.println(config.getDrink());
        System.out.println(config.getEat());*/

    final String fprDirArg = "/mount/fortify/mergefiles";
    final File fprDir = new File(fprDirArg);

    final Set<File> compatibleOrderedDirs = identifyFPRDirs("16.10.0095", fprDir);

  }

  private static Set<File> identifyFPRDirs(final String fortifyVersion, final File fprDir) {
    final Comparator<File> comparator = Comparator.comparing(o -> new SemanticVersion(o.getName()));

    //order these in reverse semantic order, i.e. largest to smallest.
    final TreeSet<File> dirs = new TreeSet<File>(Ordering.from(comparator).reverse());

    for (File subDir : fprDir.listFiles()) {
      if (subDir.isDirectory()) {
        final String name = subDir.getName();
        if (VERSION_PATTERN.matcher(name).matches()) {
          dirs.add(subDir);
        }
      }
    }

    // now limit ourselves to values which are semantically "less than" current sca version
    final Set<File> compatibleOrderedDirs = dirs.tailSet(new File(fprDir, fortifyVersion), true);
    return compatibleOrderedDirs;
  }

}
