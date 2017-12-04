package org.sample.serverless.spotinst.rds;

import com.spotinst.functions.runtime.Context;
import com.spotinst.functions.runtime.Request;
import com.spotinst.functions.runtime.RequestHandler;
import com.spotinst.functions.runtime.Response;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.json.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeHandler implements RequestHandler {

  @Override
  public Response handleRequest(Request request, Context context) {

    JSONObject obj = new JSONObject(request.getBody());

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      Employee employee = new Employee();
      employee.setId(obj.getInt("id"));
      employee.setName(obj.getString("name"));
      session.save(employee);
      session.getTransaction().commit();
    }

    String responseBody = String.format("Added %s %s.", obj.getInt("id"), obj.getString("name"));

    Response response = new Response(200, responseBody);

    return response;
  }
}
