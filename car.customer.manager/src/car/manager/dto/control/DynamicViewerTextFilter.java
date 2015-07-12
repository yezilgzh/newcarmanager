package car.manager.dto.control;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class DynamicViewerTextFilter extends ViewerFilter {
	Logger logger = Logger.getLogger(DynamicViewerTextFilter.class);
	private String text;
	private Map<String, Method> methodMap = new HashMap<String, Method>();
	private Class<? extends Object> clazz;

	public DynamicViewerTextFilter(Class<? extends Object> clazz,
			String[] methodNames) {

		this.clazz = clazz;
		Method[] methods = clazz.getMethods();
		for (Method method : methods)
			for (String methodName : methodNames)
				if (((method.getName().equals("get" + methodName)) || (method
						.getName().equals("is" + methodName)))
						&& (method.getParameterTypes().length == 0)) {
					this.methodMap.put(methodName, method);
					break;
				}
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if ((this.text == null) || (this.text.trim().equals(""))) {
			return true;
		}

		if (this.clazz.isInstance(element)) {
			for (Method method : this.methodMap.values()) {
				try {
					Object value = method.invoke(element, new Object[0]);
					if ((value != null)
							&& (value.toString().contains(this.text)))
						return true;
				} catch (Exception e) {
					this.logger.error("DynamicViewerTextFilter", e);
				}
			}
		}
		return false;
	}
}