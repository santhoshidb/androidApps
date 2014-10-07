package awsruns;

import java.io.IOException;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class yscraper {

	public static void main(String[] args) {
	
		/*
		 * div class="review-list
	div class="media-story"
		div class="review-wrapper"		
			meta itemprop="ratingValue" content="5.0"	
 			p class="review_comment ieSucks"
		 */
		Document doc = null;
		Elements ratingValue=null;
		String rating;
		int i=0;
		
		try {
			doc = (Document) Jsoup.connect("http://www.yelp.com/biz/palo-alto-creamery-palo-alto-2?sort_by=rating_desc").userAgent("Chrome").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//get all reviews
		Elements reviewList = doc.select("div[class=review-list]");
		//get review comments & rating	
		Elements reviewWrapper = doc.select("div[class=review-wrapper]");
	
		
		/*
		for(Element irevc : reviewWrapper){
			ratingValue=irevc.select("meta[itemprop=ratingValue]");
			rating = ratingValue.attr("content");
			System.out.print(i+":"+rating+"-"+irevc.select("p[class=review_comment ieSucks]").text()+'\n');
		i++;
		}
		
		
		for(Element ielem : reviewList.select("div[class=media-story]")){
			
			System.out.print("="+ielem.select("a[class=user-display-name]").text()+'\n');
			System.out.println(ielem.select("b").first().text());
	
		
			
		}
		*/
	}

}
