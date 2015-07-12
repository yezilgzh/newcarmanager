package car.manager.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import car.manager.Activator;
import car.manager.dao.ColorDao;
import car.manager.dao.ConsumerDao;
import car.manager.dto.Consumer;
import car.manager.dto.CostProject;
import car.manager.dto.InStoreRecord;
import car.manager.dto.control.DataCache;
import car.manager.lsn.IAfter;
import car.manager.lsn.ListenerManager;
import car.manager.util.StringUtil;

public class RecordEditDialog extends Dialog {
	private Table table;
	private Text text_carno;
	private Text text_name;
	private Text text_phone;
	private Group group_consumer;
	private Text text_type;
	private Button btn_addProject;
	private List<CostProject> costItems = new ArrayList<CostProject>();
	private TableViewer tableViewer;
	private Label label_account;
	private InStoreRecord isr;
	private Composite main;
	private Button btn_man;
	private Button btn_woman;
	private Combo combo_color;
	private Button btn_addColor;
	private Group group_carno;
	private Button btn_edit;
	private Group group_cost;
	private Group group_project;
	private Button btn_del;
	private Button btn_record;
	private Label lblNewLabel_6;
	private boolean edit = true;

	public RecordEditDialog(Shell parentShell, InStoreRecord isr) {
		super(parentShell);
		this.isr = isr;
	}

	public RecordEditDialog(Shell parentShell, InStoreRecord isr, boolean edit) {
		super(parentShell);
		this.isr = isr;
		this.edit = edit;
	}

