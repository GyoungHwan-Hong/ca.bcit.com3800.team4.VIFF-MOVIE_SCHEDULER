package viffpdf;

import java.util.ArrayList;

public class SectionData extends RowData {
  private String name;
  
  private String code;
  
  public SectionData(ArrayList<String> data) throws IllegalArgumentException {
    setData(data);
  }
  
  public void setData(ArrayList<String> data) throws IllegalArgumentException {
    if (data.size() != 2)
      throw new IllegalArgumentException(
          data.size() + " data elements given. Must be 2 data elements per row."); 
    super.setData(data);
    this.name = data.get(0);
    this.code = data.get(1);
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
    this.data.set(0, name);
  }
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String code) {
    this.code = code;
    this.data.set(1, code);
  }
}
