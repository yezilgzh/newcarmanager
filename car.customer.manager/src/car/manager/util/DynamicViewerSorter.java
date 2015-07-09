package car.manager.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * 动态排序器，利用反射，性能可能不好
 * 
 * @author rongzhi_li
 * 
 */
public class DynamicViewerSorter extends ViewerSorter {
	Logger logger = Logger.getLogger(getClass());
	private String sortColumn;
	private boolean desc;

	private Map<String, Method> classMethods = new HashMap<String, Method>();

	/**
	 * 解析一个要排序的class类型
	 * 
	 * @param clazz
	 *            需要排序的class类需
	 */
	public DynamicViewerSorter(Class<?> clazz) {
		// logger.debug(clazz);
		Method[] mts = clazz.getMethods();
		for (Method m : mts) {
			if (m.getName().startsWith("get")) {
				String name = m.getName().substring(3);
				classMethods.put(name, m);
			} else if (m.getName().startsWith("is")) {
				String name = m.getName().substring(2);
				classMethods.put(name, m);
			}
		}
	}

	/**
	 * 放入要排序的字段，只能针对单字段排序，连续set单双次会产生正逆排序
	 * 
	 * @param sortColumn
	 */
	public void setSortColumn(String sortColumn) {
		if (sortColumn.equals(this.sortColumn)) {
			desc = !desc;
		} else {
			desc = false;
		}
		this.sortColumn = sortColumn;

	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn, boolean desc) {
		this.desc = desc;
		this.sortColumn = sortColumn;

	}

	/**
	 * 配接口调用的方法，逻辑为null派最后，其余如果是Comparable 按Comparable拍 最后按对象的toString()排
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		int d = 1;
		if (!desc) {
			d = -1;
		} else {
			d = 1;
		}
		if (null == sortColumn) {
			return d * super.compare(viewer, e1, e2);
		} else {
			Method method = classMethods.get(sortColumn);
			if (null == method) {
				return d * super.compare(viewer, e1, e2);
			}

			try {
				Object value1 = method.invoke(e1);
				Object value2 = method.invoke(e2);
				// null永远排在最后
				if (value1 == null || value1.equals("--")) {
					return 999;
				}
				if (value2 == null || value2.equals("--")) {
					return 0;
				}
				if (value1 instanceof Comparable
						&& value2 instanceof Comparable) {
					Comparable c1 = (Comparable) value1;
					Comparable c2 = (Comparable) value2;
					return d * c1.compareTo(c2);
				}
				return value1.toString().compareTo(value2.toString());
			} catch (Exception e) {
				logger.error("错误:", e);
				return d * super.compare(viewer, e1, e2);
			}
		}

	}

	@Override
	public boolean isSorterProperty(Object element, String property) {
		logger.info("isSorterProperty:" + element + " " + property);
		return true;
	}
}
