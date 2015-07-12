package car.manager.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import car.manager.Activator;
import car.manager.dialog.UserUpdateDialog;
import car.manager.dto.Consumer;
import car.manager.viewpart.UserManagerViewPart;

public class UserEditAction extends Action
{
  private UserManagerViewPart part;

  public UserEditAction(UserManagerViewPart part)
  {
    this.part = part;
  }

  public void run()
  {
    Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
      .getShell();
    ISelection select = this.part.getTableViewer().getSelection();
    if ((select == null) || (select.isEmpty())) {
      MessageDialog.openInformation(shell, "提示信息", "请选择要修改的车主！");
      return;
    }
    Consumer consumer = (Consumer)((StructuredSelection)select)
      .getFirstElement();
    new UserUpdateDialog(shell, consumer).open();
  }

  public String getText()
  {
    return "修改车主信息";
  }

  public ImageDescriptor getImageDescriptor()
  {
    return Activator.getImageDescriptor("platform:/plugin/car.customer.manager/icons/record_edit.png");
  }
}