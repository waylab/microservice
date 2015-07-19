package microservice.dustview;

import java.io.File;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;
import org.mozilla.javascript.tools.shell.Main;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class __DustView extends AbstractTemplateView {
	 
	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String basePath = getServletContext().getContextPath();
			
			Context cx = ContextFactory.getGlobal().enterContext();
			cx.setOptimizationLevel(-1);
 
			Global global = Main.getGlobal();
			if (!global.isInitialized()) {
				global.init(cx);
			}
 
			Scriptable scope = cx.initStandardObjects(global);
			scope.put("basePath", scope, basePath);
//			scope.put("basePath", scope, getClass().getResource("/dustjs/dust-loader.js").toString().split("/dustjs/dust-loader.js")[0]);
			scope.put("modelMap", scope, model);
			scope.put("template", scope, FileUtils.readFileToString(new File(basePath + getUrl())));
//			scope.put("template", scope, IOUtils.toString(getClass().getResourceAsStream(getUrl())));
			URL resource = getClass().getResource("/dustjs/dust-loader.js");
			Main.processFile(cx, scope, getClass().getResource("/dustjs/dust-loader.js").toString());
 
			response.getOutputStream().write(scope.get("result", scope).toString().getBytes());
		} finally {
			Context.exit();
		}
	}
 
}