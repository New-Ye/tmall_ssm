package comparator;

import java.util.Comparator;

import com.zxc.tmall.pojo.Product;

//综合排序
public class ProductAllComparator implements Comparator<Product> {

    //如果返回值小于0，说明比较结果是p1<p2，如果返回值等于0，说明比较结果是p1=p2,如果返回值大于0，则说明比较结果是p1>p2。
    //默认从小到大进行排序，这里都是从大到小
    @Override
    public int compare(Product p1, Product p2) {
        //评论数*销售数量
        return p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount();
    }

}

