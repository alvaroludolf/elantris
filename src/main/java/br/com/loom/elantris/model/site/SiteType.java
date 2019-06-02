package br.com.loom.elantris.model.site;

public enum SiteType {

  PLAIN("Plain", "Flat, sweeping landmass that does not change much in elevation."),
  MARCH("March", ""),
  FOREST("Forest", ""),
  HILL("Hill", ""),
  MOUNTAIN("Mountain", "");

  private String name;
  private String description;

  private SiteType(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String shortDescription() {
    return name;
  }

  public String longDescription() {
    return description;
  }

  
  
}
