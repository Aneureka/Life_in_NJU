/*
 * Copyright (c) 2008, 2016, Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package presentation.util.candlestick;

import java.util.*;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import vo.stock.StockItemVO;

/**
 * A candlestick chart is a style of bar-chart used primarily to describe
 * price movements of a security, derivative, or currency over time.
 *
 * The Data Y value is used for the opening price and then the
 * close, high and low values are stored in the Data's
 * extra value property using a CandleStickExtraValues object.
 */
public class CandleStickChart extends XYChart<String, Number> {

    private ArrayList<Label> averageLabelList;

    /**
     * Construct a new CandleStickChart with the given axis.
     *
     * @param xAxis The x axis to use
     * @param yAxis The y axis to use
     */
    public CandleStickChart(Axis<String> xAxis, Axis<Number> yAxis) {
        super(xAxis, yAxis);
        final String candleStickChartCss =
            getClass().getResource("/css/main.css").toExternalForm();
        getStylesheets().add(candleStickChartCss);
        setAnimated(false);
        xAxis.setAnimated(false);
        yAxis.setAnimated(false);
    }

    public void setLabelList(ArrayList<Label> averageLabelList) {
        this.averageLabelList = averageLabelList;
    }

    /**
     * Construct a new CandleStickChart with the given axis and data.
     *
     * @param xAxis The x axis to use
     * @param yAxis The y axis to use
     * @param data The actual data list to use so changes will be
     *             reflected in the chart.
     */
//    public CandleStickChart(Axis<CategoryAxis> xAxis, Axis<Number> yAxis,
//                            ObservableList<Series<Number, Number>> data) {
//        this(xAxis, yAxis);
//        setData(data);
//    }

    /** Called to update and layout the content for the plot */
    @Override protected void layoutPlotChildren() {
        // we have nothing to layout if no data is present
        if (getData() == null) {
            return;
        }
        // update candle positions
        for (int index = 0; index < getData().size(); index++) {
            Series<String, Number> series = getData().get(index);
            Iterator<Data<String, Number>> iter =
                getDisplayedDataIterator(series);
            Path seriesPath = null;
            if (series.getNode() instanceof Path) {
                seriesPath = (Path) series.getNode();
                seriesPath.getElements().clear();

            }
            while (iter.hasNext()) {
                Axis<Number> yAxis = getYAxis();
                Data<String, Number> item = iter.next();
                String X = getCurrentDisplayedXValue(item);
                Number Y = getCurrentDisplayedYValue(item);
                double x = getXAxis().getDisplayPosition(X);
                double y = getYAxis().getDisplayPosition(Y);
                Node itemNode = item.getNode();
                CandleStickExtraValues extra =
                    (CandleStickExtraValues)item.getExtraValue();
                if (itemNode instanceof Candle && extra != null) {
                    double close = yAxis.getDisplayPosition(extra.getClose());
                    double high = yAxis.getDisplayPosition(extra.getHigh());
                    double low = yAxis.getDisplayPosition(extra.getLow());
                    // calculate candle width
                    double candleWidth = -1;
                    if (getXAxis() instanceof CategoryAxis) {
                         // use 90% width between ticks
                        CategoryAxis xa = (CategoryAxis) getXAxis();
                        candleWidth = xa.getCategorySpacing() * 0.9;
                    }
                    // update candle
                    Candle candle = (Candle)itemNode;
                    candle.update(close - y, high - y, low - y, candleWidth);
                    candle.updateTooltip(extra.getDate(), item.getYValue().doubleValue(),
                                         extra.getClose(), extra.getHigh(),
                                         extra.getLow(), extra.getChange(),
                                         extra.getIncrease(), extra.getVolume(),
                                         extra.getMa(), averageLabelList);

                    // position the candle
                    candle.setLayoutX(x);
                    candle.setLayoutY(y);
                }
                if (seriesPath != null) {
                    double ave = yAxis.getDisplayPosition(extra.getAverage());
                    if (seriesPath.getElements().isEmpty()) {
                        seriesPath.getElements().add(new MoveTo(x, ave));
                    } else {
                        seriesPath.getElements().add(new LineTo(x, ave));
                    }
                }
            }
        }
    }




