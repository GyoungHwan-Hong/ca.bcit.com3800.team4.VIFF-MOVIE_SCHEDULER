package viffpdf;

import java.util.ArrayList;
import java.util.Date;

public class VenueDateTable {
  VenueData thisVenue;
  
  float thisHeight;
  
  Date thisDate;
  
  ArrayList<ScreenTimeData> thisVDT = new ArrayList<>();
  
  public VenueDateTable(VenueTable venue, Date date, float height) {
    if (venue != null) {
      this.thisVenue = venue.thisVenue;
      this.thisDate = date;
      this.thisHeight = height;
      for (ScreenTimeData e : venue.getVTList()) {
        if (e.getDate().equals(date))
          this.thisVDT.add(e); 
      } 
    } 
  }
  
  public String toString() {
    String temp = String.valueOf(this.thisVenue.getNameLong()) + "\n" + "\t" + this.thisDate + "\n";
    if (!this.thisVDT.isEmpty())
      for (ScreenTimeData e : this.thisVDT)
        temp = String.valueOf(temp) + "\t\t" + e.getMovieName() + "\n";  
    return temp;
  }
  
  public float getHeight() {
    return this.thisHeight;
  }
}
