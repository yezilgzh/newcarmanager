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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import car.manager.dialog.AddOrUpdateProjectItemDialog;
import car.manager.dto.ProjectItem;
import car.manager.dto.control.DataCache;
import car.manager.lsn.IAfter;
import car.manager.util.ItemsNumberForTable;

public class ProjectItemPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	public static final String ID = "car.customer.manager.projectitempage";
	private Table table;
	private TableViewer tableViewer;
	private Button btn_add;

	public ProjectItemPreferencePage() {
		noDefaultAndApplyButton();
	}

	public void init(IWorkbench workbench) {
	}

	protected Control createContents(Composite parent) {
		setDescription("汽车保养项目维护");
		Composite main = new Composite(parent, 0);
		main.setLayout(new GridLayout(2, false));

		this.tableViewer = new TableViewer(main, 67584);
		this.table = this.tableViewer.getTable();
		this.table.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		this.table.setHeaderVisible(true);
		this.table.setLinesVisible(true);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(55);
		tblclmnNewColumn.setText("序号");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("项目名称");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(77);
		tblclmnNewColumn_2.setText("费用");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("备注");

		Group group = new Group(main, 0);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(4, 4, false, false, 1, 1));

		this.btn_add = new Button(group, 0);
		this.btn_add.setText("增加项目");
		this.btn_add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new AddOrUpdateProjectItemDialog(ProjectItemPreferencePage.this
						.getShell(), new IAfter() {
					public void afterOperate(Object t) {
						ProjectItemPreferencePage.this.refreshViewer();
					}
				}).open();
			}
		});
		Button btn_editor = new Button(group, 0);
		btn_editor.setText("修改项目");
		btn_editor.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				StructuredSelection select = (StructuredSelection) ProjectItemPreferencePage.this.tableViewer
						.getSelection();
				if (select.isEmpty()) {
					MessageDialog.openInformation(
							ProjectItemPreferencePage.this.getShell(), "提示信息",
							"请选择要保养条目");
					return;
				}
				ProjectItem item = (ProjectItem) select.getFirstElement();
				new AddOrUpdateProjectItemDialog(ProjectItemPreferencePage.this
						.getShell(), new IAfter() {
					public void afterOperate(Object t) {
						ProjectItemPreferencePage.this.refreshViewer();
					}
				}, item).open();
			}
		});
		Button btn_del = new Button(group, 0);
		btn_del.setText("删除项目");
		btn_del.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				StructuredSelection select = (StructuredSelection) ProjectItemPreferencePage.this.tableViewer
						.getSelection();
				if (select.isEmpty()) {
					MessageDialog.openInformation(
							ProjectItemPreferencePage.this.getShell(), "提示信息",
							"请选择要保养条目");
					return;
				}
				ProjectItem item = (ProjectItem) select.getFirstElement();
				try {
					DataCache.getInstance().getProjectItemDao().delete(item);
					ProjectItemPreferencePage.this.refreshViewer();
				} catch (Exception e1) {
					MessageDialog.openInformation(
							ProjectItemPreferencePage.this.getShell(), "提示信息",
							"删除保养条目出错！");
				}
			}
		});
		this.tableViewer.setContentProvider(new ArrayContentProvider());
		this.tableViewer.setLabelProvider(new ProjectItmeLabelProvider());
		refreshViewer();
		return main;
	}

	private void refreshViewer() {
		this.tableViewer.setInput(DataCache.getInstance().getProjectItemDao()
				.get());
		this.tableViewer.refresh();
		ItemsNumberForTable.setItemsNumber(this.tableViewer);
	}

	class ProjectItmeLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		ProjectItmeLabelProvider() {
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if ((element instanceof ProjectItem)) {
				ProjectItem pi = (ProjectItem) element;
				if (columnIndex == 0)
					return null;
				if (columnIndex == 1)
					return pi.getName();
				if (columnIndex == 2)
					return String.valueOf(pi.getCost());
				if (columnIndex == 3) {
					return pi.getDesc();
				}
			}

			return null;
		}
	}
}