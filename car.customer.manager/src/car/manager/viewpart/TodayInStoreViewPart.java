package car.manager.viewpart;

import java.util.List;

import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import car.manager.action.CarReceptionAction;
import car.manager.action.HistoryRecordAction;
import car.manager.action.ReceptionEditAction;
import car.manager.action.RefreshAction;
import car.manager.dialog.RecordEditDialog;
import car.manager.dto.InStoreRecord;
import car.manager.dto.control.DataCache;
import car.manager.dto.provider.CustomerLabelProvider;
import car.manager.lsn.ListenerManager;
import car.manager.lsn.RefreshListener;
import car.manager.util.DynamicViewerSorter;

public class TodayInStoreViewPart extends ViewPart {
	public static final String ID = "car.customer.manager.customerview";
	private Table table;
	private TableViewer tableViewer;
	private DynamicViewerSorter sorter = new DynamicViewerSorter(
			InStoreRecord.class);
	private Label label_profit;
	private CarReceptionAction receptionAction;
	private ReceptionEditAction editAction;
	private RefreshAction refreshAction;
	private HistoryRecordAction historyAction;

	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, 0);
		composite.setLayout(new GridLayout(1, false));
		Group group = new Group(composite, 0);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		this.tableViewer = new TableViewer(group, 68352);

		this.table = this.tableViewer.getTable();
		this.table.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		this.table.setHeaderVisible(true);
		this.table.setLinesVisible(true);

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_5 = tableViewerColumn_5.getColumn();
		tblclmnNewColumn_5.setWidth(200);
		tblclmnNewColumn_5.setText("接待时间");
		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("车牌号");

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_6 = tableViewerColumn_6.getColumn();
		tblclmnNewColumn_6.setWidth(100);
		tblclmnNewColumn_6.setText("费用总计");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("车主姓名");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(84);
		tblclmnNewColumn_2.setText("车型");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setWidth(85);
		tblclmnNewColumn_3.setText("颜色");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_4 = tableViewerColumn_4.getColumn();
		tblclmnNewColumn_4.setWidth(173);
		tblclmnNewColumn_4.setText("手机号");

		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_7 = tableViewerColumn_7.getColumn();
		tblclmnNewColumn_7.setWidth(137);
		tblclmnNewColumn_7.setText("保养项目");

		Composite composite_1 = new Composite(group, 0);
		composite_1.setLayout(new GridLayout(3, false));
		composite_1.setLayoutData(new GridData(4, 4, true, false, 1, 1));

		Label lblNewLabel = new Label(composite_1, 0);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));
		lblNewLabel.setText("总收入");

		this.table.addListener(41, new Listener() {
			public void handleEvent(Event event) {
				event.width = TodayInStoreViewPart.this.table
						.getGridLineWidth();

				event.height = (int) Math.floor(event.gc.getFontMetrics()
						.getHeight() * 1.5D);
			}
		});
		this.label_profit = new Label(composite_1, 0);
		this.label_profit.setAlignment(131072);
		this.label_profit.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));
		this.label_profit.setForeground(SWTResourceManager.getColor(3));
		GridData gd_lblNewLabel_1 = new GridData(131072, 16777216, false,
				false, 1, 1);
		gd_lblNewLabel_1.widthHint = 101;
		this.label_profit.setLayoutData(gd_lblNewLabel_1);

		Label lblNewLabel_2 = new Label(composite_1, 0);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));
		lblNewLabel_2.setText("元");
		this.tableViewer.setContentProvider(new ArrayContentProvider());
		this.tableViewer.setLabelProvider(new CustomerLabelProvider());
		this.tableViewer.setSorter(this.sorter);
		this.sorter.setSortColumn("Time", false);

		this.tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				Object select = ((StructuredSelection) event.getSelection())
						.getFirstElement();
				InStoreRecord ir = (InStoreRecord) select;
				new RecordEditDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), ir).open();
			}
		});
		refreshViewer();
		addListeners();
		createActions();
	}

	protected void createActions() {
		this.receptionAction = new CarReceptionAction(this);
		this.editAction = new ReceptionEditAction(this);
		this.historyAction = new HistoryRecordAction(this);

		this.refreshAction = new RefreshAction(this);
		initializeToolBar();
	}

	private void initializeToolBar() {
		ToolBarManager toolbarManager = (ToolBarManager) getViewSite()
				.getActionBars().getToolBarManager();
		toolbarManager.add(new Separator());
		toolbarManager.add(this.receptionAction);
		toolbarManager.add(this.editAction);
		toolbarManager.add(this.historyAction);

		toolbarManager.add(this.refreshAction);
	}

	private void addListeners() {
		ListenerManager.addListener("car.customer.manager.customerview",
				new RefreshListener() {
					public void refresh() {
						TodayInStoreViewPart.this.refreshViewer();
					}

					public String getKey() {
						return "car.customer.manager.customerview";
					}
				});
	}

	public void refreshViewer() {
		List<InStoreRecord> input = DataCache.getInstance().getRecords();
		this.tableViewer.setInput(input);
		int account = 0;
		for (InStoreRecord ir : input) {
			account += ir.getCost();
		}

		this.label_profit.setText(String.valueOf(account));
		this.tableViewer.refresh();
	}

	public void setFocus() {
	}

	public void dispose() {
		ListenerManager.removeListener("car.customer.manager.customerview");

		super.dispose();
	}

	public TableViewer getTableViewer() {
		return this.tableViewer;
	}
}