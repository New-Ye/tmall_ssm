package comparator;



import java.util.Comparator;

import com.zxc.tmall.pojo.Product;

//日期排序（新品放前面）
public class ProductDateComparator implements Comparator<Product>{

	//订单产生时间
	@Override
	public int compare(Product p1, Product p2) {
		return p1.getCreateDate().compareTo(p2.getCreateDate());
	}

}
