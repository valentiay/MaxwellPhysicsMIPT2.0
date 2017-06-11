package Maxwell.plot;

import Maxwell.physics.Atom;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.util.*;

/**
 * Class used for building plots
 * Uses XChart library, see <a href="http://knowm.org/open-source/xchart/">XChart</a>
 */
public class Plot {
    private final List<Atom> atoms;
    private final SwingWrapper<XYChart> swingWrapper;

    private final int numberOfBars;
    private int resolution;

    private XYChart xyChart;

    private double mkt2;
    private double maxwellConstant;

    /**
     * Plot class constructor.
     * Builds Maxwell distribution, reserves data series for real distribution,
     * constructs plot frame
     *
     * @param atomsArray array with information about atoms {@link Atom}
     */
    public Plot(List<Atom> atomsArray) {
        atoms = atomsArray;

        numberOfBars = 20;
        resolution = 45;

        double m = 6.6e-27;
        double k = 1.34e-23;
        double t = 300;
        mkt2 = m / (2 * k * t);
        maxwellConstant = 4*Math.PI*Math.pow(mkt2 / Math.PI, 1.5);


        xyChart = (new XYChartBuilder()).width(600).height(600).title("Experiment results").build();
        xyChart.setXAxisTitle("Velocity, m/s");
        xyChart.setYAxisTitle("Probability");
        xyChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        xyChart.getStyler().setDecimalPattern("#,###.##");

        ArrayList<Double> emptyY = new ArrayList<>(1);
        emptyY.add(0.0);
        XYSeries maxwell = xyChart.addSeries("Maxwell Distribution", null, emptyY);
        maxwell.setMarker(SeriesMarkers.NONE);
        updateMaxwellDistribution();

        XYSeries real = xyChart.addSeries("Real Distribution", null, emptyY);
        real.setMarker(SeriesMarkers.NONE);
        real.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        updateRealDistribution();

        swingWrapper = new SwingWrapper<>(xyChart);
    }



    /**
     *  Sets the chart visible
     */
    public void display() {
        swingWrapper.displayChart();
    }



    /**
     * Updates and redraws the frame
     */
    public void render() {
        updateRealDistribution();
        SwingUtilities.invokeLater(swingWrapper::repaintChart);
    }



    /**
     * Updates Maxwell distribution series
     */
    private void updateMaxwellDistribution(){
        ArrayList<Double> maxwellDistributionX = new ArrayList<>();
        ArrayList<Double> maxwellDistributionY = new ArrayList<>();
        for (int i = 0; i < numberOfBars * 2; i++) {
            double v = i * resolution / 2;
            maxwellDistributionX.add(v);
            maxwellDistributionY.add(resolution * maxwellConstant * v*v * Math.exp(-mkt2 * v*v));
        }
        xyChart.updateXYSeries("Maxwell Distribution",
                               maxwellDistributionX,
                               maxwellDistributionY,
                               null);
    }

    /**
     * Updated real distribution series
     *
     * @see Atom
     */
    private void updateRealDistribution() {
        ArrayList<Integer> realDistribution = new ArrayList<>(numberOfBars);
        for (int i = 0; i < numberOfBars; i++)
            realDistribution.add(i, 0);
        for (Atom atom : atoms) {
            int barIndex = (int)(Math.sqrt(atom.vx * atom.vx + atom.vy * atom.vy) - 1) / resolution;
            if (barIndex >= numberOfBars) {
//                break;
                resolution *= 1.5;
                updateMaxwellDistribution();
                updateRealDistribution();
                return;
            }
            realDistribution.set(barIndex, realDistribution.get(barIndex) + 1);
        }

        ArrayList<Double> xData = new ArrayList<>();
        ArrayList<Double> yData = new ArrayList<>();
        for (int i = 0; i < numberOfBars; i++) {
            xData.add((double)i * resolution);
            xData.add((double)(i + 1) * resolution);
            yData.add((double)realDistribution.get(i) / atoms.size());
            yData.add((double)realDistribution.get(i) / atoms.size());
        }
        xyChart.updateXYSeries("Real Distribution", xData, yData, null);
    }
}