package microservice.dustview;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.Await;
import scala.concurrent.Promise;
import scala.concurrent.duration.Duration;
import scala.concurrent.impl.Promise.DefaultPromise;

public class DustTemplateRenderer {

	Logger logger = LoggerFactory.getLogger(DustTemplateRenderer.class);

	private ScriptEngine engine;
	private Object dust;
	private Object myCallback;
	private Object outputHolder;
	private Invocable invocable;
	private Set<String> loadedTemplates;
	private Map<String, Object> compiledTemplates;
	
	public DustTemplateRenderer() {
		logger.debug("Creating new engine!!!");
		
		ScriptEngineManager engineManager = new ScriptEngineManager();
		engine = engineManager.getEngineByName("nashorn");

		try{
			engine.eval(new InputStreamReader(DustView.class.getResourceAsStream("/dustjs/dust-full.js")));
			engine.eval(new InputStreamReader(DustView.class.getResourceAsStream("/dustjs/dust-wrapper.js")));
			this.dust = engine.eval("dust");
			this.myCallback = engine.eval("myDustCallback");
			this.outputHolder = engine.eval("outputHolder");
			this.invocable = (Invocable) engine;
			this.loadedTemplates = new HashSet<String>();
		}catch(ScriptException e){
			e.printStackTrace();
		}
		
		compiledTemplates = new ConcurrentHashMap<String, Object>();
		
		ClasspathSource classpathSource = new ClasspathSource();
		classpathSource.setResource("/dustjstemplates/main_template.dust");
		compileTemplate("main_template", classpathSource);
		
		logger.debug("done creating new engine!!!");
	}
	
	public void compileTemplateFile(String templateName, File file) {
		FileSource fileSource = new FileSource();
		fileSource.setResource(file);
	    compileTemplate(templateName, fileSource);
	}
	
	public void compileTemplate(String templateName, TemplateSource source){
		if (!loadedTemplates.contains(templateName)) {
			logger.debug("loaded template " +  templateName);


			Object compiledTemplate = compiledTemplates.get(templateName);
			if (compiledTemplate == null) {
				logger.debug("Compiling template " + templateName);
				String template = source.getString();
				try {
					compiledTemplate = invocable.invokeMethod(dust, "compile", template, templateName);
				} catch (NoSuchMethodException | ScriptException e) {
					e.printStackTrace();
				}
				compiledTemplates.putIfAbsent(templateName, compiledTemplate);
				compiledTemplate = compiledTemplates.get(templateName);
			} else {
				logger.debug("Found precompiled template " + templateName);
			}
			try {
				invocable.invokeMethod(dust, "loadSource", compiledTemplate);
			} catch (NoSuchMethodException | ScriptException e) {
				e.printStackTrace();
			}
			loadedTemplates.add(templateName);
		} else {
			logger.debug("template already loaded " + templateName);
		}
	}
	
	public String renderTemplate(String templateName, Object data) {
		Promise<String> p = new DefaultPromise<String>();
	    try {
	    	MyJavaCallback done = new MyJavaCallback(p);
			invocable.invokeMethod(outputHolder, "setDone", done);
			invocable.invokeMethod(dust, "render", templateName, data, myCallback);

			Duration duration = new scala.concurrent.duration.FiniteDuration(500, java.util.concurrent.TimeUnit.MILLISECONDS);
			return Await.result(p.future(), duration);
	    } catch (Exception e1) {
			e1.printStackTrace();
		}
	    return "";
	}
	
	public Object createObject() {
		try {
			return engine.eval("Object.create(Object)");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return null;
	}

}

class FileSource implements TemplateSource{
	
	private Object resource;
	
	public String getString() {
		try {
			return FileUtils.readFileToString((File) resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setResource(Object resource) {
		this.resource = resource;
		
	}

}

class ClasspathSource implements TemplateSource{
	
	private Object resource;
	
	public String getString() {
		try {
			return IOUtils.toString(getClass().getResourceAsStream((String) resource));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setResource(Object resource) {
		this.resource = resource;
		
	}
}