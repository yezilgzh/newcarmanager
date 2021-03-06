package car.manager;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor
{
  private static final String PERSPECTIVE_ID = "car.customer.manager.perspective";

  public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
  {
    return new ApplicationWorkbenchWindowAdvisor(configurer);
  }

  public String getInitialWindowPerspectiveId() {
    return "car.customer.manager.perspective";
  }
}