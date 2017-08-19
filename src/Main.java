import experiment.Experiment;
import experiment.experimentExamples.GravityExperiment;
import experiment.settings.GravitySettings;


class Main {
    public static void main(String[] args) {
        Experiment experiment = new GravityExperiment();
        experiment.run(new GravitySettings());
    }
}