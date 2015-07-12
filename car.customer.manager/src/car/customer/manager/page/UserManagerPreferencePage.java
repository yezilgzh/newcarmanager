package car.customer.manager.page;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class UserManagerPreferencePage extends PreferencePage
  implements IWorkbenchPreferencePage
{
  private Table table;

  public UserManagerPreferencePage()
  {
    noDefaultAndApplyButton();
  }

  public void init(IWorkbench workbench)
  {
  }

  protected Control createContents(Composite parent)
  {
    Composite main = new Composite(parent, 0);
    main.setLayout(new GridLayout(2, false));

    TableViewer tableViewer = new TableViewer(main, 67584);

    this.table = tableViewer.getTable();
    this.table.setLayoutData(new GridData(4, 4, true, true, 1, 1));
    this.table.setHeaderVisible(true);
    this.table.setLinesVisible(true);

    TableViewerColumn tableViewerColumn = new TableViewerColumn(
      tableViewer, 0);
    TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
    tblclmnNewColumn.setWidth(100);
    tblclmnNewColumn.setText("车牌号");

    TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
      tableViewer, 0);
    TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
    tblclmnNewColumn_1.setWidth(83);
    tblclmnNewColumn_1.setText("用户名");

    Composite composite = new Composite(main, 0);
    composite.setLayoutData(
      new GridData(4, 4, false, false, 
      1, 1));
    return main;
  }
}