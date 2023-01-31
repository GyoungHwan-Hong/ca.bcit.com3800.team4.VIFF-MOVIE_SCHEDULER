package viffpdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;

public class SectionParser extends Parser {
  private TreeMap<String, SectionData> sectionMap = new TreeMap<>();
  
  public SectionParser() {
    super("Sections", false);
  }
  
  public SectionParser(File file) throws FileNotFoundException, IllegalArgumentException {
    super("Sections", false);
    setData(file);
  }
  
  public String setData(File file) throws FileNotFoundException {
    String message = "";
    ArrayList<ArrayList<String>> sectionStrings = parse(file, "\t");
    this.sectionMap.clear();
    int row = 1;
    for (ArrayList<String> sectionData : sectionStrings) {
      try {
        SectionData section = new SectionData(sectionData);
        if (this.sectionMap.containsKey(section.getCode()))
          throw new IllegalArgumentException(
              "Key \"" + section.getCode() + "\" repeated.  Meaning ambiguous."); 
        this.sectionMap.put(section.getCode(), section);
      } catch (IllegalArgumentException e) {
        setValidState(false);
        throw new IllegalArgumentException("Row #" + row + ":\n\t" + e.getMessage());
      } 
      row++;
    } 
    setValidState(true);
    return message;
  }
  
  public TreeMap<String, SectionData> getSectionList() {
    return this.sectionMap;
  }
}
