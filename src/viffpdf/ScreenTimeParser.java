package viffpdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ScreenTimeParser extends Parser {
  ArrayList<ScreenTimeData> screenTimeList = new ArrayList<>();
  
  public ScreenTimeParser() {
    super("Screen-Times", false);
  }
  
  public ScreenTimeParser(File file) throws FileNotFoundException, IllegalArgumentException {
    super("Screen-Times", false);
    setData(file);
  }
  
  public String setData(File file) throws FileNotFoundException {
    String message = "";
    ArrayList<ArrayList<String>> screenTimeStrings = parse(file, "\t");
    this.screenTimeList.clear();
    int i = 1;
    for (ArrayList<String> screenTimeData : screenTimeStrings) {
      try {
        ScreenTimeData st = new ScreenTimeData(screenTimeData);
        if (st.getSectionCode().isEmpty())
          message = String.valueOf(message) + "Warning: Section code missing in \n\tTitle:\t" + st.getMovieName() + "\n\tId:\t" + 
            st.getMovieId() + "\n\tRow:\t" + i + "\n"; 
        this.screenTimeList.add(new ScreenTimeData(screenTimeData));
      } catch (IllegalArgumentException e) {
        setValidState(false);
        throw new IllegalArgumentException("Row #" + i + ":\n\t" + e.getMessage());
      } 
      i++;
    } 
    setValidState(true);
    return message;
  }
  
  public ArrayList<ScreenTimeData> getScreenTimeList() {
    return this.screenTimeList;
  }
}
