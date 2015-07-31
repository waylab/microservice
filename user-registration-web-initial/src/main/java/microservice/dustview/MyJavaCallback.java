package microservice.dustview;

import scala.concurrent.Promise;

public class MyJavaCallback {

	private Promise<String> p;
	
	public MyJavaCallback(Promise<String> p) {
		this.p = p;
	}
	
	public Promise<String> done(Object err, Object out) {
		if (err == null){
			return p.success(out.toString());
		}else {
			return p.failure(new RuntimeException(err.toString()));
		}
	}
}