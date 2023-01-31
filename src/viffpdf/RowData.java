package viffpdf;

import java.util.ArrayList;

public abstract class RowData {
  protected ArrayList<String> data = new ArrayList<>();
  
  public RowData() {}
  
  public RowData(ArrayList<String> data) {
    this.data = data;
  }
  
  public void setData(ArrayList<String> data) {
    this.data = data;
  }
  
  public ArrayList<String> getData() {
    return this.data;
  }
}
