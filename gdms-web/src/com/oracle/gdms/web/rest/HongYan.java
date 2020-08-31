package com.oracle.gdms.web.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.oracle.gdms.entity.GoodsEntity;
import com.oracle.gdms.entity.GoodsModel;
import com.oracle.gdms.entity.GoodsTypeEntity;
import com.oracle.gdms.entity.ResponseEntity;
import com.oracle.gdms.service.GoodsService;
import com.oracle.gdms.util.Factory;



@Path("/hongyan")
public class HongYan {

	@Path("/sing")
	@GET
	public String sing() {
		System.out.println("��ҵ������ÿ�");
		return "hello";
	}
	
	@Path("/sing/{name}")
	@GET
	public String sing(@PathParam("name") String name) {
		System.out.println("����="+name);
		return "OK";
	}
	
	@Path("/sing/ci")//rest/hongyan/sing/ci?name=xxx
	@GET
	public String singOne(@QueryParam("name") String name) {
		System.out.println("���="+name);
		return "CI";
	}
	
	@Path("/push/one")
	@POST
	public String push(@FormParam("name") String name,@FormParam("sex") String sex) {
		System.out.println("��Ʒ����="+name);
		return "form";
	}
	
	@Path("/push/json")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String pushJson(String jsonparam) {
		System.out.println(jsonparam);
		JSONObject j =  JSONObject.parseObject(jsonparam);
		String name = j.getString("name");
		String sex = j.getString("sex");
		String age = j.getString("age");
		System.out.println("name="+name);
		System.out.println("sex="+sex);
		System.out.println("age="+age);
		return "json";
	}
	@Path("/push/upda")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)//�涨���صĽ��ΪJSON����
	public String updateGoodsType(String jsonparam) {
		JSONObject j =  JSONObject.parseObject(jsonparam);
		String goodsid = j.getString("goodsid");
		String gtid = j.getString("gtid");
		System.out.println("gtid="+gtid);
		GoodsEntity id = new GoodsEntity();
		id.setGoodsid(Integer.parseInt(goodsid));
		id.setGtid(Integer.parseInt(gtid));
		GoodsService service = (GoodsService) Factory.getInstance().getObject("goods.service.impl");
		int goods = service.goodsselect(id);
		return j.toJSONString();
	}

	@Path("/push/json/j")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)//�涨���صĽ��ΪJSON����
	public List<GoodsEntity> pushJs(String jsonparam) {
		//System.out.println(jsonparam);
		JSONObject j =  JSONObject.parseObject(jsonparam);
		String gtid = j.getString("gtid");
		//System.out.println("gtid="+gtid);
		GoodsService service = (GoodsService) Factory.getInstance().getObject("goods.service.impl");
		List<GoodsEntity> list = service.find(Integer.parseInt(gtid));
		//System.out.println("list="+list);
		return list;
	}
	@Path("/goods/push/one")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)//�涨���صĽ��ΪJSON����
	public ResponseEntity pushGoodsOne(String jsonparam) {
		
		
		ResponseEntity r = new ResponseEntity();
		try {
			JSONObject j = JSONObject.parseObject(jsonparam);
		    String gs = j.getString("goods");
		    GoodsModel goods = JSONObject.parseObject(gs,GoodsModel.class);
		    System.out.println("������Ʒ���յ���");
		    System.out.println("��Ʒid="+goods.getGoodsid());
		    System.out.println("��Ʒ����="+goods.getName());
			r.setCode(0);
			r.setMessage("���ͳɹ�");
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			r.setCode(1111);
			r.setMessage("����ʧ��:��Ʒ���ݲ��Ϸ�"+jsonparam);
		}
		return r;
	}
}
