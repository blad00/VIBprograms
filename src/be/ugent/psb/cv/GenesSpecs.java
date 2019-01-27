package be.ugent.psb.cv;

public class GenesSpecs {

	private String geneName;
	private int mrnas;
	private int exones;
	private int intrones;
	private int cds;
	
	
	
	public GenesSpecs() {
		
		this.geneName = "";
		this.mrnas = 0;
		this.exones = 0;
		this.intrones = 0;
		this.cds = 0;
	}
	public String getGeneName() {
		return geneName;
	}
	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}
	public int getMrnas() {
		return mrnas;
	}
	public void setMrnas(int mrnas) {
		this.mrnas = mrnas;
	}
	public int getExones() {
		return exones;
	}
	public void setExones(int exones) {
		this.exones = exones;
	}
	public int getIntrones() {
		return intrones;
	}
	public void setIntrones(int intrones) {
		this.intrones = intrones;
	}
	public int getCds() {
		return cds;
	}
	public void setCds(int cds) {
		this.cds = cds;
	}
	
// plus one

	public void plusMrnas() {
		this.mrnas++;
	}
	public void plusExones() {
		this.exones++;
	}
	public void plusIntrones() {
		this.intrones++;
	}
	public void plusCds() {
		this.cds++;
	}
	
	
	
}