    /**
     * Create a new Candle node to represent a single data item
     *
     * @param seriesIndex The index of the series the data item is in
     * @param item        The data item to create node for
     * @param itemIndex   The index of the data item in the series
     * @return New candle node to represent the give data item
     */
    private Node createCandle(int seriesIndex, final Data item,
                              int itemIndex) {
        Node candle = item.getNode();
        // check if candle has already been created
        if (candle instanceof Candle) {
            ((Candle)candle).setSeriesAndDataStyleClasses("series" + seriesIndex,
                                                          "data" + itemIndex);
        } else {
            candle = new Candle("series" + seriesIndex, "data" + itemIndex);
            item.setNode(candle);
        }
        return candle;
    }




    @Override
    protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item) {
        Node candle = createCandle(getData().indexOf(series), item, itemIndex);
        if (shouldAnimate()) {
            candle.setOpacity(0);
            getPlotChildren().add(candle);
            // fade in new candle
            final FadeTransition ft =
                    new FadeTransition(Duration.millis(500), candle);
            ft.setToValue(1);
            ft.play();
        } else {
            getPlotChildren().add(candle);
        }
        // always draw average line on top
        if (series.getNode() != null) {
            series.getNode().toFront();
        }
    }

    @Override
    protected void dataItemRemoved(Data<String, Number> item, Series<String, Number> series) {
        final Node candle = item.getNode();
        if (shouldAnimate()) {
            // fade out old candle
            final FadeTransition ft =
                    new FadeTransition(Duration.millis(500), candle);
            ft.setToValue(0);
            ft.setOnFinished((ActionEvent actionEvent) -> {
                getPlotChildren().remove(candle);
            });
            ft.play();
        } else {
            getPlotChildren().remove(candle);
        }
    }

    @Override
    protected void dataItemChanged(Data<String, Number> item) {

    }

    @Override
    protected void seriesAdded(Series<String, Number> series, int seriesIndex) {
        // handle any data already in series

        for (int j = 0; j < series.getData().size(); j++) {
            Data item = series.getData().get(j);
            Node candle = createCandle(seriesIndex, item, j);
            if (shouldAnimate()) {
                candle.setOpacity(0);
                getPlotChildren().add(candle);
                // fade in new candle
                final FadeTransition ft =
                        new FadeTransition(Duration.millis(500), candle);
                ft.setToValue(1);
                ft.play();
            } else {
                getPlotChildren().add(candle);
            }
        }
        // create series path
        Path seriesPath = new Path();
        seriesPath.getStyleClass().setAll("candlestick-average-line" + seriesIndex,
                "series" + seriesIndex);


        series.setNode(seriesPath);

        getPlotChildren().add(seriesPath);
    }

    @Override
    protected void seriesRemoved(Series<String, Number> series) {
        // remove all candle nodes
        for (Data<String, Number> d : series.getData()) {
            final Node candle = d.getNode();
            if (shouldAnimate()) {
                // fade out old candle
                final FadeTransition ft =
                        new FadeTransition(Duration.millis(500), candle);
                ft.setToValue(0);
                ft.setOnFinished((ActionEvent actionEvent) -> {
                    getPlotChildren().remove(candle);
                });
                ft.play();
            } else {
                getPlotChildren().remove(candle);
            }
        }
    }

    /**
     * This is called when the range has been invalidated and we need to
     * update it. If the axis are auto ranging then we compile a list of
     * all data that the given axis has to plot and call invalidateRange()
     * on the axis passing it that data.
     */
    @Override
    protected void updateAxisRange() {
        // For candle stick chart we need to override this method as we need
        // to let the axis know that they need to be able to cover the area
        // occupied by the high to low range not just its center data value.
        final Axis<String> xa = getXAxis();
        final Axis<Number> ya = getYAxis();
        List<String> xData = null;
        List<Number> yData = null;
        if (xa.isAutoRanging()) {
            xData = new ArrayList<>();
        }
        if (ya.isAutoRanging()) {
            yData = new ArrayList<Number>();
        }
        if (xData != null || yData != null) {
            for (Series<String, Number> series : getData()) {
                for (Data<String, Number> data : series.getData()) {
                    if (xData != null) {
                        xData.add(data.getXValue());
                    }
                    if (yData != null) {
                        CandleStickExtraValues extras =
                            (CandleStickExtraValues)data.getExtraValue();
                        if (extras != null) {
                            yData.add(extras.getHigh());
                            yData.add(extras.getLow());
                        } else {
                            yData.add(data.getYValue());
                        }
                    }
                }
            }
            if (xData != null) {
                xa.invalidateRange(xData);
            }
            if (yData != null) {
                ya.invalidateRange(yData);
            }
        }
    }

    public static void createContent(CandleStickChart chart, NumberAxis yAxis, List<StockItemVO> stockItemList, boolean isDay) {
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> series5 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> series10 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> series30 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> series60 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> series120 = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> series240 = new XYChart.Series<String, Number>();

        int size = stockItemList.size();
        for (int i = 0; i < size; i++) {
            StockItemVO stockItemVO = stockItemList.get(i);
            // K线图 DAY, OPEN, CLOSE, HIGH, LOW, AVERAGE,涨跌额，涨跌幅，成交量
            Average ma = new Average(stockItemVO.averages.get(5), stockItemVO.averages.get(10), stockItemVO.averages.get(30),
                    stockItemVO.averages.get(60), stockItemVO.averages.get(120), stockItemVO.averages.get(240));
            String date = String.valueOf(stockItemVO.date);

            final CandleStickExtraValues extras =
                    new CandleStickExtraValues(date, stockItemVO.close, stockItemVO.high, stockItemVO.low,
                            0, stockItemVO.change, stockItemVO.increase, stockItemVO.volume, ma);
            series.getData().add(new XYChart.Data<String, Number>(date, stockItemVO.open, extras));
            if (isDay) {
                addMaSeriesData(series5, date, stockItemVO.averages.get(5), ma);
                addMaSeriesData(series10, date, stockItemVO.averages.get(10), ma);
                addMaSeriesData(series30, date, stockItemVO.averages.get(30), ma);
                addMaSeriesData(series60, date, stockItemVO.averages.get(60), ma);
                addMaSeriesData(series120, date, stockItemVO.averages.get(120), ma);
                addMaSeriesData(series240, date, stockItemVO.averages.get(240), ma);
            }
        }
        // 载入数据
        if (chart.getData() != null) {
            chart.getData().clear();
        }

        // K线图数据
        ObservableList<Series<String, Number>> data = chart.getData();
        if (data == null) {
            data = FXCollections.observableArrayList(series);
            chart.setData(data);
        } else {
            chart.getData().add(series);
        }
        if (isDay) {
            // 均线图数据
            chart.getData().addAll(series5, series10, series30, series60, series120, series240);
        }

        // 设置 y 轴坐标
        double minPrice = getMinPriceValue(stockItemList);
        double maxPrice = getMaxPriceValue(stockItemList);
        double minMa = getMinMaValue(stockItemList);
        double maxMa = getMaxMaValue(stockItemList);
        double minValue = minPrice > minMa ? minMa : minPrice;
        double maxValue = maxPrice > maxMa ? maxPrice : maxMa;

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(minValue * 0.98);
        yAxis.setUpperBound(maxValue * 1.02);
        yAxis.setTickUnit((maxValue * 1.02 - minValue * 0.98) / 10);
    }

    private static void addMaSeriesData(XYChart.Series<String, Number> series, String date, double average, Average ma) {
        series.getData().add(new XYChart.Data<String, Number>(date, 0,
                new CandleStickExtraValues(date, 0, 0, 0, average, 0, 0, 0, ma)));
    }


    private static double getMinPriceValue(List<StockItemVO> stockItemList) {
        double min = stockItemList.get(0).low;
        int size = stockItemList.size();
        for (int i = 1; i < size; i++) {
            double low = stockItemList.get(i).low;
            if (low < min) {
                min = low;
            }
        }
        return min;
    }

    private static double getMaxPriceValue(List<StockItemVO> stockItemList) {
        double max = stockItemList.get(0).high;
        int size = stockItemList.size();
        for (int i = 1; i < size; i++) {
            double high = stockItemList.get(i).high;
            if (high > max) {
                max = high;
            }
        }
        return max;
    }

    private static double getMinMaValue(List<StockItemVO> stockItemList) {
        double minMa = stockItemList.get(0).averages.get(5);
        int size = stockItemList.size();
        for (int i = 0; i < size; i++) {
            Collection<Double> c = stockItemList.get(i).averages.values();
            Object[] array = c.toArray();
            Arrays.sort(array);

            if (minMa > (double) array[0]) {
                minMa = (double) array[0];
            }
        }
        return minMa;
    }

    private static double getMaxMaValue(List<StockItemVO> stockItemList) {
        double maxMa = stockItemList.get(0).averages.get(5);
        int mapSize = stockItemList.get(0).averages.size();
        int size = stockItemList.size();
        for (int i = 0; i < size; i++) {
            Collection<Double> c = stockItemList.get(i).averages.values();
            Object[] array = c.toArray();
            Arrays.sort(array);

            if (maxMa < (double) array[mapSize - 1]) {
                maxMa = (double) array[mapSize - 1];
            }
        }
        return maxMa;
    }
}
