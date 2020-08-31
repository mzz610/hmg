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
		System.out.println("锡业跳舞真好看");
		return "hello";
	}
	
	@Path("/sing/{name}")
	@GET
	public String sing(@PathParam("name") String name) {
		System.out.println("歌名="+name);
		return "OK";
	}
	
	@Path("/sing/ci")//rest/hongyan/sing/ci?name=xxx
	@GET
	public String singOne(@QueryParam("name") String name) {
		System.out.println("歌词="+name);
		return "CI";
	}
	
	@Path("/push/one")
	@POST
	public String push(@FormParam("name") String name,@FormParam("sex") String sex) {
		System.out.println("商品名称="+name);
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
	@Consumes(MediaType.APPLICATION_JSON)//规定返回的结果为JSON对象
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
	@Consumes(MediaType.APPLICATION_JSON)//规定返回的结果为JSON对象
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
	@Consumes(MediaType.APPLICATION_JSON)//规定返回的结果为JSON对象
	public ResponseEntity pushGoodsOne(String jsonparam) {
		
		
		ResponseEntity r = new ResponseEntity();
		try {
			JSONObject j = JSONObject.parseObject(jsonparam);
		    String gs = j.getString("goods");
		    GoodsModel goods = JSONObject.parseObject(gs,GoodsModel.class);
		    System.out.println("服务商品端收到了");
		    System.out.println("商品id="+goods.getGoodsid());
		    System.out.println("商品名称="+goods.getName());
			r.setCode(0);
			r.setMessage("推送成功");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			r.setCode(1111);
			r.setMessage("推送失败:商品数据不合法"+jsonparam);
		}
		return r;
	}
}
