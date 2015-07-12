package car.manager.viewpart;

import java.util.Date;
import java.util.List;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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

import car.manager.dialog.RecordEditDialog;
import car.manager.dto.InStoreRecord;
import car.manager.dto.control.DataCache;
import car.manager.dto.provider.CustomerLabelProvider;
import car.manager.lsn.ListenerManager;
import car.manager.lsn.RefreshListener;
import car.manager.util.DynamicViewerSorter;

public class HistoryRecordQueryViewPart extends ViewPart {
	public static final String ID = "car.customer.manager.historyquery";
	private Table table;
	private TableViewer tableViewer;
	private DynamicViewerSorter sorter = new DynamicViewerSorter(
			InStoreRecord.class);
	private Label label_profit;
	private DateChooserCombo endDate;
	private DateChooserCombo startDate;
	private Button btn_query;

	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, 0);
		composite.setLayout(new GridLayout(1, false));
		Group group = new Group(composite, 0);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(4, 4, true, true, 1, 1));

		Group group_1 = new Group(group, 0);
		group_1.setLayout(new GridLayout(5, false));
		group_1.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));

		Label lblNewLabel_1 = new Label(group_1, 0);
		lblNewLabel_1.setText("开始时间");
		this.startDate = new DateChooserCombo(group_1, 0);
		GridData gd_startDate = new GridData(16384, 16777216, false, false, 1,
				1);
		gd_startDate.widthHint = 142;
		this.startDate.setLayoutData(gd_startDate);
		this.startDate.setValue(new Date());

		Label label = new Label(group_1, 0);
		label.setText("结束时间");

		this.endDate = new DateChooserCombo(group_1, 0);
		GridData gd_endDate = new GridData(16384, 16777216, false, false, 1, 1);
		this.endDate.setValue(new Date());
		gd_endDate.widthHint = 136;
		this.endDate.setLayoutData(gd_endDate);

		this.btn_query = new Button(group_1, 0);
		GridData gd_btnNewButton = new GridData(16384, 16777216, false, false,
				1, 1);
		gd_btnNewButton.widthHint = 85;
		this.btn_query.setLayoutData(gd_btnNewButton);
		this.btn_query.setText("查询");
		this.btn_query.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if ((HistoryRecordQueryViewPart.this.startDate.getValue() == null)
						|| (HistoryRecordQueryViewPart.this.endDate.getValue() == null)) {
					MessageDialog.openInformation(
							HistoryRecordQueryViewPart.this.btn_query
									.getShell(), "提示信息", "请选择查询的开始日期和结束日期");
					return;
				}
				Date start = HistoryRecordQueryViewPart.this.startDate
						.getValue();
				Date end = HistoryRecordQueryViewPart.this.endDate.getValue();
				if (start.after(end)) {
					MessageDialog.openInformation(
							HistoryRecordQueryViewPart.this.btn_query
									.getShell(), "提示信息", "开始时间大于结束时间，请检查！");
					return;
				}
				List<InStoreRecord> records = DataCache.getInstance()
						.getService().getInStoreRecordDao()
						.getRecordsByDate(start, end);
				if (records.isEmpty()) {
					MessageDialog.openInformation(
							HistoryRecordQueryViewPart.this.btn_query
									.getShell(), "提示信息", "在指定时间区间内，未找到订单记录！");
					return;
				}

				int totalCost = 0;
				for (InStoreRecord ir : records) {
					totalCost += ir.getCost();
				}
				HistoryRecordQueryViewPart.this.label_profit.setText(String
						.valueOf(totalCost));
				HistoryRecordQueryViewPart.this.tableViewer.setInput(records);
				HistoryRecordQueryViewPart.this.tableViewer.refresh();
			}
		});
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
				event.width = HistoryRecordQueryViewPart.this.table
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
						.getActiveWorkbenchWindow().getShell(), ir, false)
						.open();
			}
		});
		addListeners();
		createActions();
	}

	protected void createActions() {
		initializeToolBar();
	}

	private void initializeToolBar() {
		ToolBarManager toolbarManager = (ToolBarManager) getViewSite()
				.getActionBars().getToolBarManager();
	}

	private void addListeners() {
		ListenerManager.addListener("car.customer.manager.historyquery",
				new RefreshListener() {
					public void refresh() {
						HistoryRecordQueryViewPart.this.refreshViewer();
					}

					public String getKey() {
						return "car.customer.manager.historyquery";
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
		ListenerManager.removeListener("car.customer.manager.historyquery");

		super.dispose();
	}

	public TableViewer getTableViewer() {
		return this.tableViewer;
	}
}