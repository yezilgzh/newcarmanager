package car.manager.dto.provider;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import car.manager.dto.InStoreRecord;
import car.manager.util.TimeUtil;

public class CustomerLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableFontProvider, ITableColorProvider {
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if ((element instanceof InStoreRecord)) {
			InStoreRecord ir = (InStoreRecord) element;
			if (columnIndex == 0)
				return TimeUtil.getStandardDate(ir.getTime());
			if (columnIndex == 1)
				return ir.getConsumer().getCarno();
			if (columnIndex == 2)
				return String.valueOf(ir.getCost());
			if (columnIndex == 3)
				return ir.getConsumer().getName();
			if (columnIndex == 4)
				return ir.getConsumer().getCartype();
			if (columnIndex == 5)
				return ir.getConsumer().getColor();
			if (columnIndex == 6)
				return ir.getConsumer().getPhone();
			if (columnIndex == 7) {
				return ir.getProjects();
			}
		}

		return null;
	}

	public Font getFont(Object element, int columnIndex) {
		return null;
	}

	public Color getForeground(Object element, int columnIndex) {
		if (columnIndex == 2) {
			return Display.getDefault().getSystemColor(3);
		}
		return null;
	}

	public Color getBackground(Object element, int columnIndex) {
		return null;
	}
}