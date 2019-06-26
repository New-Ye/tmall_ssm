package comparator;


import java.util.Comparator;

import com.zxc.tmall.pojo.Product;

public class ProductSaleCountComparator implements Comparator<Product> {

	//销售数量排序（销量高的放前面）
	@Override
	public int compare(Product p1, Product p2) {
		return p2.getSaleCount()-p1.getSaleCount();
	}

}

