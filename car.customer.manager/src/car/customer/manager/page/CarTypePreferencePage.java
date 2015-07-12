package car.customer.manager.page;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class CarTypePreferencePage extends PreferencePage
  implements IWorkbenchPreferencePage
{
  public CarTypePreferencePage()
  {
  }

  public CarTypePreferencePage(String title)
  {
    super(title);
  }

  public CarTypePreferencePage(String title, ImageDescriptor image)
  {
    super(title, image);
  }

  public void init(IWorkbench workbench)
  {
  }

  protected Control createContents(Composite parent)
  {
    return null;
  }
}