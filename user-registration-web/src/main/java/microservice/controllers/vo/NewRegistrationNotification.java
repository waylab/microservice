package microservice.controllers.vo;

public class NewRegistrationNotification {
	private String id;
	private String emailAddress;
	private String password;
	
	public NewRegistrationNotification(String id, String emailAddress,
			String password) {
		super();
		this.id = id;
		this.emailAddress = emailAddress;
		this.password = password;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
