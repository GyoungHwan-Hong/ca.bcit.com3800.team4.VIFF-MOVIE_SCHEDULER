package viffpdf;

import java.util.ArrayList;

public class PageTable {
  static int dayCount = 0;
  
  static int pageCount = 1;
  
  int numOfDays = 0;
  
  final float maxHeight = 651.0F;
  
  float thisHeight = 0.0F;
  
  int pageNum;
  
  ArrayList<DayTable> dayList = new ArrayList<>();
  
  public PageTable(ArrayList<DayTable> list, int days, int page) {
    int upperBound = dayCount + days;
    this.pageNum = pageCount++;
    Status.print("Trying to allocate " + days + " days into page " + page + "...");
    float heightCounter = 0.0F;
    if (upperBound > list.size())
      upperBound = list.size(); 
    for (int n = dayCount; n < upperBound; n++) {
      heightCounter += ((DayTable)list.get(n)).thisHeight;
      if (heightCounter >= 651.0F) {
        Status.print("Too many days within one page. Emitting days on ***PAGE " + this.pageNum + "***.");
        break;
      } 
      this.dayList.add(list.get(n));
      dayCount++;
      this.numOfDays++;
    } 
    this.thisHeight = heightCounter;
  }
  
  public void addDay(DayTable day) {
    Status.print("Adding day #" + (dayCount + 1) + " to page# " + pageCount + ".\n");
    this.dayList.add(day);
    dayCount++;
  }
  
  public boolean isFull(DayTable day) {
    return (this.thisHeight + day.thisHeight > 651.0F);
  }
  
  public String toString() {
    String temp = "Page " + this.pageNum + "\n" + "Height: " + this.thisHeight + "\n";
    for (DayTable d : this.dayList)
      temp = String.valueOf(temp) + "\t" + d; 
    return temp;
  }
}
