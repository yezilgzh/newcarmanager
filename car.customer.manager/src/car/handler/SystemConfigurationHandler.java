package car.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.handlers.HandlerUtil;

import car.manager.Activator;

public class SystemConfigurationHandler extends AbstractHandler
{
  public Object execute(ExecutionEvent event)
    throws ExecutionException
  {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
    openConfigurationPage(window.getShell(), null);
    return null;
  }

  public static void openConfigurationPage(Shell shell, String activatePage) {
    Image img = Activator.getImage("platform:/plugin/car.customer.manager/icons/car.png");
    Window.setDefaultImage(img);
    PreferenceDialog dialog = null;
    String[] showPreferences = { "car.customer.manager.colorpage", 
      "car.customer.manager.projectitempage" };
    dialog = PreferencesUtil.createPreferenceDialogOn(shell, 
      "car.customer.manager.projectitempage", showPreferences, null);
    dialog.getShell().setText("œµÕ≥≈‰÷√");
    dialog.getShell().setImage(Activator.getImage("platform:/plugin/car.customer.manager/icons/car.png"));
    dialog.open();
  }
}