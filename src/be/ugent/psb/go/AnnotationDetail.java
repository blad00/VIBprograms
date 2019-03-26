package be.ugent.psb.go;

public class AnnotationDetail {
	
	private String gene_id;
	private int go;
	private boolean is_shown;
	private String desc;
	
	public String getGene_id() {
		return gene_id;
	}
	public void setGene_id(String gene_id) {
		this.gene_id = gene_id;
	}
	public int getGo() {
		return go;
	}
	public void setGo(int go) {
		this.go = go;
	}
	public boolean isIs_shown() {
		return is_shown;
	}
	public void setIs_shown(boolean is_shown) {
		this.is_shown = is_shown;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
