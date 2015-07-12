package car.manager.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import car.manager.Activator;
import car.manager.viewpart.TodayInStoreViewPart;

public class RefreshAction extends Action
{
  private TodayInStoreViewPart part;

  public RefreshAction(TodayInStoreViewPart part)
  {
    this.part = part;
  }

  public void run()
  {
    this.part.refreshViewer();
  }

  public String getText()
  {
    return "Ë¢ÐÂ";
  }

  public ImageDescriptor getImageDescriptor()
  {
    return Activator.getImageDescriptor("platform:/plugin/car.customer.manager/icons/refresh.png");
  }
}