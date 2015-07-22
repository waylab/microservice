package microservice.dustview;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class DustView extends AbstractTemplateView {

	public void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {

		String templateName = getUrl().substring("/WEB-INF/views/".length());
		String file = "/dustjsviews/" + templateName;

		TemplateSource templateSource = new ClasspathSource();
		templateSource.setResource(file);
		
		DustTemplateRenderer dustTemplateRenderer= dustTemplateRendererHolder().get();
		dustTemplateRenderer.compileTemplate(templateName, templateSource);
		
		String output = dustTemplateRenderer.renderTemplate(templateName, toJSObject(model));
		try {
			response.getOutputStream().write(output.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object toJSObject(Map<String, Object> javaMap) {
		@SuppressWarnings("unchecked")
		Map<String, Object> jsObject = (Map<String, Object>) dustTemplateRendererHolder().get().createObject();
		for(String key : javaMap.keySet()){
			Object value = javaMap.get(key);
			if(value instanceof BindingResult){
				BindingResult br = (BindingResult) value;
				Map<String, Object> toMap = null;
				if (!br.getFieldErrors().isEmpty()) {
					List<FieldError> list = br.getFieldErrors();
					toMap = new HashMap<String, Object>();
					for (FieldError i : list) {
						toMap.put(i.getField(), (Object) i.getDefaultMessage());
					}
				
					Map<String, Object> fieldErrors = (Map<String, Object>) toJSObject(toMap);
					jsObject.put("fieldErrors", fieldErrors);
				}
				if (!br.getGlobalErrors().isEmpty()) {
					List<ObjectError> globalErrors = br.getGlobalErrors();
					jsObject.put("globalErrors", globalErrors);
				}
			}else{
				jsObject.put(key, value);
			}
		}
		return jsObject;
	}

	public ThreadLocal<DustTemplateRenderer> dustTemplateRendererHolder(){
		return new ThreadLocal<DustTemplateRenderer>() {
			@Override
			protected DustTemplateRenderer initialValue(){
				return new DustTemplateRenderer();
			}
		};
	}
}