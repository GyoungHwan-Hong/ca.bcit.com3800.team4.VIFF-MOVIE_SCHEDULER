package viffpdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class VenueParser extends Parser {
  private HashMap<String, VenueData> venueMap = new HashMap<>();
  
  public VenueParser() {
    super("Venues", false);
  }
  
  public VenueParser(File file) throws FileNotFoundException, IllegalArgumentException {
    super("Venues", false);
    setData(file);
  }
  
  public String setData(File file) throws FileNotFoundException {
    String message = "";
    ArrayList<ArrayList<String>> venueStrings = parse(file, "\t");
    this.venueMap.clear();
    int row = 1;
    for (ArrayList<String> venueData : venueStrings) {
      try {
        VenueData venue = new VenueData(venueData);
        if (this.venueMap.containsKey(venue.getCode()))
          throw new IllegalArgumentException("Key \"" + venue.getCode() + "\" repeated.  Meaning ambiguous."); 
        this.venueMap.put(venue.getCode(), venue);
      } catch (IllegalArgumentException e) {
        setValidState(false);
        throw new IllegalArgumentException("Row #" + row + ":\n\t" + e.getMessage());
      } 
      row++;
    } 
    setValidState(true);
    return message;
  }
  
  public HashMap<String, VenueData> getVenueList() {
    return this.venueMap;
  }
}
