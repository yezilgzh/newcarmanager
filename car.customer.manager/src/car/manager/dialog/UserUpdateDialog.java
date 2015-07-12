package car.manager.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import car.manager.Activator;
import car.manager.dto.Consumer;
import car.manager.dto.control.DataCache;
import car.manager.lsn.ListenerManager;
import car.manager.util.StringUtil;

public class UserUpdateDialog extends Dialog
  implements ModifyListener
{
  private Consumer consumer;
  private Text text_carno;
  private Text text_name;
  private Text text_cartype;
  private Text text_phone;
  private Combo combo_color;
  private Button btn_man;
  private Button btn_woman;

  public UserUpdateDialog(Shell parentShell, Consumer consumer)
  {
    super(parentShell);
    this.consumer = consumer;
  }

  protected Control createDialogArea(Composite parent)
  {
    parent.getShell().setText("车主信息更新");
    parent.getShell().setImage(Activator.getImage("platform:/plugin/car.customer.manager/icons/car.png"));
    Composite main = (Composite)super.createDialogArea(parent);
    GridLayout gridLayout = (GridLayout)main.getLayout();
    gridLayout.numColumns = 2;

    Label lblNewLabel = new Label(main, 0);
    lblNewLabel.setLayoutData(
      new GridData(131072, 16777216, false, 
      false, 1, 1));
    lblNewLabel.setText("车牌号");
    this.text_carno = new Text(main, 2048);
    this.text_carno.setLayoutData(
      new GridData(4, 16777216, true, 
      false, 1, 1));

    Label lblNewLabel_1 = new Label(main, 0);
    lblNewLabel_1.setLayoutData(
      new GridData(131072, 16777216, false, 
      false, 1, 1));
    lblNewLabel_1.setText("车主名");

    this.text_name = new Text(main, 2048);
    this.text_name.setLayoutData(
      new GridData(4, 16777216, true, false, 
      1, 1));

    Label lblNewLabel_5 = new Label(main, 0);
    lblNewLabel_5.setLayoutData(
      new GridData(131072, 16777216, false, 
      false, 1, 1));
    lblNewLabel_5.setText("性别");

    Composite composite = new Composite(main, 0);
    composite.setLayout(new GridLayout(2, false));
    GridData gd_composite = new GridData(4, 16777216, false, 
      false, 1, 1);
    gd_composite.heightHint = 30;
    composite.setLayoutData(gd_composite);

    this.btn_man = new Button(composite, 16);
    this.btn_man.setText("男");

    this.btn_woman = new Button(composite, 16);
    this.btn_woman.setText("女");

    Label lblNewLabel_4 = new Label(main, 0);
    lblNewLabel_4.setLayoutData(
      new GridData(131072, 16777216, false, 
      false, 1, 1));
    lblNewLabel_4.setText("电话");

    this.text_phone = new Text(main, 2048);
    this.text_phone.setLayoutData(
      new GridData(4, 16777216, true, 
      false, 1, 1));

    Label lblNewLabel_2 = new Label(main, 0);
    lblNewLabel_2.setLayoutData(
      new GridData(131072, 16777216, false, 
      false, 1, 1));
    lblNewLabel_2.setText("车型");

    this.text_cartype = new Text(main, 2048);
    this.text_cartype.setLayoutData(
      new GridData(4, 16777216, true, 
      false, 1, 1));

    Label lblNewLabel_3 = new Label(main, 0);
    lblNewLabel_3.setLayoutData(
      new GridData(131072, 16777216, false, 
      false, 1, 1));
    lblNewLabel_3.setText("颜色");

    this.combo_color = new Combo(main, 0);
    this.combo_color.setLayoutData(
      new GridData(4, 16777216, true, 
      false, 1, 1));
    addListener();
    init();
    return main;
  }

  private void addListener() {
    this.text_carno.addModifyListener(this);
    this.text_name.addModifyListener(this);
    this.text_phone.addModifyListener(this);
    this.text_cartype.addModifyListener(this);
    this.text_name.addModifyListener(this);
    this.combo_color.addModifyListener(this);
    this.btn_man.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        Button btn = UserUpdateDialog.this.getButton(0);
        if (btn != null)
          btn.setEnabled(true);
      }
    });
    this.btn_woman.addSelectionListener(new SelectionAdapter()
    {
      public void widgetSelected(SelectionEvent e) {
        Button btn = UserUpdateDialog.this.getButton(0);
        if (btn != null)
          btn.setEnabled(true);
      }
    });
  }

  private void init() {
    this.text_carno.setText(this.consumer.getCarno());
    this.text_name.setText(StringUtil.filterNull(this.consumer.getName()));
    this.text_phone.setText(StringUtil.filterNull(this.consumer.getPhone()));
    this.combo_color.setText(StringUtil.filterNull(this.consumer.getColor()));
    this.text_cartype.setText(StringUtil.filterNull(this.consumer.getCartype()));
    boolean sex = this.consumer.getSex() == 0;
    this.btn_man.setSelection(sex);
    this.btn_woman.setSelection(!sex);
  }

  protected void okPressed()
  {
    this.consumer.setCarno(this.text_carno.getText().trim());
    this.consumer.setName(this.text_name.getText().trim());
    this.consumer.setPhone(this.text_phone.getText().trim());
    this.consumer.setCartype(this.text_cartype.getText().trim());
    this.consumer.setColor(this.combo_color.getText().trim());
    this.consumer.setSex(this.btn_man.getSelection() ? 0 : 1);
    try {
      DataCache.getInstance().getService().getConsumerDao()
        .update(this.consumer);
    } catch (Exception e) {
      MessageDialog.openError(getShell(), "提示信息", "车主信息更新出错！");
      return;
    }
    super.okPressed();
    ListenerManager.fireListener("car.customer.manager.customerview");
    ListenerManager.fireListener("car.customer.manager.usermanager");
  }

  protected void createButtonsForButtonBar(Composite parent)
  {
    Button okbtn = createButton(parent, 0, 
      IDialogConstants.OK_LABEL, true);
    okbtn.setEnabled(false);
    createButton(parent, 1, 
      IDialogConstants.CANCEL_LABEL, false);
  }

  public void modifyText(ModifyEvent e)
  {
    Button btn = getButton(0);
    if (btn != null)
      btn.setEnabled(true);
  }

  protected Point getInitialSize()
  {
    return new Point(400, 300);
  }
}