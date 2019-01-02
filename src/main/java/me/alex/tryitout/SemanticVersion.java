package me.alex.tryitout;

public class SemanticVersion implements Comparable<SemanticVersion> {
  private final int[] versions;
  private final String modifier;

  public SemanticVersion(String version)
  {
    int modifierIdx = version.indexOf('-');
    if (modifierIdx > 0)
    {
      modifier = version.substring(modifierIdx + 1);
      version = version.substring(0, modifierIdx);
    }
    else
    {
      modifier = null;
    }

    String[] versionParts = version.split("\\.");

    versions = new int[versionParts.length];
    for (int i=0; i<versionParts.length; ++i)
    {
      if ("*".equals(versionParts[i]) || "?".equals(versionParts[i]))
      {
        versions[i] = -1;
      }
      else
      {
        versions[i] = Integer.parseInt(versionParts[i]);
      }
    }
  }

  public static int compare(String s1, String s2)
  {
    return new SemanticVersion(s1).compareTo(new SemanticVersion(s2));
  }

  public int compareTo(SemanticVersion other)
  {
    final int minVersions = Math.min(this.versions.length, other.versions.length);
    for (int i=0; i<minVersions; ++i)
    {
      final int diff = this.versions[i] - other.versions[i];
      if (diff != 0)
      {
        return diff;
      }
    }

    //check if this has more version components
    for (int i = other.versions.length; i < this.versions.length; ++i)
    {
      if (this.versions[i] > 0)
      {
        return 1;
      }
    }

    //check if other has more version components
    for (int i = this.versions.length; i < other.versions.length; ++i)
    {
      if (other.versions[i] > 0)
      {
        return -1;
      }
    }

    //both versions are equivalent, compare the modifiers
    if (this.modifier != null)
    {
      if (other.modifier != null)
      {
        return this.modifier.compareTo(other.modifier);
      }

      //if this has modifier and other does not, this is "less"
      return -1;
    }

    if (other.modifier != null)
    {
      //if this does not have modifier and other does, this is "greater"
      return 1;
    }

    return 0;
  }
}
