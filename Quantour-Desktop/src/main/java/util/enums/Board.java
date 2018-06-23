package util.enums;

import java.util.Map;

/**
 * Created by 97257 on 2017/3/31.
 */
public enum Board {

    // 主板，其中沪市主板以600和601开头，深市主板以000和001开头   沪深300：000300
    Main_Board,

    // 中小板，以002开头   中小板指数：399005
    SME_Board,

    // 创业板，以300开头   创业板指数：399006
    Second_Board;

    /**
     * 根据板块类型查询该板块代码头部信息
     * @param board
     * @return
     */
    public static String[] getBoardCodeHead(Board board) {

        String[] mb_ch = {"600", "601", "000", "001"};
        String[] smeb_ch = {"002"};
        String[] sb_ch = {"300"};

        switch (board){
            case Main_Board:
                return mb_ch;
            case SME_Board:
                return smeb_ch;
            case Second_Board:
                return sb_ch;
            default:
                System.out.println("Board类型错误！");
                return null;
        }

    }


    public static Board getBoardByStock(String code){
        long codeNum = Long.parseLong(code);
        return codeNum < 2000 ? Board.Main_Board :
                    codeNum < 3000 ? Board.SME_Board :
                        codeNum < 400000 ? Board.Second_Board : Board.Main_Board;
    }


    /**
     * 获取板块枚举类型
     * @param boardName
     * @return
     */
    public static Board getBoard(String boardName){

        if (boardName.equals("主板"))
            return Board.Main_Board;
        else if (boardName.equals("中小板"))
            return Board.SME_Board;
        else if (boardName.equals("创业板"))
            return Board.Second_Board;
        else{
            System.out.println("Invalid Board Name.");
            return null;
        }

    }


    /**
     * 板块枚举类型转名称
     * @param board
     * @return
     */
    public static String getBoardName(Board board) {
        if (board.equals(Board.Main_Board)) {
            return "主板";
        } else if (board.equals(Board.SME_Board)) {
            return "中小板";
        } else if (board.equals(Board.Second_Board)) {
            return "创业板";
        } else {
            return "";
        }
    }
}
