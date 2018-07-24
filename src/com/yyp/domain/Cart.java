package com.yyp.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车实体
 * @author yyp
 *
 */
public class Cart implements Serializable{
	//存放购物车项的集合 key:商品的id  cartitem:购物车项  使用map集合便于删除单个购物车项
	private Map<String, CartItem> map=new LinkedHashMap<String, CartItem>();
	//总金额
	private Double total=0.0;
	/**
	 * 返回所有的购物车项
	 * @return
	 */
	public Collection<CartItem> getItems(){
		 return map.values();
	}
	
	/**
	 * 将购物车项添加到购物车
	 * @param item 购物车项
	 */
	public void add2Cart(CartItem item){
		//先判断购物车中有无该商品
		//1.获取该商品的pid
		String pid = item.getProduct().getPid();
		//判断这个购物车里有无包含该pid的购物车项
		if(map.containsKey(pid)){
			//有
			//将该商品的数量进行累加
			CartItem oItem = map.get(pid);//之前的该商品
			oItem.setCount(oItem.getCount()+item.getCount());
		}else{
			//没有
			map.put(pid, item);
		}
		
		//添加完成之后，修改总金额
		total+=item.getSubtotal();
		
	}
	
	/**
	 * 将指定的商品pid从购物车中删除
	 * @param pid 商品的pid
	 */
	public void removeFromCart(String pid){
		//1.从map集合中删除
		CartItem item = map.remove(pid);
		//2.修改金额
		total-=item.getSubtotal();
	}
	
	/**
	 * 清空购物车(将购物车项全部移除)
	 */
	public void clearCart(){
		//1.将map置空
		map.clear();
		//2.金额归零
		total=0.0;
	}
	
	
	public Map<String, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	
	
}
