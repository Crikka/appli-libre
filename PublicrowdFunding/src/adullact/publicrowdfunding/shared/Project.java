package adullact.publicrowdfunding.shared;

public class Project {
	private String m_name;
	private String m_description;

	public Project(String name, String description) {
		this.m_name = name;
		this.m_description = description;
	}
	
	public String getName() {
		return m_name;
	}
	
	public String getDescription(){
		return m_description;
	}

}
