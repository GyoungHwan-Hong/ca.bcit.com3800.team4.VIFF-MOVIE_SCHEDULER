package viffpdf;

import java.io.Serializable;
import java.util.ArrayList;

public class configSave implements Serializable {
  double dc;
  
  double dm;
  
  double dy;
  
  double dk;
  
  double bc;
  
  double bm;
  
  double by;
  
  double bk;
  
  double vc;
  
  double vm;
  
  double vy;
  
  double vk;
  
  double sc;
  
  double sm;
  
  double sy;
  
  double sk;
  
  double hc;
  
  double hm;
  
  double hy;
  
  double hk;
  
  double fc;
  
  double fm;
  
  double fy;
  
  double fk;
  
  double oc;
  
  double om;
  
  double oy;
  
  double ok;
  
  double ec;
  
  double em;
  
  double ey;
  
  double ek;
  
  int vfs;
  
  int mf;
  
  public configSave() {}
  
  public configSave(int venueFontSize, double dColorc, double dColorm, double dColory, double dColork, double bColorc, double bColorm, double bColory, double bColork, double vColorc, double vColorm, double vColory, double vColork, double sColorc, double sColorm, double sColory, double sColork, double hColorc, double hColorm, double hColory, double hColork, double fColorc, double fColorm, double fColory, double fColork, double oColorc, double oColorm, double oColory, double oColork, double eColorc, double eColorm, double eColory, double eColork, int masterFont) {
    this.vfs = venueFontSize;
    this.mf = masterFont;
    this.dc = dColorc;
    this.dm = dColorm;
    this.dy = dColory;
    this.dk = dColork;
    this.bc = bColorc;
    this.bm = bColorm;
    this.by = bColory;
    this.bk = bColork;
    this.vc = vColorc;
    this.vm = vColorm;
    this.vy = vColory;
    this.vk = vColork;
    this.sc = sColorc;
    this.sm = sColorm;
    this.sy = sColory;
    this.sk = sColork;
    this.hc = hColorc;
    this.hm = hColorm;
    this.hy = hColory;
    this.hk = hColork;
    this.fc = fColorc;
    this.fm = fColorm;
    this.fy = fColory;
    this.fk = fColork;
    this.oc = oColorc;
    this.om = oColorm;
    this.oy = oColory;
    this.ok = oColork;
    this.ec = eColorc;
    this.em = eColorm;
    this.ey = eColork;
    this.ek = eColork;
  }
  
  void set(char type, ArrayList<Double> list) {
    switch (type) {
      case 'd':
        this.dc = ((Double)list.get(0)).doubleValue();
        this.dm = ((Double)list.get(1)).doubleValue();
        this.dy = ((Double)list.get(2)).doubleValue();
        this.dk = ((Double)list.get(3)).doubleValue();
        break;
      case 'b':
        this.bc = ((Double)list.get(0)).doubleValue();
        this.bm = ((Double)list.get(1)).doubleValue();
        this.by = ((Double)list.get(2)).doubleValue();
        this.bk = ((Double)list.get(3)).doubleValue();
        break;
      case 'v':
        this.vc = ((Double)list.get(0)).doubleValue();
        this.vm = ((Double)list.get(1)).doubleValue();
        this.vy = ((Double)list.get(2)).doubleValue();
        this.vk = ((Double)list.get(3)).doubleValue();
        break;
      case 's':
        this.sc = ((Double)list.get(0)).doubleValue();
        this.sm = ((Double)list.get(1)).doubleValue();
        this.sy = ((Double)list.get(2)).doubleValue();
        this.sk = ((Double)list.get(3)).doubleValue();
        break;
      case 'h':
        this.hc = ((Double)list.get(0)).doubleValue();
        this.hm = ((Double)list.get(1)).doubleValue();
        this.hy = ((Double)list.get(2)).doubleValue();
        this.hk = ((Double)list.get(3)).doubleValue();
        break;
      case 'f':
        this.fc = ((Double)list.get(0)).doubleValue();
        this.fm = ((Double)list.get(1)).doubleValue();
        this.fy = ((Double)list.get(2)).doubleValue();
        this.fk = ((Double)list.get(3)).doubleValue();
        break;
      case 'o':
        this.oc = ((Double)list.get(0)).doubleValue();
        this.om = ((Double)list.get(1)).doubleValue();
        this.oy = ((Double)list.get(2)).doubleValue();
        this.ok = ((Double)list.get(3)).doubleValue();
        break;
      case 'e':
        this.ec = ((Double)list.get(0)).doubleValue();
        this.em = ((Double)list.get(1)).doubleValue();
        this.ey = ((Double)list.get(2)).doubleValue();
        this.ek = ((Double)list.get(3)).doubleValue();
        break;
    } 
  }
  
  public void setFontSize(int venueFontSize) {
    this.vfs = venueFontSize;
  }
  
  public void setFont(int font) {
    this.mf = font;
  }
  
  public ArrayList<Double> getD() {
    ArrayList<Double> s = new ArrayList<>();
    s.add(Double.valueOf(this.dc));
    s.add(Double.valueOf(this.dm));
    s.add(Double.valueOf(this.dy));
    s.add(Double.valueOf(this.dk));
    return s;
  }
  
  public ArrayList<Double> getB() {
    ArrayList<Double> s = new ArrayList<>();
    s.add(Double.valueOf(this.bc));
    s.add(Double.valueOf(this.bm));
    s.add(Double.valueOf(this.by));
    s.add(Double.valueOf(this.bk));
    return s;
  }
  
  public ArrayList<Double> getH() {
    ArrayList<Double> s = new ArrayList<>();
    s.add(Double.valueOf(this.hc));
    s.add(Double.valueOf(this.hm));
    s.add(Double.valueOf(this.hy));
    s.add(Double.valueOf(this.hk));
    return s;
  }
  
  public ArrayList<Double> getV() {
    ArrayList<Double> s = new ArrayList<>();
    s.add(Double.valueOf(this.vc));
    s.add(Double.valueOf(this.vm));
    s.add(Double.valueOf(this.vy));
    s.add(Double.valueOf(this.vk));
    return s;
  }
  
  public ArrayList<Double> getS() {
    ArrayList<Double> s = new ArrayList<>();
    s.add(Double.valueOf(this.sc));
    s.add(Double.valueOf(this.sm));
    s.add(Double.valueOf(this.sy));
    s.add(Double.valueOf(this.sk));
    return s;
  }
  
  public ArrayList<Double> getF() {
    ArrayList<Double> s = new ArrayList<>();
    s.add(Double.valueOf(this.fc));
    s.add(Double.valueOf(this.fm));
    s.add(Double.valueOf(this.fy));
    s.add(Double.valueOf(this.fk));
    return s;
  }
  
  public ArrayList<Double> getO() {
    ArrayList<Double> s = new ArrayList<>();
    s.add(Double.valueOf(this.oc));
    s.add(Double.valueOf(this.om));
    s.add(Double.valueOf(this.oy));
    s.add(Double.valueOf(this.ok));
    return s;
  }
  
  public ArrayList<Double> getE() {
    ArrayList<Double> s = new ArrayList<>();
    s.add(Double.valueOf(this.ec));
    s.add(Double.valueOf(this.em));
    s.add(Double.valueOf(this.ey));
    s.add(Double.valueOf(this.ek));
    return s;
  }
}
