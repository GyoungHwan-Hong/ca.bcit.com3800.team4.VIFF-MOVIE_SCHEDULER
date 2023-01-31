package viffpdf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class DayTable {
  static int count = 0;
  
  float thisHeight = 0.0F;
  
  Date dayDate;
  
  ArrayList<VenueDateTable> venueSCTList = new ArrayList<>();
  
  public DayTable(ArrayList<VenueDateTable> list, Date date) {
    this.dayDate = date;
    for (VenueDateTable vdtEntry : list) {
      if (vdtEntry.thisDate.equals(date))
        this.venueSCTList.add(vdtEntry); 
    } 
    Collections.sort(this.venueSCTList, new Comparator<VenueDateTable>() {
          public int compare(VenueDateTable a, VenueDateTable b) {
            String aName = a.thisVenue.getCode();
            String bName = b.thisVenue.getCode();
            return aName.compareTo(bName);
          }
        });
    this.thisHeight = (this.venueSCTList.size() + 2) * ((VenueDateTable)this.venueSCTList.get(0)).getHeight() + 3.0F;
    System.out.println("Day Height for " + this.dayDate + " is: " + this.thisHeight);
  }
  
  public String toString() {
    String temp = "Day " + ++count + ": " + this.dayDate.toString() + "\n";
    temp = String.valueOf(temp) + "\tHeight: " + this.thisHeight + "\n";
    for (VenueDateTable e : this.venueSCTList)
      temp = String.valueOf(temp) + "\t" + e + "\n"; 
    return temp;
  }
}
