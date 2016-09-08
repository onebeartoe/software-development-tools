
package org.onebeartoe.data.visualization.serial.plotter;

import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;

/**
 * @author Roberto Marquez
 */
public class DataChannel
{

    private int xPosition;

    private XYChart.Series chartSeries;

    private ConcurrentLinkedQueue<Number> dataQueue;

    public DataChannel()
    {
        xPosition = 0;
        chartSeries = new AreaChart.Series<Number, Number>();
        dataQueue = new ConcurrentLinkedQueue();
    }

    public int getxPosition()
    {
        return xPosition;
    }

    public void setxPosition(int xPosition)
    {
        this.xPosition = xPosition;
    }

    public XYChart.Series getChartSeries()
    {
        return chartSeries;
    }

    public void setChartSeries(XYChart.Series chartSeries)
    {
        this.chartSeries = chartSeries;
    }

    public ConcurrentLinkedQueue<Number> getDataQueue()
    {
        return dataQueue;
    }

    public void setDataQueue(ConcurrentLinkedQueue<Number> dataQueue)
    {
        this.dataQueue = dataQueue;
    }
}
