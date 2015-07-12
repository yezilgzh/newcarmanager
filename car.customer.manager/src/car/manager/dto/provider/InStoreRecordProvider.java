package car.manager.dto.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import car.manager.dto.InStoreRecord;
import car.manager.util.TimeUtil;

public class InStoreRecordProvider extends LabelProvider
  implements ITableLabelProvider
{
  public Image getColumnImage(Object element, int columnIndex)
  {
    return null;
  }

  public String getColumnText(Object element, int columnIndex)
  {
    if ((element instanceof InStoreRecord)) {
      InStoreRecord isr = (InStoreRecord)element;
      if (columnIndex == 0) {
        return TimeUtil.getStandardDate(isr.getTime());
      }
    }

    return null;
  }
}