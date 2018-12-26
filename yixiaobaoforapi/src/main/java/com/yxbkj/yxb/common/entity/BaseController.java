package com.yxbkj.yxb.common.entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @Author Lin_J
 * @ClassName: BaseController
 * @Description:  (control基类)
 * @date 2017年10月12日 上午11:02:32
 *
 */
@ControllerAdvice
@Controller
public class BaseController implements EmbeddedServletContainerCustomizer {

	protected static Log log  = (Log) LogFactory.getLog(BaseController.class);
	protected static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	protected static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
	protected static ThreadLocal<HttpSession> session = new ThreadLocal<HttpSession>();
	protected static ThreadLocal<ServletContext> application = new ThreadLocal<ServletContext>();
	protected static ThreadLocal<Model> model = new ThreadLocal<Model>();
	@ModelAttribute
	public void init(HttpServletRequest request, HttpServletResponse response, Model model) {
		BaseController.request.set(request);
        BaseController.response.set(response);
        BaseController.session.set(request.getSession());
        BaseController.application.set(request.getServletContext());
        BaseController.model.set(model);
		request.setAttribute("systemName", YxbConstants.SYSTEM_NAME);
	}
	public String getCurrentOpenId() {
		return (String) this.getSession().getAttribute("openId");
	}

	public Map<String, String> getParamMapFromRequest(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> properties = request.getParameterMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		while (entries.hasNext()) {
			String value = "";
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value += values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			params.put(name, value);
		}
		return params;
	}

	@InitBinder
	protected void ininBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		sdf.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

    public HttpServletRequest getRequest() {
        return request.get();
    }

    public HttpServletResponse getResponse() {
        return response.get();
    }

    public HttpSession getSession() {
        return session.get();
    }

    public ServletContext getApplication() {
        return application.get();
    }

    public Model getModel() {
        return model.get();
    }
    /**
     *
     * @Auther Lin_J
     * @Title: customize
     * @Description:  (处理400、405、404)
     * @param @param container    设定文件
     * @return void    返回类型
     * @throws
     */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST,"/login.html"),
				new ErrorPage(HttpStatus.NOT_FOUND,"/page_404.html"),
				new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED,"/login.html"));
	}

	public static <T> T newAssignment(T source,HttpServletRequest request) {

		return source;
	}

	public static <T> T modifyingData(T source,HttpServletRequest request) {

		return source;
	}
}