	protected Control createDialogArea(Composite parent) {
		parent.getShell().setText("订单详情");
		parent.getShell()
				.setImage(
						Activator
								.getImage("platform:/plugin/car.customer.manager/icons/car.png"));
		this.main = ((Composite) super.createDialogArea(parent));
		if (this.edit) {
			this.btn_edit = new Button(this.main, 0);
			GridData gd_btn_edit = new GridData(16384, 16777216, false, false,
					1, 1);
			gd_btn_edit.widthHint = 86;
			this.btn_edit.setLayoutData(gd_btn_edit);
			this.btn_edit.setText("修改订单");
			this.btn_edit.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					RecordEditDialog.this.group_project.setEnabled(true);
					RecordEditDialog.this.group_carno.setEnabled(true);
					RecordEditDialog.this.group_consumer.setEnabled(true);

					RecordEditDialog.this.text_carno.setEnabled(true);
					RecordEditDialog.this.getButton(0).setEnabled(true);
				}
			});
		}

		this.group_carno = new Group(this.main, 0);
		this.group_carno.setText("车牌信息");
		this.group_carno.setLayout(new GridLayout(3, false));
		this.group_carno.setLayoutData(new GridData(4, 16777216, true, false,
				1, 1));

		Label lblNewLabel = new Label(this.group_carno, 0);
		lblNewLabel.setLayoutData(new GridData(131072, 16777216, false, false,
				1, 1));
		lblNewLabel.setText("车牌号");

		this.text_carno = new Text(this.group_carno, 2048);
		this.text_carno.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));
		this.text_carno.setEnabled(false);
		this.btn_record = new Button(this.group_carno, 0);
		this.btn_record.setText("进店记录");

		this.btn_record.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String carno = RecordEditDialog.this.text_carno.getText()
						.trim();
				ConsumerDao cdao = DataCache.getInstance().getService()
						.getConsumerDao();
				Consumer cs = cdao.get(carno);
				if (cs == null) {
					MessageDialog.openInformation(
							RecordEditDialog.this.getShell(), "提示信息", "["
									+ carno + "]为首次到店，未找到对应的进店记录！");
					return;
				}
				new HistoryRecordDialog(RecordEditDialog.this.getShell(), carno)
						.open();
			}
		});
		this.group_consumer = new Group(this.main, 0);
		this.group_consumer.setText("客户信息");
		this.group_consumer.setLayout(new GridLayout(5, false));
		GridData gd_group_1 = new GridData(4, 16777216, false, false, 1, 1);
		gd_group_1.heightHint = 95;
		this.group_consumer.setLayoutData(gd_group_1);
		this.group_consumer.setEnabled(false);

		Label lblNewLabel_1 = new Label(this.group_consumer, 0);
		lblNewLabel_1.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_1.setText("客户姓名");

		this.text_name = new Text(this.group_consumer, 2048);
		this.text_name.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Label lblNewLabel_2 = new Label(this.group_consumer, 0);
		lblNewLabel_2.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_2.setText("手机号");

		this.text_phone = new Text(this.group_consumer, 2048);
		this.text_phone.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));
		new Label(this.group_consumer, 0);

		Label lblNewLabel_7 = new Label(this.group_consumer, 0);
		lblNewLabel_7.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_7.setText("车型");

		this.text_type = new Text(this.group_consumer, 2048);
		this.text_type.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Label lblNewLabel_3 = new Label(this.group_consumer, 0);
		lblNewLabel_3.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_3.setText("颜色");

		this.combo_color = new Combo(this.group_consumer, 0);
		this.combo_color.setLayoutData(new GridData(4, 16777216, true, false,
				1, 1));
		this.combo_color.setItems(DataCache.getInstance().getColorDao().get());
		this.combo_color.setText("");
		this.btn_addColor = new Button(this.group_consumer, 0);
		GridData gd_btnNewButton = new GridData(16384, 16777216, false, false,
				1, 1);
		gd_btnNewButton.widthHint = 38;
		this.btn_addColor.setLayoutData(gd_btnNewButton);
		this.btn_addColor.setImage(ResourceManager.getPluginImage(
				"car.customer.manager", "icons/add.png"));
		this.btn_addColor.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				String color = RecordEditDialog.this.combo_color.getText()
						.trim();
				ColorDao dao = DataCache.getInstance().getColorDao();
				try {
					dao.insert(color);
				} catch (Exception e1) {
					MessageDialog.openError(getShell(), "提示信息", color
							+ "可能已存在，请检查");
					return;
				}
				MessageDialog.openInformation(getShell(), "提示信息", color
						+ "添加成功！");
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		Label lblNewLabel_4 = new Label(this.group_consumer, 0);
		lblNewLabel_4.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_4.setText("性别");

		Composite composite_1 = new Composite(this.group_consumer, 0);
		composite_1.setLayout(new GridLayout(2, false));
		GridData gd_composite_1 = new GridData(4, 16777216, false, false, 1, 1);
		gd_composite_1.heightHint = 23;
		composite_1.setLayoutData(gd_composite_1);

		this.btn_man = new Button(composite_1, 16);
		this.btn_man.setSelection(true);
		this.btn_man.setText("男");

		this.btn_woman = new Button(composite_1, 16);
		this.btn_woman.setText("女");
		new Label(this.group_consumer, 0);
		new Label(this.group_consumer, 0);
		new Label(this.group_consumer, 0);

		this.group_project = new Group(this.main, 0);
		this.group_project.setText("保养项目");
		this.group_project.setLayout(new GridLayout(2, false));
		this.group_project.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		this.group_project.setEnabled(false);
		this.tableViewer = new TableViewer(this.group_project, 67584);

		this.table = this.tableViewer.getTable();
		GridData gd_table = new GridData(4, 4, false, true, 1, 1);
		gd_table.widthHint = 525;
		this.table.setLayoutData(gd_table);
		this.table.setLinesVisible(true);
		this.table.setHeaderVisible(true);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(151);
		tblclmnNewColumn.setText("项目名称");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(184);
		tblclmnNewColumn_1.setText("费用");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				this.tableViewer, 0);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(193);
		tblclmnNewColumn_2.setText("备注");

		Composite composite = new Composite(this.group_project, 0);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(4, 4, true, false, 1, 1));
		new Label(composite, 0);

		this.btn_addProject = new Button(composite, 0);
		this.btn_addProject.setLayoutData(new GridData(4, 16777216, true,
				false, 1, 1));
		this.btn_addProject.setText("增加");
		this.btn_addProject.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new ProjectItemDialog(getShell(), new IAfter<CostProject>() {
					public void afterOperate(CostProject t) {
						RecordEditDialog.this.costItems.add(t);
						RecordEditDialog.this.tableViewer
								.setInput(RecordEditDialog.this.costItems);
						int account = 0;
						for (CostProject c : RecordEditDialog.this.costItems) {
							account += c.getCost();
						}
						RecordEditDialog.this.label_account.setText(String
								.valueOf(account));
						RecordEditDialog.this.tableViewer.refresh();
					}
				}).open();
			}
		});
		new Label(composite, 0);

		this.btn_del = new Button(composite, 0);
		this.btn_del
				.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));
		this.btn_del.setText("删除");
		this.btn_del.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ISelection select = RecordEditDialog.this.tableViewer
						.getSelection();
				if ((select == null) || (select.isEmpty())) {
					MessageDialog.openInformation(
							RecordEditDialog.this.getShell(), "提示信息",
							"请选择要删除的选项！");
					return;
				}
				CostProject st = (CostProject) ((StructuredSelection) select)
						.getFirstElement();
				RecordEditDialog.this.costItems.remove(st);
				RecordEditDialog.this.tableViewer
						.setInput(RecordEditDialog.this.costItems);
				int account = 0;
				for (CostProject c : RecordEditDialog.this.costItems) {
					account += c.getCost();
				}
				RecordEditDialog.this.label_account.setText(String
						.valueOf(account));
				RecordEditDialog.this.tableViewer.refresh();
			}
		});
		this.group_cost = new Group(this.main, 0);
		this.group_cost.setLayout(new GridLayout(3, false));
		this.group_cost.setLayoutData(new GridData(4, 4, true, false, 1, 1));
		Label lblNewLabel_5 = new Label(this.group_cost, 0);
		lblNewLabel_5.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));
		lblNewLabel_5.setText("费用总计");
		this.label_account = new Label(this.group_cost, 0);
		this.label_account.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));
		this.label_account.setForeground(SWTResourceManager.getColor(3));
		this.label_account.setAlignment(131072);
		GridData gd_label_account = new GridData(131072, 16777216, false,
				false, 1, 1);
		gd_label_account.widthHint = 92;
		this.label_account.setLayoutData(gd_label_account);

		this.lblNewLabel_6 = new Label(this.group_cost, 0);
		this.lblNewLabel_6.setFont(SWTResourceManager.getFont("微软雅黑", 15, 0));
		this.lblNewLabel_6.setText("元");
		this.text_carno.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
			}
		});
		this.tableViewer.setContentProvider(new ArrayContentProvider());
		this.tableViewer.setLabelProvider(new CostItemsLabelProvider());
		this.tableViewer.setInput(this.costItems);
		this.tableViewer.refresh();
		init();
		return this.main;
	}

	private void init() {
		if (this.isr != null) {
			this.text_carno.setText(this.isr.getConsumer().getCarno());
			this.text_name.setText(StringUtil.filterNull(this.isr.getConsumer()
					.getName()));
			this.text_phone.setText(StringUtil.filterNull(this.isr
					.getConsumer().getPhone()));
			this.combo_color.setText(StringUtil.filterNull(this.isr
					.getConsumer().getColor()));
			this.text_type.setText(StringUtil.filterNull(this.isr.getConsumer()
					.getCartype()));
			boolean sex = this.isr.getConsumer().getSex() == 0;
			this.btn_man.setSelection(sex);
			this.btn_woman.setSelection(!sex);
			this.tableViewer.setInput(this.isr.getCostProjects());
			this.costItems.addAll(this.isr.getCostProjects());
			int cost = 0;
			for (CostProject cp : this.isr.getCostProjects()) {
				cost += cp.getCost();
			}
			this.label_account.setText(String.valueOf(cost));
			this.tableViewer.refresh();
		}
	}

	protected void okPressed() {
		if (this.group_project.getEnabled()) {
			Consumer consumer = this.isr.getConsumer();
			consumer.setCarno(this.text_carno.getText().trim());
			consumer.setName(this.text_name.getText().trim());
			consumer.setPhone(this.text_phone.getText().trim());
			consumer.setCartype(this.text_type.getText().trim());
			consumer.setColor(this.combo_color.getText().trim());
			consumer.setSex(this.btn_man.getSelection() ? 0 : 1);
			this.isr.setCostProjects(this.costItems);
			try {
				DataCache.getInstance().getService().updateRecord(this.isr);
			} catch (Exception e) {
				MessageDialog.openError(getShell(), "提示信息", "订单更新失败!");
				return;
			}
		}
		ListenerManager.fireListener("car.customer.manager.customerview");
		super.okPressed();
	}

	protected Point getInitialSize() {
		return new Point(670, 720);
	}

	protected void createButtonsForButtonBar(Composite parent) {
		Button btn = createButton(parent, 0, IDialogConstants.OK_LABEL, true);
		btn.setEnabled(false);
		createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
	}

	class CostItemsLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		CostItemsLabelProvider() {
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if ((element instanceof CostProject)) {
				CostProject cp = (CostProject) element;
				if (columnIndex == 0)
					return cp.getProjectName();
				if (columnIndex == 1)
					return String.valueOf(cp.getCost());
				if (columnIndex == 2) {
					return cp.getRemark();
				}
			}
			return null;
		}
	}
}