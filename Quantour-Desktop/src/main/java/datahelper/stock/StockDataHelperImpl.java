package datahelper.stock;

import datahelper.utilities.FileOpener;
import po.stock.StockAttributesPO;
import util.exception.StockNotFoundException;
import po.stock.StockItemPO;
import po.stock.StockPO;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Hiki on 2017/3/8.
 */
public class StockDataHelperImpl implements StockDataHelper {


    /**
     * 在csv文件中根据股票代码查询股票信息列表
     * @param code
     * @return
     * @throws StockNotFoundException
     */
    @Override
    public StockPO findStockPOByCodeFromCsv(String code) throws StockNotFoundException {

//        // 先定位到响应股票代码的文件并准备读文件
//        String path = Path.StockDirPath + code + ".csv";

        // 读取符合要求的股票信息行并储存到一个List中
        List<String> stockLines = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileOpener().getStream("/data/stocks/" + code + ".csv")));

            // 读取表头（读掉而已）
            String line = br.readLine();
            while((line = br.readLine()) != null){
                    stockLines.add(line);
            }
            br.close();
        } catch (IOException e) {
            throw new StockNotFoundException("Can not find stock by code: " + code, e);
        } catch (NullPointerException e) {
            throw new StockNotFoundException("Can not find stock by code: " + code, e);
        }

        // 如果stockLines没有数据，则抛出异常
        if(stockLines.isEmpty()) {
            throw new StockNotFoundException("Can not find any stockitem by code: " + code);
        }

        // 将stockLines中的每一个line转换成StockItemPO
        List<StockItemPO> stockItemPOs = new ArrayList<>();
        for (String stockLine : stockLines) {
            stockItemPOs.add(toStockItemPO(stockLine));
        }

        // 根据时间，反转
        Collections.reverse(stockItemPOs);

        return new StockPO(stockItemPOs);
    }

    /**
     * 在csv文件中根据股票名称查询股票信息列表
     *
     * @param name
     * @return
     * @throws StockNotFoundException
     */
    @Override
    public StockPO findStockPOByNameFromCsv(String name) throws StockNotFoundException {
        return findStockPOByCodeFromCsv(findCodeByNameFromTxt(name));
    }

    /**
     * 在code-name关系映射文件中通过name寻找code
     * @param name
     * @return
     * @throws StockNotFoundException
     */
    @Override
    public String findCodeByNameFromTxt(String name) throws StockNotFoundException {

        // 通过股票代码-名称映射文件获取股票代码
        String line = "";
        String code = "";
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileOpener().getStream("/data/code_name.txt")));

            while((line = br.readLine()) != null){
                if (line.contains(name)){
                    String[] words = line.split("\t");
                    code = words[0];
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            throw new StockNotFoundException("Code-name mapping file is missed.", e);
        }

        if(code.equals("")){
            throw new StockNotFoundException("No stock code referring to the stock name: " + name);
        }

        return code;
    }

    /**
     * 在code-name关系映射文件中查找所有code-name映射关系
     *
     * @return
     * @throws StockNotFoundException
     */
    @Override
    public Map<String, String> findCodeNameFromTxt() throws StockNotFoundException {
        // 创建存储结果的Map
        Map<String, String> code_name = new HashMap<>();

        String line = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileOpener().getStream("/data/code_name.txt")));

            while((line = br.readLine()) != null){
                String code = line.split("\t")[0];
                String name = line.split("\t")[1];
                code_name.put(code, name);
            }
            br.close();
        } catch (IOException e) {
            throw new StockNotFoundException("Code-name mapping file is missed.", e);
        }

        return code_name;
    }

    @Override
    public StockAttributesPO getStockAttributes(String code) throws StockNotFoundException {
        String[] lines = new FileOpener().openStockAttributeFileAsBR().lines().skip(1).toArray(String[]::new);

        for (String line : lines) {
            if (StockAttributesPO.parse(line).getCode().equals(code)){
                return StockAttributesPO.parse(line);
            }
        }

        for (String line : lines) {
            if (StockAttributesPO.parse(line).getName().equals(code)){
                return StockAttributesPO.parse(line);
            }
        }

        throw new StockNotFoundException("Stock " + code + " not found in stock attributes file.");
    }



    /**
     * 将取到的信息转换称StockItemPO
     * @param line
     * @return
     */
    public static StockItemPO toStockItemPO(String line) {
        // 先把line中的信息存放到迭代器中
        String[] infoArray = line.split("\t");
        List<String> infoList = Arrays.asList(infoArray);
        Iterator<String> infos = infoList.iterator();
        // 生成一个StockItemPO
        long serial = Long.parseLong(infos.next());
        LocalDate date = LocalDate.parse(infos.next());
        double open = Double.parseDouble(infos.next());
        double high = Double.parseDouble(infos.next());
        double low = Double.parseDouble(infos.next());
        double close = Double.parseDouble(infos.next());
        double volume = Long.parseLong(infos.next());
        double adjClose = Double.parseDouble(infos.next());
        String code = infos.next();
        String name = infos.next();
        String market = infos.next();

        return new StockItemPO(serial, date, open, high, low, close, volume, adjClose, code, name, market);


    }


//    private boolean required(String line, LocalDate beginTime, LocalDate endTime) {
//        LocalDate date = getDate(line);
//        if((date.isAfter(beginTime) || date.isEqual(beginTime)) && (date.isBefore(endTime) || date.isEqual(endTime)))
//            return true;
//        else
//            return false;
//
//    }


//    private LocalDate getDate(String line) {
//
//        String dateString = line.split("\t")[1];
//        LocalDate date = LocalDate.parse(dateString);
//        return date;
//    }



//    /**
//     * 在csv文件中根据股票代码、时间段查询股票信息列表
//     *
//     * @param code
//     * @param beginTime
//     * @param endTime
//     * @return
//     */
//    @Override
//    public StockPO findStockPOByCodeFromCsv(String code, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException {
//
//        // 先定位到响应股票代码的文件并准备读文件
//        String path = stockDirPath + code + ".csv";
//
//
//        // 读取符合要求的股票信息行并储存到一个List中
//        List<String> stockLines = new ArrayList<>();
//
//       try {
//           FileReader fr = new FileReader(path);
//           BufferedReader br = new BufferedReader(fr);
//
//           // 读取表头（读掉而已）
//           String line = br.readLine();
//           while((line = br.readLine()) != null){
//               if(required(line, beginTime, endTime)){
//                   stockLines.add(line);
//               }
//           }
//       } catch (IOException e) {
//           e.printStackTrace();
//       }
//
//        // 如果stockLines没有数据，则抛出异常
//        if(stockLines.isEmpty()) {
//            throw new StockNotFoundException("Can not find any stockitem by code: " + code);
//        }
//
//        // 将stockLines中的每一个line转换成StockItemPO
//        List<StockItemPO> stockItemPOs = new ArrayList<>();
//        for (String stockLine : stockLines) {
//            stockItemPOs.add(toStockItemPO(stockLine));
//        }
//
//        return new StockPO(stockItemPOs);
//
//
//    }

//    /**
//     * 在csv文件中根据股票名称、时间段查询股票信息列表
//     *
//     * @param name
//     * @param beginTime
//     * @param endTime
//     * @return
//     */
//    @Override
//    public StockPO findStockPOByNameFromCsv(String name, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException {
//
//        return findStockPOByCodeFromCsv(findCodeByNameFromTxt(name), beginTime, endTime);
//    }
}
