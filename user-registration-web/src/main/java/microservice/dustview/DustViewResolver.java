package microservice.dustview;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

public class DustViewResolver extends AbstractTemplateViewResolver {

	public DustViewResolver(){
		setViewClass(requiredViewClass());
	}

	public Class<?> requiredViewClass() {
		return DustView.class;
	}

}
