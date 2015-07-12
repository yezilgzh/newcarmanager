package car.manager.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;

import car.manager.dto.control.DataCache;

import com.alibaba.fastjson.JSON;

public class QuerUsersServlet extends HttpServlet
{
  private static final long serialVersionUID = -9080875068147052401L;
  private BundleContext context;

  public QuerUsersServlet()
  {
  }

  public QuerUsersServlet(BundleContext context)
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
    String carno = req.getParameter("carno");
    List users = DataCache.getInstance().getService()
      .getConsumerDao().getFuzzyConsumer(carno, true);
    BufferedWriter bw = new BufferedWriter(
      new OutputStreamWriter(resp.getOutputStream(), "GBK"));

    String result = JSON.toJSONString(users);
    bw.write(result);
    bw.flush();
    bw.close();
  }
}