package car.manager.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import car.manager.dao.RecordService;
import car.manager.dto.Consumer;
import car.manager.dto.CostProject;
import car.manager.dto.InStoreRecord;
import car.manager.dto.control.DataCache;
import car.manager.dto.provider.InStoreRecordProvider;
import car.manager.dto.provider.ProjectProvider;

public class HistoryRecordDialog extends Dialog {
	private Text text_carno;
	private TableViewer inStoreViewer;
	private Table table;
	private TableViewer projectViewer;
	private Table table_1;
	private String carNo;
	private Label label_totalcost;

	protected HistoryRecordDialog(Shell parentShell, String carno) {
		super(parentShell);
		this.carNo = carno;
	}

	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("进店记录");
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(1, false));

		Group group = new Group(composite, 0);
		group.setEnabled(false);
		group.setLayout(new GridLayout(2, false));
		GridData gd_group = new GridData(4, 4, true, false, 1, 1);
		gd_group.heightHint = 37;
		group.setLayoutData(gd_group);

		Label lblNewLabel = new Label(group, 0);
		lblNewLabel.setLayoutData(new GridData(131072, 16777216, false, false,
				1, 1));
		lblNewLabel.setText("车牌号");

		this.text_carno = new Text(group, 2048);
		this.text_carno.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));
		this.text_carno.setEnabled(false);
		Group group_2 = new Group(composite, 0);
		group_2.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		group_2.setLayout(new GridLayout(2, true));

		Group group_3 = new Group(group_2, 0);
		group_3.setText("进店记录");
		group_3.setLayout(new GridLayout(1, false));
		group_3.setLayoutData(new GridData(4, 4, true, true, 1, 1));

		this.inStoreViewer = new TableViewer(group_3, 67584);

		this.table = this.inStoreViewer.getTable();
		this.table.setHeaderVisible(true);
		this.table.setLinesVisible(true);
		this.table.setLayoutData(new GridData(4, 4, true, true, 1, 1));

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				this.inStoreViewer, 0);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(195);
		tblclmnNewColumn.setText("进店时间");
		this.inStoreViewer.setContentProvider(new ArrayContentProvider());
		this.inStoreViewer.setLabelProvider(new InStoreRecordProvider());
		this.inStoreViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						InStoreRecord select = (InStoreRecord) ((StructuredSelection) event
								.getSelection()).getFirstElement();
						if (select != null) {
							HistoryRecordDialog.this.projectViewer
									.setInput(select.getCostProjects());
							HistoryRecordDialog.this.projectViewer.refresh();
						}
					}
				});
		Group group_4 = new Group(group_2, 0);
		group_4.setText("保养记录");
		group_4.setLayout(new GridLayout(1, false));
		group_4.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		this.projectViewer = new TableViewer(group_4, 67584);

		this.table_1 = this.projectViewer.getTable();
		this.table_1.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		this.table_1.setHeaderVisible(true);
		this.table_1.setLinesVisible(true);

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				this.projectViewer, 0);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(75);
		tblclmnNewColumn_1.setText("项目");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				this.projectViewer, 0);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(77);
		tblclmnNewColumn_2.setText("费用");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				this.projectViewer, 0);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setWidth(77);
		tblclmnNewColumn_3.setText("备注");

		Group group_1 = new Group(composite, 0);
		group_1.setLayout(new GridLayout(3, false));
		group_1.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));

		Label lblNewLabel_1 = new Label(group_1, 0);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));
		lblNewLabel_1.setText("消费总计");

		this.label_totalcost = new Label(group_1, 0);
		this.label_totalcost.setAlignment(131072);
		GridData gd_label_totalcost = new GridData(131072, 16777216, false,
				false, 1, 1);
		gd_label_totalcost.widthHint = 70;
		this.label_totalcost.setLayoutData(gd_label_totalcost);
		this.label_totalcost.setForeground(SWTResourceManager.getColor(3));
		this.label_totalcost.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));

		Label lblNewLabel_3 = new Label(group_1, 0);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));
		lblNewLabel_3.setText("元");
		this.projectViewer.setContentProvider(new ArrayContentProvider());
		this.projectViewer.setLabelProvider(new ProjectProvider());
		init();
		return composite;
	}

	private void init() {
		this.text_carno.setText(this.carNo);
		RecordService service = DataCache.getInstance().getService();
		Consumer consumer = service.getConsumerWithInStoreRecords(this.carNo);
		if (consumer != null) {
			this.inStoreViewer.setInput(consumer.getInstoreRecords());
			this.inStoreViewer.refresh();
			int totalCost = 0;
			for (InStoreRecord in : consumer.getInstoreRecords()) {
				for (CostProject cost : in.getCostProjects()) {
					totalCost += cost.getCost();
				}
			}
			this.label_totalcost.setText(String.valueOf(totalCost));
		}
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, 0, IDialogConstants.OK_LABEL, true);
	}

	protected Point getInitialSize() {
		return new Point(580, 547);
	}
}