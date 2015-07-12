package car.manager;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class CarPerspective
  implements IPerspectiveFactory
{
  public void createInitialLayout(IPageLayout layout)
  {
    layout.setEditorAreaVisible(false);
  }
}