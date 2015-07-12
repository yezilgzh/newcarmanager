package car.manager.viewpart;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.SWTResourceManager;

import car.manager.dao.RecordService;
import car.manager.dto.Consumer;
import car.manager.dto.CostProject;
import car.manager.dto.InStoreRecord;
import car.manager.dto.control.DataCache;
import car.manager.dto.provider.InStoreRecordProvider;
import car.manager.dto.provider.ProjectProvider;
import car.manager.util.StringUtil;

public class InStoreRecordViewPart extends ViewPart {
	public static final String ID = "car.customer.manager.historyrecord";
	private Text text_carno;
	private Text text_name;
	private Text text_type;
	private Text text_phone;
	private Text text_color;
	private Table table;
	private Table table_1;
	private TableViewer inStoreViewer;
	private TableViewer projectViewer;
	private Consumer consumer;
	private Button btn_man;
	private Button btn_woman;
	private Label label_account;

	public void init(IViewSite site, IMemento memento) throws PartInitException {
		String carno = site.getSecondaryId();
		RecordService service = DataCache.getInstance().getService();
		this.consumer = service.getConsumerWithInStoreRecords(carno);
		super.init(site, memento);
		setPartName(this.consumer.getCarno());
	}

	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, 0);
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
		lblNewLabel.setText("���ƺ�");

		this.text_carno = new Text(group, 2048);
		this.text_carno.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Group group_1 = new Group(composite, 0);
		group_1.setEnabled(false);
		group_1.setText("�û���Ϣ");
		group_1.setLayout(new GridLayout(4, false));
		group_1.setLayoutData(new GridData(4, 16777216, false, false, 1, 1));

		Label lblNewLabel_1 = new Label(group_1, 0);
		lblNewLabel_1.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_1.setText("�û�����");

		this.text_name = new Text(group_1, 2048);
		this.text_name.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Label label = new Label(group_1, 0);
		label.setText("�ֻ���");
		label.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));

		this.text_phone = new Text(group_1, 2048);
		this.text_phone.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Label label_1 = new Label(group_1, 0);
		label_1.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
		label_1.setText("����");

		this.text_type = new Text(group_1, 2048);
		this.text_type.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Label label_2 = new Label(group_1, 0);
		label_2.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
		label_2.setText("��ɫ");

		this.text_color = new Text(group_1, 2048);
		this.text_color.setLayoutData(new GridData(4, 16777216, true, false, 1,
				1));

		Label lblNewLabel_2 = new Label(group_1, 0);
		lblNewLabel_2.setLayoutData(new GridData(131072, 16777216, false,
				false, 1, 1));
		lblNewLabel_2.setText("�Ա�");

		Composite composite_1 = new Composite(group_1, 0);
		composite_1.setLayout(new GridLayout(2, false));
		GridData gd_composite_1 = new GridData(4, 16777216, false, false, 1, 1);
		gd_composite_1.widthHint = 100;
		gd_composite_1.heightHint = 29;
		composite_1.setLayoutData(gd_composite_1);
		this.btn_man = new Button(composite_1, 16);
		this.btn_man.setSelection(true);
		this.btn_man.setText("��");

		this.btn_woman = new Button(composite_1, 16);
		this.btn_woman.setText("Ů");
		new Label(group_1, 0);
		new Label(group_1, 0);

		Group group_2 = new Group(composite, 0);
		group_2.setLayoutData(new GridData(4, 4, true, true, 1, 1));
		group_2.setLayout(new GridLayout(2, true));

		Group group_3 = new Group(group_2, 0);
		group_3.setText("�����¼");
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
		tblclmnNewColumn.setWidth(245);
		tblclmnNewColumn.setText("����ʱ��");
		this.inStoreViewer.setContentProvider(new ArrayContentProvider());
		this.inStoreViewer.setLabelProvider(new InStoreRecordProvider());
		this.inStoreViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						InStoreRecord select = (InStoreRecord) ((StructuredSelection) event
								.getSelection()).getFirstElement();
						if (select != null) {
							InStoreRecordViewPart.this.projectViewer
									.setInput(select.getCostProjects());
							InStoreRecordViewPart.this.projectViewer.refresh();
						}
					}
				});
		Group group_4 = new Group(group_2, 0);
		group_4.setText("������¼");
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
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("��Ŀ");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				this.projectViewer, 0);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("����");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				this.projectViewer, 0);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setWidth(100);
		tblclmnNewColumn_3.setText("��ע");

		Group group_5 = new Group(composite, 0);
		group_5.setLayout(new GridLayout(3, false));
		GridData gd_group_5 = new GridData(4, 16777216, false, false, 1, 1);
		gd_group_5.heightHint = 40;
		group_5.setLayoutData(gd_group_5);

		Label lblNewLabel_3 = new Label(group_5, 0);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("΢���ź�", 15, 0));
		lblNewLabel_3.setText("���M�ܼ�");

		this.label_account = new Label(group_5, 0);
		this.label_account.setForeground(SWTResourceManager.getColor(3));
		this.label_account.setFont(SWTResourceManager.getFont("΢���ź�", 15, 0));
		GridData gd_label_account = new GridData(16384, 16777216, false, false,
				1, 1);
		gd_label_account.widthHint = 60;
		this.label_account.setLayoutData(gd_label_account);

		Label lblNewLabel_4 = new Label(group_5, 0);
		lblNewLabel_4.setFont(SWTResourceManager.getFont("΢���ź�", 15, 0));
		lblNewLabel_4.setText("Ԫ");
		this.projectViewer.setContentProvider(new ArrayContentProvider());
		this.projectViewer.setLabelProvider(new ProjectProvider());
		init();
	}

	private void init() {
		if (this.consumer != null) {
			this.text_carno.setText(StringUtil.filterNull(this.consumer
					.getCarno()));
			this.text_name.setText(StringUtil.filterNull(this.consumer
					.getName()));
			this.text_color.setText(StringUtil.filterNull(this.consumer
					.getColor()));
			this.text_phone.setText(StringUtil.filterNull(this.consumer
					.getPhone()));
			this.text_type.setText(StringUtil.filterNull(this.consumer
					.getCartype()));
			boolean sex = this.consumer.getSex() == 0;
			this.btn_man.setSelection(sex);
			this.btn_woman.setSelection(!sex);
			this.inStoreViewer.setInput(this.consumer.getInstoreRecords());
			this.inStoreViewer.refresh();
			int totalCost = 0;
			for (InStoreRecord in : this.consumer.getInstoreRecords()) {
				for (CostProject cost : in.getCostProjects()) {
					totalCost += cost.getCost();
				}
			}
			this.label_account.setText(String.valueOf(totalCost));
		}
	}

	public void setFocus() {
		try {
			PlatformUI
					.getWorkbench()
					.getActiveWorkbenchWindow()
					.getActivePage()
					.showView("car.customer.manager.historyrecord",
							getViewSite().getSecondaryId(), 1);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
}