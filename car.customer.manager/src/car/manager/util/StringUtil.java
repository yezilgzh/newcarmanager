package car.manager.util;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.FastDateFormat;

public class StringUtil
{
  private static final FastDateFormat df0 = FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss.SSS");

  public static String filterNull(String str)
  {
    if (str == null) {
      return "";
    }
    return str;
  }

  public static boolean isBlank(String input)
  {
    return (input == null) || ("".equals(input.trim()));
  }

  public static String join(Object[] arr, String separator)
  {
    if ((arr == null) || (arr.length == 0)) {
      return "";
    }
    StringBuilder sb = new StringBuilder(arr.length * 10);
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == null)
        sb.append("");
      else {
        sb.append(arr[i]);
      }
      if (i != arr.length - 1) {
        sb.append(separator);
      }
    }
    return sb.toString();
  }

  public static String join(List<? extends Object> list, String separator)
  {
    if ((list == null) || (list.size() == 0)) {
      return "";
    }
    StringBuilder sb = new StringBuilder(list.size() * 10);
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) == null)
        sb.append("");
      else {
        sb.append(list.get(i));
      }
      if (i != list.size() - 1) {
        sb.append(separator);
      }
    }
    return sb.toString();
  }

  public static String join(Object[] arr)
  {
    return join(arr, "|");
  }

  public static String getStandardDate(Date date)
  {
    if (date == null) {
      return "";
    }
    return df0.format(date);
  }
}