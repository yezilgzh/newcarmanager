package car.manager.util;

import org.apache.log4j.Logger;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

public class ViewUtil {
	static Logger log = Logger.getLogger(ViewUtil.class);

	public static void openView(final String viewId) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				try {
					IViewPart view = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.findView(viewId);
					if (view == null) {
						view = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage()
								.showView(viewId);
					}
					if (view != null)
						view.setFocus();
				} catch (Exception e) {
					ViewUtil.log.error("[视图未正常打开]viewId=" + viewId, e);
				}
			}
		});
	}

	public static IViewPart openViewPart(String viewId, String strategyId) {
		IViewPart view = null;
		try {
			view = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(viewId, strategyId, 3);
			if (view != null)
				view.setFocus();
		} catch (Exception e) {
			log.error("视图未正常打开，。。。", e);
		}
		return view;
	}
}