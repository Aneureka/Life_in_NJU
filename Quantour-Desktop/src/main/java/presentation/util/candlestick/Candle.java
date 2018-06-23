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

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import util.tool.NumberFormatter;

import java.util.ArrayList;

/** Candle node used for drawing a candle */
public class Candle extends Group {
    private Line highLowLine = new Line();
    private Region bar = new Region();
    private String seriesStyleClass;
    private String dataStyleClass;
    private boolean openAboveClose = true;
    private Tooltip tooltip = new Tooltip();

    private ArrayList<Label> averageLabelList;
    private Average ma;

    Candle(String seriesStyleClass, String dataStyleClass) {
        setAutoSizeChildren(false);
        getChildren().addAll(highLowLine, bar);
        this.seriesStyleClass = seriesStyleClass;
        this.dataStyleClass = dataStyleClass;
        updateStyleClasses();
        tooltip.setGraphic(new TooltipContent());
        tooltip.getStyleClass().add("candlestick-tooltip-style");
        //设置显示时间
        TooltipContent.hackTooltipStartTiming(tooltip);
        Tooltip.install(bar, tooltip);

        initListener();
    }

    public void setSeriesAndDataStyleClasses(String seriesStyleClass,
                                             String dataStyleClass) {
        this.seriesStyleClass = seriesStyleClass;
        this.dataStyleClass = dataStyleClass;
        updateStyleClasses();
    }

    public void update(double closeOffset, double highOffset,
                       double lowOffset, double candleWidth) {
        openAboveClose = (closeOffset > 0);
        updateStyleClasses();
        highLowLine.setStartY(highOffset);
        highLowLine.setEndY(lowOffset);
        if (candleWidth == -1) {
            candleWidth = bar.prefWidth(-1);
        }
        if (openAboveClose) {
            bar.resizeRelocate(-candleWidth / 2, 0,
                               candleWidth, closeOffset);
        } else {
            bar.resizeRelocate(-candleWidth / 2, closeOffset,
                               candleWidth, -closeOffset);
        }
    }

    public void updateTooltip(String date, double open, double close, double high, double low, double change, double increase, double volume,
                              Average ma, ArrayList<Label> averageLabelList) {
        TooltipContent tooltipContent = (TooltipContent) tooltip.getGraphic();
        this.averageLabelList = averageLabelList;
        this.ma = ma;
        tooltipContent.update(date, open, close, high, low, change, increase, volume, ma);
    }

    private void updateStyleClasses() {
        final String aboveClose =
            openAboveClose ? "open-above-close" : "close-above-open";
        getStyleClass().setAll("candlestick-candle",
                               seriesStyleClass, dataStyleClass);
        highLowLine.getStyleClass().setAll("candlestick-line",
                                           seriesStyleClass, dataStyleClass,
                                           aboveClose);
        bar.getStyleClass().setAll("candlestick-bar",
                                   seriesStyleClass, dataStyleClass,
                                   aboveClose);
    }

    /**
     * 显示均值的鼠标事件
     */
    private void initListener() {
        bar.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                averageLabelList.get(0).setText(NumberFormatter.format(ma.getMa5()));
                averageLabelList.get(1).setText(NumberFormatter.format(ma.getMa10()));
                averageLabelList.get(2).setText(NumberFormatter.format(ma.getMa30()));
                averageLabelList.get(3).setText(NumberFormatter.format(ma.getMa60()));
                averageLabelList.get(4).setText(NumberFormatter.format(ma.getMa120()));
                averageLabelList.get(5).setText(NumberFormatter.format(ma.getMa240()));
            }
        });
    }
}
