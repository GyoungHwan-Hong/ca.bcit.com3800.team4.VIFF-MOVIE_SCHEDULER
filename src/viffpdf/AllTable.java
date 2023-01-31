package viffpdf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

public class AllTable {
  HashMap<String, ColorData> colorList;
  
  TreeMap<String, SectionData> sectionList;
  
  ArrayList<VenueTable> VTList = new ArrayList<>();
  
  ArrayList<VenueDateTable> VDTList = new ArrayList<>();
  
  ArrayList<DayTable> DList = new ArrayList<>();
  
  ArrayList<PageTable> PList = new ArrayList<>();
  
  ArrayList<Date> dateList = new ArrayList<>();
  
  public AllTable(ArrayList<VenueTable> v, ArrayList<VenueDateTable> vd, ArrayList<DayTable> d, ArrayList<PageTable> p, ArrayList<Date> date, HashMap<String, ColorData> cL, TreeMap<String, SectionData> sL) {
    this.VTList = v;
    this.VDTList = vd;
    this.DList = d;
    this.PList = p;
    this.dateList = date;
    this.colorList = cL;
    this.sectionList = sL;
  }
}
