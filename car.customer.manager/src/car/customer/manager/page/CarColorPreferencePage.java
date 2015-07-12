package car.customer.manager.page;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import car.manager.dialog.ColorDialog;
import car.manager.dto.control.DataCache;
import car.manager.lsn.IAfter;
import car.manager.util.ItemsNumberForTable;

public class CarColorPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	public static final String ID = "car.customer.manager.colorpage";
	private Table table;
	private Button btn_add;
	private Button btn_del;
	private TableColumn tblclmnNewColumn_1;
	private TableViewerColumn tableViewerColumn_1;
	private TableViewer tableViewer;

	public CarColorPreferencePage() {
		noDefaultAndApplyButton();
	}

	public void init(IWorkbench workbench) {
	}

	protected Control createContents(Composite parent) {
		setDescription("汽车颜色维护");
		Composite main = new Composite(parent, 0);
		main.setLayout(new GridLayout(2, false));
		this.tableViewer = new TableViewer(main, 67586);

		this.table = this.tableViewer.getTable();
		this.table.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		this.table.setHeaderVisible(true);
		this.table.setLinesVisible(true);

		this.tableViewerColumn_1 = new TableViewerColumn(this.tableViewer, 0);
		this.tblclmnNewColumn_1 = this.tableViewerColumn_1.getColumn();
		this.tblclmnNewColumn_1.setWidth(74);
		this.tblclmnNewColumn_1.setText("编号");

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(144);
		tblclmnNewColumn.setText("颜色");

		Group group = new Group(main, 0);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(4, 4, false, false, 1, 1));

		this.btn_add = new Button(group, 0);
		this.btn_add.setText("增加颜色");
		this.btn_add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new ColorDialog(getShell(), new IAfter() {
					public void afterOperate(Object t) {
						CarColorPreferencePage.this.refreshViewer();
					}
				}).open();
			}
		});
		new Label(group, 0);
		this.btn_del = new Button(group, 0);

		this.btn_del.setText("删除颜色");
		this.btn_del.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				StructuredSelection select = (StructuredSelection) CarColorPreferencePage.this.tableViewer
						.getSelection();
				if (select.isEmpty()) {
					MessageDialog.openInformation(
							CarColorPreferencePage.this.getShell(), "提示信息",
							"请选择要删除的颜色");
					return;
				}
				String color = (String) select.getFirstElement();
				try {
					DataCache.getInstance().getColorDao().delete(color);
					CarColorPreferencePage.this.refreshViewer();
				} catch (Exception e1) {
					MessageDialog.openInformation(
							CarColorPreferencePage.this.getShell(), "提示信息",
							"删除颜色出错！");
				}
			}
		});
		this.tableViewer.setContentProvider(new ArrayContentProvider());
		this.tableViewer.setLabelProvider(new ColorProvider());
		refreshViewer();
		return main;
	}

	private void refreshViewer() {
		this.tableViewer.setInput(DataCache.getInstance().getColorDao().get());
		this.tableViewer.refresh();
		ItemsNumberForTable.setItemsNumber(this.tableViewer);
	}

	class ColorProvider extends LabelProvider implements ITableLabelProvider {
		ColorProvider() {
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 1)
				return element.toString();
			return null;
		}
	}
}