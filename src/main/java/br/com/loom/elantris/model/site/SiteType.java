package br.com.loom.elantris.model.site;

public enum SiteType {

  PLAIN("Plain", "It is a flat, sweeping landmass that does not change", "much in elevation."),
  MARSH("Marsh", "It is a wetland that is dominated by herbaceous rather", "than woody plant species."),
  FOREST("Forest", "It is a large area dominated by trees."),
  HILL("Hill", "It is a landform that extends above the surrounding terrain"),
  MOUNTAIN("Mountain", "it is a large landform that rises above the surrounding", "land in a limited area");

  private String name;
  private String[] description;

  private SiteType(String name, String... description) {
    this.name = name;
    this.description = description;
  }

  public String shortDescription() {
    return name;
  }

  public String[] longDescription() {
    return description;
  }

}
