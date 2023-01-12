package pojos;

import org.apache.juneau.annotation.Beanc;

public class User {

	private String name;
	private String job;
	
	@Beanc(properties="name,job")
	
	public User(String name, String job) {
		super();
		this.name = name;
		this.job = job;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	
	
	
}
