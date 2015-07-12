package car.manager.dto.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import car.manager.dto.CostProject;

public class ProjectProvider extends LabelProvider implements
		ITableLabelProvider {
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if ((element instanceof CostProject)) {
			CostProject cp = (CostProject) element;
			if (columnIndex == 0)
				return cp.getProjectName();
			if (columnIndex == 1)
				return String.valueOf(cp.getCost());
			if (columnIndex == 2) {
				return cp.getRemark();
			}
		}

		return null;
	}
}