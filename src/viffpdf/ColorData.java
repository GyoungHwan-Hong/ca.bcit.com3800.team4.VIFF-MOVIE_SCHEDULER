package viffpdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import java.util.ArrayList;

public class ColorData extends RowData {
  private String sectionCode;
  
  private int cyan;
  
  private int magenta;
  
  private int yellow;
  
  private int black;
  
  private int red;
  
  private int green;
  
  private int blue;
  
  private Color color;
  
  public ColorData(ArrayList<String> data) throws IllegalArgumentException {
    setData(data);
  }
  
  public void setData(ArrayList<String> data) throws IllegalArgumentException {
    if (data.size() != 5)
      throw new IllegalArgumentException(
          data.size() + " data elements given. Must be 5 data elements per row."); 
    super.setData(data);
    try {
      this.sectionCode = ((String)data.get(0)).replace(" ", "");
      this.cyan = Integer.parseInt(((String)data.get(1)).replace(" ", ""));
      this.magenta = Integer.parseInt(((String)data.get(2)).replace(" ", ""));
      this.yellow = Integer.parseInt(((String)data.get(3)).replace(" ", ""));
      this.black = Integer.parseInt(((String)data.get(4)).replace(" ", ""));
      this.color = (Color)new DeviceCmyk(this.cyan, this.magenta, this.yellow, this.black);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Rows must be in format {sectionCode, cyan, magenta, yellow, black}");
    } 
  }
  
  public Color getColor() {
    return this.color;
  }
  
  public void setColor(Color color) {
    this.color = color;
  }
  
  public int getRed() {
    return this.red;
  }
  
  public void setRed(int red) {
    this.red = red;
  }
  
  public int getGreen() {
    return this.green;
  }
  
  public void setGreen(int green) {
    this.green = green;
  }
  
  public int getBlue() {
    return this.blue;
  }
  
  public void setBlue(int blue) {
    this.blue = blue;
  }
  
  public String getSectionCode() {
    return this.sectionCode;
  }
  
  public void setSectionCode(String sectionCode) {
    this.sectionCode = sectionCode;
    this.data.set(0, sectionCode);
  }
  
  public int getCyan() {
    return this.cyan;
  }
  
  public void setCyan(int cyan) {
    this.cyan = cyan;
    this.data.set(1, cyan);
  }
  
  public int getMagenta() {
    return this.magenta;
  }
  
  public void setMagenta(int magenta) {
    this.magenta = magenta;
    this.data.set(2, magenta);
  }
  
  public int getYellow() {
    return this.yellow;
  }
  
  public void setYellow(int yellow) {
    this.yellow = yellow;
    this.data.set(3, yellow);
  }
  
  public int getBlack() {
    return this.black;
  }
  
  public void setBlack(int black) {
    this.black = black;
    this.data.set(4, black);
  }
}
