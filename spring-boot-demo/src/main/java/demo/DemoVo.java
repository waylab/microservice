package demo;

public class DemoVo {

	private String test;

	
	public DemoVo() {
		super();
	}

	public DemoVo(String test) {
		super();
		this.test = test;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	@Override
	public String toString() {
		return "DemoVo [test=" + test + "]";
	}
	
	
}
