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

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import util.tool.NumberFormatter;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * The content for Candle util.tool tips
 */
public class TooltipContent  extends GridPane {
    private Label dateValue = new Label();
    private Label openValue = new Label();
    private Label closeValue = new Label();
    private Label highValue = new Label();
    private Label lowValue = new Label();
    private Label changeValue = new Label();
    private Label increaseValue = new Label();
    private Label volumeValue = new Label();

    TooltipContent() {
        Label open = new Label("开盘价:");
        Label close = new Label("收盘价:");
        Label high = new Label("最高价:");
        Label low = new Label("最低价:");
        Label change = new Label("涨跌额:");
        Label increase = new Label("涨跌幅:");
        Label volume = new Label("成交量:");

        open.getStyleClass().add("candlestick-tooltip-label");
        close.getStyleClass().add("candlestick-tooltip-label");
        high.getStyleClass().add("candlestick-tooltip-label");
        low.getStyleClass().add("candlestick-tooltip-label");
        change.getStyleClass().add("candlestick-tooltip-label");
        increase.getStyleClass().add("candlestick-tooltip-label");
        volume.getStyleClass().add("candlestick-tooltip-label");
        this.getStyleClass().add("candlestick-tooltip-inner");

        setConstraints(open, 0, 1);
        setConstraints(openValue, 1, 1);
        setConstraints(close, 0, 2);
        setConstraints(closeValue, 1, 2);
        setConstraints(high, 0, 3);
        setConstraints(highValue, 1, 3);
        setConstraints(low, 0, 4);
        setConstraints(lowValue, 1, 4);
        setConstraints(change, 0, 5);
        setConstraints(changeValue, 1, 5);
        setConstraints(increase, 0, 6);
        setConstraints(increaseValue, 1, 6);
        setConstraints(volume, 0, 7);
        setConstraints(volumeValue, 1, 7);
        getChildren().addAll(dateValue,open, openValue, close, closeValue,
                             high, highValue, low, lowValue,
                            change, changeValue, increase, increaseValue,
                            volume, volumeValue);
    }

    public void update(String date, double open, double close, double high, double low, double change,
                       double increase, double volume, Average ma) {

        dateValue.getStyleClass().add("tooltip-date-label");
        openValue.getStyleClass().add("tooltip-val-label");
        closeValue.getStyleClass().add("tooltip-val-label");
        highValue.getStyleClass().add("tooltip-val-label");
        lowValue.getStyleClass().add("tooltip-val-label");
        changeValue.getStyleClass().add("tooltip-val-label");
        increaseValue.getStyleClass().add("tooltip-val-label");
        volumeValue.getStyleClass().add("tooltip-val-label");

        dateValue.setText(date);
        openValue.setText(Double.toString(open));
        closeValue.setText(Double.toString(close));
        highValue.setText(Double.toString(high));
        lowValue.setText(Double.toString(low));
        changeValue.setText(NumberFormatter.format(change));
        increaseValue.setText(NumberFormatter.formatToPercent(increase) + "%");
        if (increase < 0) {
            increaseValue.getStyleClass().add("tooltip-decrease-label");
            changeValue.getStyleClass().add("tooltip-decrease-label");
        } else {
            increaseValue.getStyleClass().add("tooltip-increase-label");
            changeValue.getStyleClass().add("tooltip-increase-label");
        }
        volumeValue.setText(NumberFormatter.formatToBaseWan(volume));
    }

    /**
     * 设置提示框的弹出时间
     * @param tooltip
     */
    public static void hackTooltipStartTiming(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
