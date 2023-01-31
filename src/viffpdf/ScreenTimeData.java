package viffpdf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScreenTimeData extends RowData {
  private String movieName;
  
  private String movieId;
  
  private String sectionCode;
  
  private String venueCode;
  
  private String pageNum;
  
  private Date date;
  
  private int lengthMin;
  
  private int lengthHrs;
  
  private int startTime;
  
  private int startBlock;
  
  public ScreenTimeData(ArrayList<String> data) throws IllegalArgumentException {
    setData(data);
  }
  
  public void setData(ArrayList<String> data) throws IllegalArgumentException {
    if (data.size() != 9)
      throw new IllegalArgumentException(
          data.size() + " data elements given. Must be 9 data elements per row."); 
    super.setData(data);
    try {
      this.date = (new SimpleDateFormat("MM/dd/yyyy")).parse(data.get(0));
      this.movieName = data.get(1);
      this.movieId = data.get(2);
      this.lengthMin = Integer.parseInt(data.get(3));
      this.lengthHrs = Integer.parseInt(((String)data.get(4)).replace(":", "")) / 100;
      this.sectionCode = data.get(5);
      this.startTime = Integer.parseInt(((String)data.get(6)).replace(":", "")) / 100;
      this.startBlock = getStartTime() - 570;
      this.venueCode = data.get(7);
      this.pageNum = data.get(8);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Rows must be in format {date, movieName, movieId, length(minutes), length(HH:MM:SS), sectionCode, startTime(HH:MM:SS), venueCode}");
    } catch (ParseException e) {
      throw new IllegalArgumentException("Date must be in format MM/DD/YY");
    } 
  }
  
  public Date getDate() {
    return this.date;
  }
  
  public void setDate(Date date) {
    this.date = date;
    this.data.set(0, date);
  }
  
  public String getMovieName() {
    if (this.movieName.length() * 5 > getLengthMin()) {
      this.movieName = truncateName(this.movieName);
      this.data.set(1, this.movieName);
    } 
    return this.movieName;
  }
  
  public String truncateName(String name) {
    int firstColumn = name.indexOf(":");
    int firstSlash = name.indexOf("/");
    int firstDash = name.indexOf("-");
    int firstComma = name.indexOf(",");
    int first = name.indexOf(" ");
    int second = name.indexOf(" ", name.indexOf(" ") + 1);
    if (firstColumn >= 0) {
      name = String.valueOf(name.substring(0, firstColumn + 1)) + "...";
    } else if (firstSlash >= 0) {
      name = String.valueOf(name.substring(0, firstSlash)) + "...";
    } else if (firstDash >= 0) {
      name = String.valueOf(name.substring(0, firstDash)) + "...";
    } else if (firstComma >= 0) {
      name = String.valueOf(name.substring(0, firstComma)) + "...";
    } else if (second >= 1 && first >= 1) {
      name = String.valueOf(name.substring(0, second)) + "...";
    } else if (second < 0 && first >= 1) {
      name = String.valueOf(name.substring(0, first)) + "...";
    } else {
      name = name.substring(0, 8);
    } 
    return name;
  }
  
  public void setMovieName(String movieName) {
    this.movieName = movieName;
    this.data.set(1, movieName);
  }
  
  public String getMovieId() {
    return this.movieId;
  }
  
  public void setMovieId(String movieId) {
    this.movieId = movieId;
    this.data.set(2, movieId);
  }
  
  public String getSectionCode() {
    return this.sectionCode;
  }
  
  public void setSectionCode(String sectionCode) {
    this.sectionCode = sectionCode;
    this.data.set(5, sectionCode);
  }
  
  public String getVenueCode() {
    return this.venueCode;
  }
  
  public void setVenueCode(String venueCode) {
    this.venueCode = venueCode;
    this.data.set(7, venueCode);
  }
  
  public int getLengthMin() {
    return this.lengthMin;
  }
  
  public void setLengthMin(int lengthMin) {
    this.lengthMin = lengthMin;
    this.data.set(3, lengthMin);
  }
  
  public int getLengthHrs() {
    return this.lengthHrs;
  }
  
  public void setLengthHrs(int lengthHrs) {
    this.lengthHrs = lengthHrs;
    this.data.set(4, lengthHrs);
  }
  
  public int getStartTime() {
    int hours = this.startTime / 100;
    int minutes = this.startTime % 100 + hours * 60;
    return minutes;
  }
  
  public int getStartBlock() {
    return this.startBlock;
  }
  
  public String getTimeDetails() {
    int hours = this.startTime / 100;
    int minutes = this.startTime % 100;
    String stringMin = (new StringBuilder(String.valueOf(minutes))).toString();
    String amPm = "am";
    String pageNumber = this.pageNum;
    if (hours >= 12) {
      amPm = "pm";
      if (hours != 12)
        hours -= 12; 
    } 
    if (minutes < 10)
      stringMin = "0" + minutes; 
    String result = String.valueOf(hours) + ":" + stringMin + amPm + " " + this.lengthMin + "min p" + this.pageNum;
    if (pageNumber.equals("NaN"))
      result = String.valueOf(hours) + ":" + stringMin + amPm + " " + this.lengthMin + "min"; 
    return result;
  }
  
  public void setStartTime(int startTime) {
    this.startTime = startTime;
    this.data.set(6, startTime);
  }
  
  public void setPageNum(String pageNumber) {
    this.pageNum = pageNumber;
    this.data.set(8, pageNumber);
  }
  
  public String getPageNum() {
    return this.pageNum;
  }
  
  public String toString() {
    return this.movieName;
  }
}
