package car.manager.viewpart;

import java.util.List;

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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import car.manager.dao.ConsumerDao;
import car.manager.dialog.OldUserCarReceptionDialog;
import car.manager.dto.Consumer;
import car.manager.dto.control.DataCache;
import car.manager.util.ViewUtil;

public class UserRecordsQueryViewPart extends ViewPart
{
  public static final String ID = "car.customer.manager.query";
  private Text text_carno;
  private Table table;
  private Button btn_query;
  private TableViewer tableViewer;
  private Button btn_reception;
  private Button btn_carno;
  private Button btn_phone;

  public void createPartControl(Composite parent)
  {
    Composite composite = new Composite(parent, 0);
    composite.setLayout(new GridLayout(1, false));
    Group group = new Group(composite, 0);
    group.setLayout(new GridLayout(4, false));
    group.setLayoutData(new GridData(4, 4, true, false, 1, 1));

    Composite composite_1 = new Composite(group, 0);
    composite_1.setLayout(new GridLayout(2, false));

    this.btn_carno = new Button(composite_1, 16);
    this.btn_carno.setText("车牌号");
    this.btn_carno.setSelection(true);
    this.btn_phone = new Button(composite_1, 16);
    this.btn_phone.setText("手机号");

    this.text_carno = new Text(group, 2048);
    this.text_carno.setLayoutData(
      new GridData(4, 16777216, true, 
      false, 1, 1));

    this.btn_query = new Button(group, 0);
    GridData gd_btnNewButton = new GridData(16384, 16777216, false, 
      false, 1, 1);
    gd_btnNewButton.widthHint = 83;
    this.btn_query.setLayoutData(gd_btnNewButton);
    this.btn_query.setText("查询");

    this.btn_reception = new Button(group, 0);
    GridData gd_btnNewButton_1 = new GridData(16384, 16777216, false, 
      false, 1, 1);
    gd_btnNewButton_1.widthHint = 82;
    this.btn_reception.setLayoutData(gd_btnNewButton_1);
    this.btn_reception.setText("接待");

    Group group_1 = new Group(composite, 0);
    group_1.setLayout(new GridLayout(1, false));
    group_1.setLayoutData(new GridData(4, 4, true, true, 1, 1));

    this.tableViewer = new TableViewer(group_1, 67584);
    this.table = this.tableViewer.getTable();
    this.table.setLayoutData(new GridData(4, 4, true, true, 1, 1));
    this.table.setHeaderVisible(true);
    this.table.setLinesVisible(true);

    TableViewerColumn tableViewerColumn = new TableViewerColumn(
      this.tableViewer, 0);
    TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
    tblclmnNewColumn.setWidth(100);
    tblclmnNewColumn.setText("车牌号");

    TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
      this.tableViewer, 0);
    TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
    tblclmnNewColumn_1.setWidth(100);
    tblclmnNewColumn_1.setText("客户姓名");

    TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(
      this.tableViewer, 0);
    TableColumn tblclmnNewColumn_5 = tableViewerColumn_5.getColumn();
    tblclmnNewColumn_5.setWidth(79);
    tblclmnNewColumn_5.setText("性别");

    TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
      this.tableViewer, 0);
    TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
    tblclmnNewColumn_2.setWidth(100);
    tblclmnNewColumn_2.setText("车型");

    TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
      this.tableViewer, 0);
    TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
    tblclmnNewColumn_3.setWidth(100);
    tblclmnNewColumn_3.setText("颜色");

    TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(
      this.tableViewer, 0);
    TableColumn tblclmnNewColumn_4 = tableViewerColumn_4.getColumn();
    tblclmnNewColumn_4.setWidth(145);
    tblclmnNewColumn_4.setText("手机号");
    this.btn_query.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        String carno = UserRecordsQueryViewPart.this.text_carno.getText().trim();
        ConsumerDao dao = DataCache.getInstance().getService()
          .getConsumerDao();
        List css = dao.getFuzzyConsumer(carno, 
          UserRecordsQueryViewPart.this.btn_carno.getSelection());
        UserRecordsQueryViewPart.this.tableViewer.setInput(css);
        UserRecordsQueryViewPart.this.tableViewer.refresh();
      }
    });
    this.text_carno.addKeyListener(new KeyListener()
    {
      public void keyReleased(KeyEvent e)
      {
        if (e.keyCode == 16777296) {
          String carno = UserRecordsQueryViewPart.this.text_carno.getText().trim();
          ConsumerDao dao = DataCache.getInstance().getService()
            .getConsumerDao();
          List css = dao.getFuzzyConsumer(carno, 
            UserRecordsQueryViewPart.this.btn_carno.getSelection());
          UserRecordsQueryViewPart.this.tableViewer.setInput(css);
          UserRecordsQueryViewPart.this.tableViewer.refresh();
        }
      }

      public void keyPressed(KeyEvent e)
      {
      }
    });
    this.tableViewer.setContentProvider(new ArrayContentProvider());
    this.tableViewer.setLabelProvider(new CustomerQueryLabelProvider());
    this.tableViewer.refresh();
    this.tableViewer.addDoubleClickListener(new IDoubleClickListener()
    {
      public void doubleClick(DoubleClickEvent event)
      {
        Consumer select = (Consumer)((StructuredSelection)event
          .getSelection()).getFirstElement();
        ViewUtil.openViewPart("car.customer.manager.historyrecord", 
          select.getCarno());
      }
    });
    this.btn_reception.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e)
      {
        ISelection select = UserRecordsQueryViewPart.this.tableViewer.getSelection();
        if ((select == null) || (select.isEmpty())) {
          MessageDialog.openInformation(UserRecordsQueryViewPart.this.btn_reception.getShell(), 
            "提示信息", "请选择要接待的车！");
          return;
        }
        Consumer consumer = (Consumer)((StructuredSelection)select)
          .getFirstElement();
        new OldUserCarReceptionDialog(UserRecordsQueryViewPart.this.btn_reception.getShell(), 
          consumer).open();
      }
    });
  }

  public void setFocus() {
  }

  class CustomerQueryLabelProvider extends LabelProvider implements ITableLabelProvider, ITableFontProvider {
    CustomerQueryLabelProvider() {
    }

    public Image getColumnImage(Object element, int columnIndex) {
      return null;
    }

    public String getColumnText(Object element, int columnIndex)
    {
      if ((element instanceof Consumer)) {
        Consumer cr = (Consumer)element;
        if (columnIndex == 0)
          return cr.getCarno();
        if (columnIndex == 1)
          return cr.getName();
        if (columnIndex == 2)
          return cr.getSex() == 0 ? "男" : "女";
        if (columnIndex == 3)
          return cr.getCartype();
        if (columnIndex == 4)
          return cr.getColor();
        if (columnIndex == 5) {
          return cr.getPhone();
        }
      }

      return null;
    }

    public Font getFont(Object element, int columnIndex)
    {
      return null;
    }
  }
}