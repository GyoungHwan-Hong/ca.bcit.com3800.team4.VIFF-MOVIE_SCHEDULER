package viffpdf;

import java.util.ArrayList;

public class VenueTable {
  ArrayList<ScreenTimeData> thisTimeBlocks = new ArrayList<>();
  
  VenueData thisVenue;
  
  boolean ready = false;
  
  public VenueTable(VenueData venue, ScreenTimeParser screenTime) {
    if (venue != null && screenTime.isValidState()) {
      this.thisVenue = venue;
      ArrayList<ScreenTimeData> STDList = screenTime.getScreenTimeList();
      for (ScreenTimeData std : STDList) {
        if (std.getVenueCode().equals(venue.getCode()))
          this.thisTimeBlocks.add(std); 
      } 
    } 
  }
  
  public String toString() {
    String temp = String.valueOf(this.thisVenue.getNameLong()) + ": \n";
    if (!this.thisTimeBlocks.isEmpty())
      for (ScreenTimeData e : this.thisTimeBlocks)
        temp = String.valueOf(temp) + "\t" + e.getMovieName() + " " + e.getDate() + "\n";  
    return temp;
  }
  
  public ArrayList<ScreenTimeData> getVTList() {
    return this.thisTimeBlocks;
  }
}
