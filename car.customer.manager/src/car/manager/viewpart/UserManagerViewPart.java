package car.manager.viewpart;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import car.manager.action.UserEditAction;
import car.manager.dialog.UserUpdateDialog;
import car.manager.dto.Consumer;
import car.manager.dto.control.DataCache;
import car.manager.dto.control.DynamicViewerTextFilter;
import car.manager.lsn.ListenerManager;
import car.manager.lsn.RefreshListener;
import car.manager.util.ItemsNumberForTable;
import car.manager.util.TimeUtil;

public class UserManagerViewPart extends ViewPart {
	public static final String ID = "car.customer.manager.usermanager";
	private Table table;
	private TableViewer tableViewer;
	private DynamicViewerTextFilter filter = new DynamicViewerTextFilter(
			Consumer.class, new String[] { "Carno", "Phone" });
	private UserEditAction editAction;

	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, 0);
		composite.setLayout(new GridLayout(1, false));

		this.tableViewer = new TableViewer(composite, 67584);

		this.table = this.tableViewer.getTable();
		this.table.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		this.table.setLinesVisible(true);
		this.table.setHeaderVisible(true);

		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_7 = tableViewerColumn_7.getColumn();
		tblclmnNewColumn_7.setWidth(76);
		tblclmnNewColumn_7.setText("序号");

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("车牌号");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("用户名");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("性别");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("车型");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_4 = tableViewerColumn_4.getColumn();
		tblclmnNewColumn_4.setWidth(100);
		tblclmnNewColumn_4.setText("颜色");

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_5 = tableViewerColumn_5.getColumn();
		tblclmnNewColumn_5.setWidth(146);
		tblclmnNewColumn_5.setText("电话");

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_6 = tableViewerColumn_6.getColumn();
		tblclmnNewColumn_6.setWidth(191);
		tblclmnNewColumn_6.setText("创建时间");
		this.tableViewer.setContentProvider(new ArrayContentProvider());
		this.tableViewer.setLabelProvider(new CustomerLabelProvider());
		this.tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection select = UserManagerViewPart.this.tableViewer
						.getSelection();
				if ((select == null) || (select.isEmpty())) {
					MessageDialog.openInformation(getViewSite().getShell(),
							"提示信息", "请选择要修改的车主！");
					return;
				}
				Consumer consumer = (Consumer) ((StructuredSelection) select)
						.getFirstElement();
				new UserUpdateDialog(getViewSite().getShell(), consumer).open();
			}
		});
		this.table.addListener(41, new Listener() {
			public void handleEvent(Event event) {
				event.width = UserManagerViewPart.this.table.getGridLineWidth();

				event.height = (int) Math.floor(event.gc.getFontMetrics()
						.getHeight() * 1.5D);
			}
		});
		this.tableViewer.setFilters(new ViewerFilter[] { this.filter });
		refreshViewer();
		createActions();
		addListener();
	}

	private void addListener() {
		ListenerManager.addListener("car.customer.manager.usermanager",
				new RefreshListener() {
					public void refresh() {
						UserManagerViewPart.this.refreshViewer();
					}

					public String getKey() {
						return "car.customer.manager.usermanager";
					}
				});
	}

	protected void createActions() {
		this.editAction = new UserEditAction(this);
		initializeToolBar();
	}

	private void initializeToolBar() {
		ToolBarManager toolbarManager = (ToolBarManager) getViewSite()
				.getActionBars().getToolBarManager();

		toolbarManager.add(new ControlContribution("filterText") {
			protected Control createControl(Composite parent) {
				Text filterText = new Text(parent, 2048);
				filterText.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						String input = ((Text) e.getSource()).getText();
						UserManagerViewPart.this.filter.setText(input);

						UserManagerViewPart.this.tableViewer.refresh();
						ItemsNumberForTable
								.setItemsNumber(UserManagerViewPart.this.tableViewer);
					}
				});
				return filterText;
			}

			protected int computeWidth(Control control) {
				return 100;
			}
		});
		toolbarManager.add(new Separator());
		toolbarManager.add(this.editAction);
	}

	private void refreshViewer() {
		this.tableViewer.setInput(DataCache.getInstance().getService()
				.getConsumerDao().get());
		this.tableViewer.refresh();
		ItemsNumberForTable.setItemsNumber(this.tableViewer);
	}

	public void setFocus() {
	}

	public void dispose() {
		ListenerManager.removeListener("car.customer.manager.usermanager");
		super.dispose();
	}

	public TableViewer getTableViewer() {
		return this.tableViewer;
	}

	class CustomerLabelProvider extends LabelProvider implements
			ITableLabelProvider, ITableFontProvider {
		CustomerLabelProvider() {
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if ((element instanceof Consumer)) {
				Consumer cr = (Consumer) element;
				if (columnIndex == 0)
					return null;
				if (columnIndex == 1)
					return cr.getCarno();
				if (columnIndex == 2)
					return cr.getName();
				if (columnIndex == 3)
					return cr.getSex() == 0 ? "男" : "女";
				if (columnIndex == 4)
					return cr.getCartype();
				if (columnIndex == 5)
					return cr.getColor();
				if (columnIndex == 6)
					return cr.getPhone();
				if (columnIndex == 7) {
					return TimeUtil.getStandardDate(cr.getCreateTime());
				}
			}

			return null;
		}

		public Font getFont(Object element, int columnIndex) {
			return null;
		}
	}
}