package viffpdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class ColorParser extends Parser {
  private HashMap<String, ColorData> colorMap = new HashMap<>();
  
  public ColorParser() {
    super("colors", false);
  }
  
  public ColorParser(File file) throws FileNotFoundException {
    super("Color", false);
  }
  
  public String setData(File file) throws FileNotFoundException {
    String message = "";
    ArrayList<ArrayList<String>> colorStrings = parse(file, "=|%");
    this.colorMap.clear();
    int row = 1;
    for (ArrayList<String> colorData : colorStrings) {
      try {
        ColorData color = new ColorData(colorData);
        if (this.colorMap.containsKey(color.getSectionCode()))
          throw new IllegalArgumentException(
              "Key \"" + color.getSectionCode() + "\" repeated.  Meaning ambiguous."); 
        this.colorMap.put(color.getSectionCode(), color);
      } catch (IllegalArgumentException e) {
        setValidState(false);
        throw new IllegalArgumentException("Row #" + row + ":\n\t" + e.getMessage());
      } 
      row++;
    } 
    setValidState(true);
    return message;
  }
  
  public HashMap<String, ColorData> getColorMap() {
    return this.colorMap;
  }
}
