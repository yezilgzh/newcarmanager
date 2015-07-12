package car.manager.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import car.manager.Activator;
import car.manager.dialog.RecordEditDialog;
import car.manager.dto.InStoreRecord;
import car.manager.viewpart.TodayInStoreViewPart;

public class ReceptionEditAction extends Action
{
  private TodayInStoreViewPart part;

  public ReceptionEditAction(TodayInStoreViewPart part)
  {
    this.part = part;
  }

  public void run()
  {
    Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
      .getShell();
    ISelection st = this.part.getTableViewer().getSelection();
    if ((st == null) || (st.isEmpty())) {
      MessageDialog.openInformation(shell, "提示信息", "请选择要修改的订单");
      return;
    }
    Object select = ((StructuredSelection)st).getFirstElement();
    InStoreRecord ir = (InStoreRecord)select;
    new RecordEditDialog(shell, ir).open();
  }

  public String getText()
  {
    return "订单修改";
  }

  public ImageDescriptor getImageDescriptor()
  {
    return Activator.getImageDescriptor("platform:/plugin/car.customer.manager/icons/record_edit.png");
  }
}