package car.manager.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;

import car.manager.dao.RecordService;
import car.manager.dto.Consumer;
import car.manager.dto.control.DataCache;

import com.alibaba.fastjson.JSON;

public class QuerUserRecordsServlet extends HttpServlet
{
  private static final long serialVersionUID = -9080875068147052401L;
  private BundleContext context;

  public QuerUserRecordsServlet()
  {
  }

  public QuerUserRecordsServlet(BundleContext context)
  {
    this.context = context;
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    doGet(req, resp);
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    resp.setCharacterEncoding("GBK");
    String userid = req.getParameter("userid");
    BufferedWriter bw = new BufferedWriter(
      new OutputStreamWriter(resp.getOutputStream(), "GBK"));
    RecordService service = DataCache.getInstance().getService();
    Consumer consumer = service.getConsumerWithInStoreRecords(userid);
    String result = JSON.toJSONString(consumer);
    bw.write(result);
    bw.flush();
    bw.close();
  }
}