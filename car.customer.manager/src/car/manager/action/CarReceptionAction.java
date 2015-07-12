package car.manager.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PlatformUI;

import car.manager.Activator;
import car.manager.dialog.CarReceptionDialog;
import car.manager.dto.InStoreRecord;
import car.manager.lsn.IAfter;
import car.manager.viewpart.TodayInStoreViewPart;

public class CarReceptionAction extends Action {
	private TodayInStoreViewPart part;

	public CarReceptionAction(TodayInStoreViewPart part) {
		this.part = part;
	}

	public void run() {
		new CarReceptionDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(),
				new IAfter<InStoreRecord>() {
					public void afterOperate(InStoreRecord record) {
						part.refreshViewer();
					}
				}).open();
	}

	public String getText() {
		return "ÐÂ³µ½Ó´ý";
	}

	public ImageDescriptor getImageDescriptor() {
		return Activator
				.getImageDescriptor("platform:/plugin/car.customer.manager/icons/car_reception.png");
	}
}