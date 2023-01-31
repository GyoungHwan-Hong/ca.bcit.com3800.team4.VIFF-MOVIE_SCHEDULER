package viffpdf;

import java.util.ArrayList;
import java.util.Scanner;

public class VenueData extends RowData implements Comparable<VenueData> {
  private String nameLong;
  
  private String nameShort;
  
  private String code;
  
  public VenueData(ArrayList<String> data) throws IllegalArgumentException {
    setData(data);
  }
  
  public void setData(ArrayList<String> data) throws IllegalArgumentException {
    if (data.size() != 3)
      throw new IllegalArgumentException(
          data.size() + " data elements given. Must be 3 data elements per row."); 
    super.setData(data);
    this.nameLong = data.get(0);
    this.nameShort = data.get(1);
    this.code = data.get(2);
  }
  
  public String getNameLong() {
    return this.nameLong;
  }
  
  public void setNameLong(String nameLong) {
    this.nameLong = nameLong;
    this.data.set(0, nameLong);
  }
  
  public String getNameShort() {
    return this.nameShort;
  }
  
  public void setNameShort(String nameShort) {
    this.nameShort = nameShort;
    this.data.set(1, nameShort);
  }
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String code) {
    this.code = code;
    this.data.set(2, code);
  }
  
  public String toString() {
    return this.nameShort;
  }
  
  public int compareTo(VenueData that) {
    Scanner scanThis = new Scanner(this.nameShort);
    Scanner scanThat = new Scanner(that.nameShort);
    while (scanThis.hasNext()) {
      int result;
      if (!scanThat.hasNext()) {
        scanThis.close();
        scanThat.close();
        return -1;
      } 
      if (scanThis.hasNextInt() && scanThat.hasNextInt()) {
        result = scanThis.nextInt() - scanThat.nextInt();
      } else {
        result = scanThis.next().compareTo(scanThat.next());
      } 
      if (result != 0) {
        scanThis.close();
        scanThat.close();
        return result;
      } 
    } 
    if (scanThat.hasNext()) {
      scanThis.close();
      scanThat.close();
      return 1;
    } 
    scanThis.close();
    scanThat.close();
    return 0;
  }
}
