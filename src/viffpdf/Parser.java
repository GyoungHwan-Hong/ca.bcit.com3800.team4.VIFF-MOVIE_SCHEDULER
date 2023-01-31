package viffpdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Parser {
  private String fileType;
  
  private boolean validState;
  
  public abstract String setData(File paramFile) throws FileNotFoundException;
  
  public Parser(String fileType, boolean validState) {
    this.fileType = fileType;
    this.validState = validState;
  }
  
  public ArrayList<ArrayList<String>> parse(File file, String delim) throws FileNotFoundException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    ArrayList<ArrayList<String>> output = new ArrayList<>();
    try {
      String line = br.readLine();
      while (line != null) {
        String[] data = line.split(delim);
        output.add(new ArrayList<>(Arrays.asList(data)));
        line = br.readLine();
      } 
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return output;
  }
  
  public String getFileType() {
    return this.fileType;
  }
  
  public boolean isValidState() {
    return this.validState;
  }
  
  protected void setValidState(boolean validState) {
    this.validState = validState;
  }
}
