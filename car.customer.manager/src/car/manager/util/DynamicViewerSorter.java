package car.manager.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class DynamicViewerSorter extends ViewerSorter
{
  Logger logger = Logger.getLogger(getClass());
  private String sortColumn;
  private boolean desc;
  private Map<String, Method> classMethods = new HashMap();

  public DynamicViewerSorter(Class<?> clazz)
  {
    Method[] mts = clazz.getMethods();
    for (Method m : mts)
      if (m.getName().startsWith("get")) {
        String name = m.getName().substring(3);
        this.classMethods.put(name, m);
      } else if (m.getName().startsWith("is")) {
        String name = m.getName().substring(2);
        this.classMethods.put(name, m);
      }
  }

  public void setSortColumn(String sortColumn)
  {
    if (sortColumn.equals(this.sortColumn))
      this.desc = (!this.desc);
    else {
      this.desc = false;
    }
    this.sortColumn = sortColumn;
  }

  public String getSortColumn()
  {
    return this.sortColumn;
  }

  public void setSortColumn(String sortColumn, boolean desc) {
    this.desc = desc;
    this.sortColumn = sortColumn;
  }

  public int compare(Viewer viewer, Object e1, Object e2)
  {
    int d = 1;
    if (!this.desc)
      d = -1;
    else {
      d = 1;
    }
    if (this.sortColumn == null) {
      return d * super.compare(viewer, e1, e2);
    }
    Method method = (Method)this.classMethods.get(this.sortColumn);
    if (method == null) {
      return d * super.compare(viewer, e1, e2);
    }
    try
    {
      Object value1 = method.invoke(e1, new Object[0]);
      Object value2 = method.invoke(e2, new Object[0]);

      if ((value1 == null) || (value1.equals("--"))) {
        return 999;
      }
      if ((value2 == null) || (value2.equals("--"))) {
        return 0;
      }
      if (((value1 instanceof Comparable)) && 
        ((value2 instanceof Comparable))) {
        Comparable c1 = (Comparable)value1;
        Comparable c2 = (Comparable)value2;
        return d * c1.compareTo(c2);
      }
      return value1.toString().compareTo(value2.toString());
    } catch (Exception e) {
      this.logger.error("´íÎó:", e);
    }return d * super.compare(viewer, e1, e2);
  }

  public boolean isSorterProperty(Object element, String property)
  {
    this.logger.info("isSorterProperty:" + element + " " + property);
    return true;
  }
}