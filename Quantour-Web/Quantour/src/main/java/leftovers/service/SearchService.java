package leftovers.service;

import leftovers.model.SearchStockItem;
import leftovers.repository.SearchStockItemRepository;
import leftovers.util.Chinese2Pinyin;
import leftovers.util.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by kevin on 2017/6/7.
 */
@Service
public class SearchService {
    @Autowired
    private SearchStockItemRepository searchStockItemRepository;

    public void addPinyinForAllItem(){
        List<SearchStockItem> searchStockItems = searchStockItemRepository.findAll();

        for (int i = 0; i < searchStockItems.size(); i++) {
            String chinese = searchStockItems.get(i).getName();
            searchStockItems.get(i).setPinyinFirstSpell(Chinese2Pinyin.convert(chinese, Chinese2Pinyin.FIRST_SPELL).iterator().next());
            searchStockItems.get(i).setPinyinFullSpell(Chinese2Pinyin.convert(chinese, Chinese2Pinyin.FULL_SPELL).iterator().next());
        }

        searchStockItemRepository.save(searchStockItems);
    }

    public String search(String input){
        try {
            String url = "http://123.206.119.37:8983/solr/mystocks/select?defType=dismax&indent=on&q=" +
                URLEncoder.encode(input, "UTF-8") +
                "&qf=id%20name%20first_spell%20full_spell&wt=json";
            return Request.get(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
