package car.manager.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import car.manager.Activator;
import car.manager.dto.InStoreRecord;
import car.manager.util.ViewUtil;
import car.manager.viewpart.TodayInStoreViewPart;

public class HistoryRecordAction extends Action
{
  private TodayInStoreViewPart part;

  public HistoryRecordAction(TodayInStoreViewPart part)
  {
    this.part = part;
  }

  public void run()
  {
    Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
      .getShell();
    ISelection st = this.part.getTableViewer().getSelection();
    if ((st == null) || (st.isEmpty())) {
      MessageDialog.openInformation(shell, "提示信息", "请选择要查询的订单信息");
      return;
    }
    Object select = ((StructuredSelection)st).getFirstElement();
    InStoreRecord ir = (InStoreRecord)select;
    ViewUtil.openViewPart("car.customer.manager.historyrecord", ir.getConsumer()
      .getCarno());
  }

  public String getText()
  {
    return "进店记录";
  }

  public ImageDescriptor getImageDescriptor()
  {
    return Activator.getImageDescriptor("platform:/plugin/car.customer.manager/icons/query.png");
  }
}