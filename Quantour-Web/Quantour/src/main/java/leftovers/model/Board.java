package leftovers.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by kevin on 2017/6/4.
 */
@Entity
@Table(name = "board")
public class Board {
    @Id
    @Column(name = "id")
    @JsonIgnore
    private int id;

    @Column(name = "wind_code")
    private String code;

    @Column(name = "board")
    private String board;

//    @ManyToOne
//    @JoinColumn(name = "wind_code")
//    @JsonBackReference
//    private StockCurInfo stockCurInfo;

    public Board() {
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

//    public StockCurInfo getStockCurInfo() {
//        return stockCurInfo;
//    }
//
//    public void setStockCurInfo(StockCurInfo stockCurInfo) {
//        this.stockCurInfo = stockCurInfo;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (getClass() != o.getClass() && !(o instanceof String))) return false;

        if (getClass() == o.getClass()){
            Board board1 = (Board)o;
            return board.equals(board1.board);
        }else {
            String board1 = (String)o;
            return board.equals(board1);
        }
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }
}
