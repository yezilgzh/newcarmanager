package car.manager.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

public class ItemsNumberForTable
{
  private static HashMap<TableViewer, List<String>> numberLabelForTableViewer = new HashMap();

  public static void setItemsNumber(TableViewer tableViewer) {
    List numberLabel = new ArrayList();
    numberLabelForTableViewer.remove(tableViewer);
    TableItem[] tableItem = tableViewer.getTable().getItems();
    for (int i = 0; i < tableItem.length; i++) {
      String num = String.valueOf(i + 1);
      tableItem[i].setText(num);
      numberLabel.add(num);
    }
    numberLabelForTableViewer.put(tableViewer, numberLabel);
  }

  private static HashMap<TableViewer, List<String>> getNumberLabelForTableViewer() {
    return numberLabelForTableViewer;
  }

  public static void setItemsNumberFromZero(TableViewer tableViewer, int index) {
    List numberLabel = new ArrayList();
    numberLabelForTableViewer.remove(tableViewer);
    TableItem[] tableItem = tableViewer.getTable().getItems();
    for (int i = 0; i < tableItem.length; i++) {
      String num = String.valueOf(i);
      tableItem[i].setText(index, num);
      numberLabel.add(num);
    }
    numberLabelForTableViewer.put(tableViewer, numberLabel);
  }
}