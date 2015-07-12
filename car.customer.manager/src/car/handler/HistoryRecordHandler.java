package car.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class HistoryRecordHandler extends AbstractHandler
{
  public Object execute(ExecutionEvent event)
    throws ExecutionException
  {
    try
    {
      PlatformUI.getWorkbench().getActiveWorkbenchWindow()
        .getActivePage().showView("car.customer.manager.historyquery");
    } catch (PartInitException localPartInitException) {
    }
    return null;
  }
}	